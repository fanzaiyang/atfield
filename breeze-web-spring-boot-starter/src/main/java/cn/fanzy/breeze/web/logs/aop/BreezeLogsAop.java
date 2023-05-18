package cn.fanzy.breeze.web.logs.aop;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.web.logs.annotation.Log;
import cn.fanzy.breeze.web.logs.model.AppInfoModel;
import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;
import cn.fanzy.breeze.web.logs.model.UserInfoModel;
import cn.fanzy.breeze.web.logs.properties.BreezeLogsProperties;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import cn.fanzy.breeze.web.utils.ExceptionUtil;
import cn.fanzy.breeze.web.utils.HttpUtil;
import cn.fanzy.breeze.web.utils.JoinPointUtils;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yomahub.tlog.context.TLogContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author fanzaiyang
 */
@Slf4j
@Aspect
@Component
@EnableConfigurationProperties(BreezeLogsProperties.class)
@ConditionalOnProperty(prefix = "breeze.web.log", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeLogsAop {
    private final BreezeLogsProperties properties;
    private final BreezeLogCallbackService breezeLogCallbackService;

    public BreezeLogsAop(BreezeLogsProperties properties, BreezeLogCallbackService breezeLogCallbackService) {
        this.properties = properties;
        this.breezeLogCallbackService = breezeLogCallbackService;
    }

    private BreezeRequestArgs breezeRequestArgs;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)||" +
            "@annotation(cn.fanzy.breeze.web.logs.annotation.Log)")
    public void cut() {
    }

    @Before(value = "cut()")
    public void before(JoinPoint joinPoint) {
        if (skipSwagger()) {
            return;
        }
        Log annotation = JoinPointUtils.getAnnotation(joinPoint, Log.class);

        Map<String, Object> requestData = JoinPointUtils.getParams(joinPoint);
        HttpServletRequest request = SpringUtils.getRequest();
        String clientIp = SpringUtils.getClientIp(request);
        String requestUrl = request.getRequestURI();
        if (StrUtil.isNotBlank(request.getQueryString())) {
            requestUrl = requestUrl.concat("?").concat(request.getQueryString());
        }
        log.info("===客户端IP：{}，请求地址：「{}」，\n请求头：{}\n请求参数：{}", clientIp, requestUrl,
                JSONUtil.toJsonStr(HttpUtil.getHeaderMap(request)),
                JSONUtil.toJsonStr(SpringUtils.getRequestParams(request)));
        if (annotation != null && annotation.ignore()) {
            breezeRequestArgs = new BreezeRequestArgs();
            breezeRequestArgs.setIgnore(annotation.ignore());
            return;
        }
        Map<String, Object> requestParams = SpringUtils.getRequestParams(request);
        String userIdKey = annotation != null ? annotation.userIdKey() : "";
        Object userId = requestParams.get(userIdKey);
        UserInfoModel userInfo = breezeLogCallbackService.getUserInfo(userId != null ? userId.toString() : "");
        if (userInfo == null) {
            userInfo = new UserInfoModel();
        }
        String appIdKey = annotation != null ? annotation.appIdKey() : "";
        String appId = requestParams.get(appIdKey)!=null?requestParams.get(appIdKey).toString():appIdKey;
        AppInfoModel appInfo = breezeLogCallbackService.getAppInfo(appId);
        if (appInfo == null) {
            appInfo = new AppInfoModel();
        }

        breezeRequestArgs = BreezeRequestArgs.builder()
                .traceId(TLogContext.getTraceId())
                .clientIp(clientIp)
                .requestData(requestData)
                .startTime(new Date())
                .classMethod(JoinPointUtils.getMethodInfo(joinPoint))
                .requestMethod(request.getMethod())
                .requestUrl(requestUrl)
                .userId(userInfo.getUserId())
                .userName(userInfo.getUserName())
                .appId(appInfo.getAppId())
                .appName(appInfo.getAppName())
                .success(true)
                .build();

        if (annotation == null) {
            return;
        }
        breezeRequestArgs.setBizName(annotation.value());
        breezeRequestArgs.setModule(annotation.module());
        breezeRequestArgs.setLogType(annotation.type());
        if (StrUtil.isNotBlank(annotation.userIdKey())) {
            Object o = requestData.get(annotation.userIdKey());
            if (o != null) {
                breezeRequestArgs.setUserName(StrUtil.blankToDefault(breezeRequestArgs.getUserName(), o.toString()));
            }
        }
        if (StrUtil.isBlank(breezeRequestArgs.getUserName())) {
            breezeRequestArgs.setUserName(annotation.userName());
        }
        if (StrUtil.isBlank(breezeRequestArgs.getAppName())) {
            breezeRequestArgs.setAppId(annotation.appName());
        }
    }

    @AfterReturning(returning = "obj", value = "cut()")
    public void afterReturning(Object obj) {
        if (skipSwagger()) {
            return;
        }
        log.info("===响应结果：{}", JSONUtil.toJsonStr(obj));
        if (breezeRequestArgs.isIgnore()) {
            return;
        }
        breezeRequestArgs.setResponseData(obj);
        breezeRequestArgs.setEndTime(new Date());
        breezeRequestArgs.setProceedSecond(DateUtil.between(breezeRequestArgs.getStartTime(), breezeRequestArgs.getEndTime(), DateUnit.SECOND));
        breezeRequestArgs.setSuccess(true);
        breezeLogCallbackService.callback(breezeRequestArgs);

    }

    @AfterThrowing(throwing = "e", value = "cut()")
    public void afterThrowing(Throwable e) {
        if (skipSwagger()) {
            return;
        }
        log.info("===响应异常：{}", e.getMessage());
        if (breezeRequestArgs == null) {
            breezeRequestArgs = BreezeRequestArgs.builder()
                    .traceId(TLogContext.getTraceId())
                    .clientIp(SpringUtils.getClientIp())
                    .startTime(new Date())
                    .requestMethod(SpringUtils.getRequestMethod())
                    .requestUrl(SpringUtils.getRequestUri())
                    .success(false)
                    .build();
        }
        if (breezeRequestArgs.isIgnore()) {
            return;
        }
        breezeRequestArgs.setResponseData(ExceptionUtil.getErrorStackMessage(e, 1024));
        breezeRequestArgs.setEndTime(new Date());
        breezeRequestArgs.setProceedSecond(DateUtil.between(breezeRequestArgs.getStartTime(), breezeRequestArgs.getEndTime(), DateUnit.SECOND));
        breezeRequestArgs.setSuccess(false);
        breezeLogCallbackService.callback(breezeRequestArgs);
    }

    public boolean skipSwagger() {
        if (properties.getIgnoreSwagger()) {
            AntPathMatcher matcher = new AntPathMatcher();
            String requestUri = SpringUtils.getRequestUri();
            for (String pattern : BreezeConstants.SWAGGER_LIST) {
                if (matcher.match(pattern, requestUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启<全局日志AOP>相关的配置。");
    }
}
