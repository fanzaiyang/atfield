package cn.fanzy.infra.log.print.advice;


import cn.fanzy.infra.core.spring.SpringUtils;
import cn.fanzy.infra.core.utils.ExceptionUtil;
import cn.fanzy.infra.core.utils.InfraConstants;
import cn.fanzy.infra.log.common.context.TLogContext;
import cn.fanzy.infra.log.configuration.property.TLogProperty;
import cn.fanzy.infra.log.print.callback.LogCallbackService;
import cn.fanzy.infra.log.print.annotation.Log;
import cn.fanzy.infra.log.print.bean.PrintLogInfo;
import cn.fanzy.infra.log.print.util.JoinPointUtils;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * tlog-web调用时间adive
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
@EnableConfigurationProperties(TLogProperty.class)
@ConditionalOnProperty(prefix = "infra.web.enable-invoke-time-print", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class TLogWebInvokeTimeAdvice {
    private final TransmittableThreadLocal<StopWatch> invokeTimeTL = new TransmittableThreadLocal<>();
    private final TransmittableThreadLocal<PrintLogInfo> invokeLogInfo = new TransmittableThreadLocal<>();

    private final TLogProperty property;

    private final LogCallbackService callbackService;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)||" +
            "@annotation(cn.fanzy.infra.log.print.annotation.Log)")
    public void cut() {
    }

    @Before(value = "cut()")
    public void before(JoinPoint joinPoint) {
        if (skipSwagger()) {
            return;
        }
        StopWatch stopWatch = new StopWatch();
        invokeTimeTL.set(stopWatch);
        stopWatch.start();
        Log annotation = JoinPointUtils.getAnnotation(joinPoint, Log.class);
        HttpServletRequest request = SpringUtils.getRequest();
        String requestUrl = request.getRequestURI();
        if (StrUtil.isNotBlank(request.getQueryString())) {
            requestUrl = requestUrl.concat("?").concat(request.getQueryString());
        }
        String traceId = TLogContext.getTraceId();
        String appName = joinPoint.getSignature().getDeclaringTypeName();
        String moduleName = joinPoint.getSignature().getName();
        String methodName = JoinPointUtils.getMethodInfo(joinPoint);
        String operateType = "";
        String userId = StrUtil.blankToDefault(callbackService.getUserId(null), "-");
        String userName = StrUtil.blankToDefault(callbackService.getUserName(null), "-");

        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        String deviceName = JSONUtil.toJsonStr(ua);
        String clientIp = SpringUtils.getClientIp(request);
        String requestType = request.getMethod();
        String requestMethod = JoinPointUtils.getMethodInfo(joinPoint);
        String requestData = JSONUtil.toJsonStr(SpringUtils.getRequestParams(request));
        LocalDateTime requestTime = LocalDateTime.now();
        String remarks = "";
        boolean isSkip = property.getPrint().getPreEnable() != null && !property.getPrint().getPreEnable();
        if (annotation != null) {
            appName = annotation.appName();
            moduleName = annotation.moduleName();
            methodName = annotation.methodName();
            operateType = annotation.operateType();
            remarks = annotation.remarks();
            isSkip = annotation.ignore();
        }
        invokeLogInfo.set(PrintLogInfo.builder()
                .appName(appName)
                .moduleName(moduleName)
                .methodName(methodName)
                .operateType(operateType)
                .userId(userId)
                .userName(userName)
                .remarks(remarks)
                .clientIp(clientIp)
                .deviceName(deviceName)
                .requestData(requestData)
                .requestMethod(requestMethod)
                .requestTime(requestTime)
                .requestType(requestType)
                .requestUrl(requestUrl)
                .traceId(traceId)
                .build());
        callbackService.before(invokeLogInfo.get());
        if (isSkip) {
            return;
        }

        String logStr = property.getPrint().getPrePattern()
                .replace("$clientIp", StrUtil.blankToDefault(clientIp, "-"))
                .replace("$userId", StrUtil.blankToDefault(userId, "-"))
                .replace("$requestMethod", StrUtil.blankToDefault(requestMethod, "-"))
                .replace("$requestUrl", StrUtil.blankToDefault(requestUrl, "-"))
                .replace("$requestData", StrUtil.blankToDefault(requestData, "-"))
                .replace("$traceId", StrUtil.blankToDefault(traceId, "-"))
                .replace("$appName", StrUtil.blankToDefault(appName, "-"))
                .replace("$moduleName", StrUtil.blankToDefault(moduleName, "-"))
                .replace("$methodName", StrUtil.blankToDefault(methodName, "-"))
                .replace("$operateType", StrUtil.blankToDefault(operateType, "-"))
                .replace("$userId", StrUtil.blankToDefault(userId, "-"))
                .replace("$userName", StrUtil.blankToDefault(userName, "-"))
                .replace("$deviceName", StrUtil.blankToDefault(deviceName, "-"))
                .replace("$requestType", StrUtil.blankToDefault(requestType, "-"))
                .replace("$requestTime", StrUtil.blankToDefault(requestTime.toString(), "-"))
                .replace("$remarks", StrUtil.blankToDefault(remarks, "-"));
        log.info(logStr);
    }

    @AfterReturning(returning = "obj", value = "cut()")
    public void afterReturning(Object obj) {
        if (skipSwagger()) {
            return;
        }
        StopWatch stopWatch = invokeTimeTL.get();
        stopWatch.stop();
        PrintLogInfo logInfo = invokeLogInfo.get();
        logInfo.setResponseTime(LocalDateTime.now());
        logInfo.setResponseData(JSONUtil.toJsonStr(obj));
        logInfo.setResponseStatus(PrintLogInfo.ResponseStatus.SUCCESS);
        if (obj != null && JSONUtil.isTypeJSONObject(obj.toString())) {
            JSONObject entries = JSONUtil.parseObj(obj.toString());
            logInfo.setResponseMessage(entries.getStr("message", ""));
        }
        logInfo.setSpendMillis(stopWatch.getTotalTimeMillis());
        callbackService.callback(logInfo);
        if (property.getPrint().getAfterEnable() != null && !property.getPrint().getAfterEnable()) {
            return;
        }
        String logStr = property.getPrint().getAfterPattern()
                .replace("$clientIp", logInfo.getClientIp())
                .replace("$userId", StrUtil.blankToDefault(logInfo.getUserId(), "-"))
                .replace("$requestMethod", StrUtil.blankToDefault(logInfo.getRequestMethod(), "-"))
                .replace("$requestUrl", StrUtil.blankToDefault(logInfo.getRequestUrl(), "-"))
                .replace("$requestData", StrUtil.blankToDefault(logInfo.getRequestData(), "-"))
                .replace("$traceId", StrUtil.blankToDefault(logInfo.getTraceId(), "-"))
                .replace("$appName", StrUtil.blankToDefault(logInfo.getAppName(), "-"))
                .replace("$moduleName", StrUtil.blankToDefault(logInfo.getModuleName(), "-"))
                .replace("$methodName", StrUtil.blankToDefault(logInfo.getMethodName(), "-"))
                .replace("$operateType", StrUtil.blankToDefault(logInfo.getOperateType(), "-"))
                .replace("$userName", StrUtil.blankToDefault(logInfo.getUserName(), "-"))
                .replace("$deviceName", StrUtil.blankToDefault(logInfo.getDeviceName(), "-"))
                .replace("$requestType", StrUtil.blankToDefault(logInfo.getRequestType(), "-"))
                .replace("$requestTime", StrUtil.blankToDefault(logInfo.getRequestTime().toString(), "-"))
                .replace("$responseData", StrUtil.blankToDefault(logInfo.getResponseData(), "-"))
                .replace("$responseMessage", StrUtil.blankToDefault(logInfo.getResponseMessage(), "-"))
                .replace("$spendMillis", String.valueOf(logInfo.getSpendMillis()))
                .replace("$responseStatus", StrUtil.blankToDefault(logInfo.getResponseStatus().name(), "-"))
                .replace("$remarks", StrUtil.blankToDefault(logInfo.getRemarks(), "-"))
                .replace("$responseTime", StrUtil.blankToDefault(logInfo.getResponseTime().toString(), "-"));
        log.info(logStr);
    }

    @AfterThrowing(throwing = "e", value = "cut()")
    public void afterThrowing(Throwable e) {
        if (skipSwagger()) {
            return;
        }
        StopWatch stopWatch = invokeTimeTL.get();
        stopWatch.stop();
        PrintLogInfo logInfo = invokeLogInfo.get();
        logInfo.setSpendMillis(stopWatch.getTotalTimeMillis());
        logInfo.setResponseTime(LocalDateTime.now());
        logInfo.setResponseData(ExceptionUtil.getErrorStackMessage(e, 1024));
        logInfo.setResponseStatus(PrintLogInfo.ResponseStatus.FAIL);
        logInfo.setResponseMessage(e.getMessage());
        callbackService.callback(logInfo);
        if (property.getPrint().getAfterEnable() != null && !property.getPrint().getAfterEnable()) {
            return;
        }
        String logStr = property.getPrint().getAfterPattern()
                .replace("$clientIp", StrUtil.blankToDefault(logInfo.getClientIp(), "-"))
                .replace("$userId", StrUtil.blankToDefault(logInfo.getUserId(), "-"))
                .replace("$requestMethod", StrUtil.blankToDefault(logInfo.getRequestMethod(), "-"))
                .replace("$requestUrl", StrUtil.blankToDefault(logInfo.getRequestUrl(), "-"))
                .replace("$requestData", StrUtil.blankToDefault(logInfo.getRequestData(), "-"))
                .replace("$traceId", StrUtil.blankToDefault(logInfo.getTraceId(), "-"))
                .replace("$appName", StrUtil.blankToDefault(logInfo.getAppName(), "-"))
                .replace("$moduleName", StrUtil.blankToDefault(logInfo.getModuleName(), "-"))
                .replace("$methodName", StrUtil.blankToDefault(logInfo.getMethodName(), "-"))
                .replace("$operateType", StrUtil.blankToDefault(logInfo.getOperateType(), "-"))
                .replace("$userId", StrUtil.blankToDefault(logInfo.getUserId(), "-"))
                .replace("$userName", StrUtil.blankToDefault(logInfo.getUserName(), "-"))
                .replace("$deviceName", StrUtil.blankToDefault(logInfo.getDeviceName(), "-"))
                .replace("$requestType", StrUtil.blankToDefault(logInfo.getRequestType(), "-"))
                .replace("$requestTime", StrUtil.blankToDefault(logInfo.getRequestTime().toString(), "-"))
                .replace("$responseData", StrUtil.blankToDefault(logInfo.getResponseData(), "-"))
                .replace("$responseMessage", StrUtil.blankToDefault(logInfo.getResponseMessage(), "-"))
                .replace("$spendMillis", String.valueOf(logInfo.getSpendMillis()))
                .replace("$responseStatus", StrUtil.blankToDefault(logInfo.getResponseStatus().name(), "-"))
                .replace("$remarks", StrUtil.blankToDefault(logInfo.getRemarks(), "-"))
                .replace("$responseTime", StrUtil.blankToDefault(logInfo.getResponseTime().toString(), "-"));
        log.info(logStr);
    }

    public boolean skipSwagger() {
        if (property.getIgnoreSpringdoc() == null || property.getIgnoreSpringdoc()) {
            AntPathMatcher matcher = new AntPathMatcher();
            String requestUri = SpringUtils.getRequestUri();
            for (String pattern : InfraConstants.SWAGGER_LIST) {
                if (matcher.match(pattern, requestUri)) {
                    return true;
                }
            }
        }
        return false;
    }
}
