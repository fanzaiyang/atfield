package cn.fanzy.breeze.web.logs.aop;

import cn.fanzy.breeze.web.logs.annotation.Log;
import cn.fanzy.breeze.web.logs.model.BreezeRequestArgs;
import cn.fanzy.breeze.web.logs.properties.BreezeLogsProperties;
import cn.fanzy.breeze.web.logs.service.BreezeLogCallbackService;
import cn.fanzy.breeze.web.utils.JoinPointUtils;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.json.JSONUtil;
import com.yomahub.tlog.context.TLogContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

@Slf4j
@Aspect
@Component
@EnableConfigurationProperties(BreezeLogsProperties.class)
@ConditionalOnProperty(prefix = "breeze.web.log", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class BreezeLogsAop {
    private final BreezeLogCallbackService breezeLogCallbackService;

    public BreezeLogsAop(BreezeLogCallbackService breezeLogCallbackService) {
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
        Map<String, Object> requestData = JoinPointUtils.getParams(joinPoint);
        String clientIp = SpringUtils.getClientIp();
        Date startTime = new Date();
        TimeInterval interval = DateUtil.timer();
        long start = interval.start();
        log.info("===客户端IP：「{}」-用户信息：「{}」-请求参数：{}",
                clientIp, "",
                JSONUtil.toJsonStr(requestData));
        breezeRequestArgs = BreezeRequestArgs.builder()
                .traceId(TLogContext.getTraceId())
                .clientIp(clientIp)
                .requestData(requestData)
                .startTime(startTime)
                .classMethod(JoinPointUtils.getMethodInfo(joinPoint))
                .requestMethod(SpringUtils.getRequestMethod())
                .requestUrl(SpringUtils.getRequestUri())
                .success(true)
                .build();
        Log annotation = JoinPointUtils.getAnnotation(joinPoint, Log.class);
        if (annotation != null) {
            breezeRequestArgs.setBizName(annotation.value());
        }
    }

    @AfterReturning(value = "cut()", returning = "obj")
    public void afterReturning(Object obj) {
        log.info("===响应结果：{}", JSONUtil.toJsonStr(obj));
        if (breezeRequestArgs == null) {
            breezeRequestArgs = BreezeRequestArgs.builder()
                    .traceId(TLogContext.getTraceId())
                    .clientIp(SpringUtils.getClientIp())
                    .startTime(new Date())
                    .requestMethod(SpringUtils.getRequestMethod())
                    .requestUrl(SpringUtils.getRequestUri())
                    .success(true)
                    .build();
        }
        breezeRequestArgs.setResponseData(obj);
        breezeRequestArgs.setEndTime(new Date());
        breezeRequestArgs.setProceedSecond(DateUtil.between(breezeRequestArgs.getStartTime(), breezeRequestArgs.getEndTime(), DateUnit.SECOND));
        breezeRequestArgs.setSuccess(true);
        breezeLogCallbackService.callback(breezeRequestArgs);
    }

    @AfterThrowing(throwing = "e", value = "cut()")
    public void afterThrowing(Throwable e) {
        if (breezeRequestArgs == null) {
            breezeRequestArgs = BreezeRequestArgs.builder()
                    .traceId(TLogContext.getTraceId())
                    .clientIp(SpringUtils.getClientIp())
                    .startTime(new Date())
                    .requestMethod(SpringUtils.getRequestMethod())
                    .requestUrl(SpringUtils.getRequestUri())
                    .success(true)
                    .build();
        }
        String errorMsg = e.toString().length() > 2048 ? e.toString().substring(0, 2048) : e.toString();
        breezeRequestArgs.setResponseData(errorMsg);
        breezeRequestArgs.setEndTime(new Date());
        breezeRequestArgs.setProceedSecond(DateUtil.between(breezeRequestArgs.getStartTime(), breezeRequestArgs.getEndTime(), DateUnit.SECOND));
        breezeRequestArgs.setSuccess(false);
        breezeLogCallbackService.callback(breezeRequestArgs);
    }

    @PostConstruct
    public void init() {
        log.info("「微风组件」开启<全局日志AOP>相关的配置。");
    }
}
