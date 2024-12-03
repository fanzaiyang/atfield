package cn.fanzy.atfield.tlog.print.advice;

import cn.fanzy.atfield.core.model.Operator;
import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.core.utils.AopUtil;
import cn.fanzy.atfield.core.utils.Constants;
import cn.fanzy.atfield.core.utils.ExceptionUtil;
import cn.fanzy.atfield.tlog.common.context.TLogContext;
import cn.fanzy.atfield.tlog.configuration.property.TLogProperty;
import cn.fanzy.atfield.tlog.print.annotation.Log;
import cn.fanzy.atfield.tlog.print.bean.PrintLogInfo;
import cn.fanzy.atfield.tlog.print.callback.LogCallbackService;
import cn.fanzy.atfield.tlog.print.callback.LogOperatorService;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.StopWatch;
import cn.hutool.core.text.AntPathMatcher;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import com.fasterxml.jackson.core.JsonProcessingException;
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
 * @since 17
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
@EnableConfigurationProperties(TLogProperty.class)
@ConditionalOnProperty(prefix = "atfield.web.enable-invoke-time-print", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class TLogWebInvokeTimeAdvice {
    private final TransmittableThreadLocal<StopWatch> invokeTimeTL = new TransmittableThreadLocal<>();
    private final TransmittableThreadLocal<PrintLogInfo> invokeLogInfo = new TransmittableThreadLocal<>();

    private final TLogProperty property;

    private final LogCallbackService callbackService;

    private final LogOperatorService userCallbackService;
    private final Operator operator;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PostMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.DeleteMapping)||" +
            "@annotation(org.springframework.web.bind.annotation.PutMapping)||" +
            "@annotation(cn.fanzy.atfield.tlog.print.annotation.Log)")
    public void cut() {
    }

    @Before(value = "cut()")
    public void before(JoinPoint joinPoint) throws JsonProcessingException {
        if (skipSwagger()) {
            return;
        }
        Log annotation = AopUtil.getAnnotation(joinPoint, Log.class);
        if (annotation != null && annotation.ignore()) {
            return;
        }
        StopWatch stopWatch = new StopWatch();
        invokeTimeTL.set(stopWatch);
        stopWatch.start();
        HttpServletRequest request = SpringUtils.getRequest();
        String requestUrl = request.getRequestURI();
        if (StrUtil.isNotBlank(request.getQueryString())) {
            requestUrl = requestUrl.concat("?").concat(request.getQueryString());
        }
        String traceId = TLogContext.getTraceId();
        String appName = joinPoint.getSignature().getDeclaringTypeName();
        String moduleName = joinPoint.getSignature().getName();
        String methodName = AopUtil.getMethodInfo(joinPoint);
        String operateType = "";
        String userId = StrUtil.blankToDefault(userCallbackService.getUserId(null), "-");
        String userName = StrUtil.blankToDefault(userCallbackService.getUserName(null), "-");
        if (StrUtil.isBlank(userId) || StrUtil.containsIgnoreCase(userId, "anonymous")) {
            userId = operator.getId();
            userName = operator.getName();
        }
        UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
        String deviceName = JSONUtil.toJsonStr(ua);
        String clientIp = SpringUtils.getClientIp(request);
        String requestType = request.getMethod();
        String requestMethod = AopUtil.getMethodInfo(joinPoint);
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
        PrintLogInfo logInfo = PrintLogInfo.builder()
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
                .responseStatus(PrintLogInfo.ResponseStatus.UNKNOWN)
                .traceId(traceId)
                .build();
        invokeLogInfo.set(logInfo);
        callbackService.before(invokeLogInfo.get());
        if (isSkip) {
            return;
        }
        log.info(getLogStr(property.getPrint().getPrePattern(), logInfo));
    }

    @AfterReturning(returning = "obj", value = "cut()")
    public void afterReturning(Object obj) throws JsonProcessingException {
        if (skipSwagger()) {
            return;
        }
        PrintLogInfo logInfo = invokeLogInfo.get();
        if (logInfo == null) {
            return;
        }
        StopWatch stopWatch = invokeTimeTL.get();
        stopWatch.stop();
        logInfo.setTraceId(TLogContext.getTraceId());
        logInfo.setResponseTime(LocalDateTime.now());
        logInfo.setResponseData(JSONUtil.toJsonStr(obj));
        logInfo.setResponseStatus(PrintLogInfo.ResponseStatus.SUCCESS);
        if (obj != null) {
            try {
                JSONObject entries = JSONUtil.parseObj(obj.toString());
                logInfo.setResponseMessage(entries.getStr("message", ""));
            } catch (Exception ignored) {
            }
        }
        logInfo.setSpendMillis(stopWatch.getTotalTimeMillis());
        callbackService.after(logInfo);
        if (property.getPrint().getAfterEnable() != null && !property.getPrint().getAfterEnable()) {
            return;
        }
        log.info(getLogStr(property.getPrint().getAfterPattern(), logInfo));
    }

    @AfterThrowing(throwing = "e", value = "cut()")
    public void afterThrowing(Throwable e) {
        if (skipSwagger()) {
            return;
        }
        PrintLogInfo logInfo = invokeLogInfo.get();
        if (logInfo == null) {
            return;
        }
        StopWatch stopWatch = invokeTimeTL.get();
        stopWatch.stop();
        logInfo.setSpendMillis(stopWatch.getTotalTimeMillis());
        logInfo.setResponseTime(LocalDateTime.now());
        logInfo.setResponseData(ExceptionUtil.getErrorStackMessage(e, property.getPrint().getResponseDataLength()));
        logInfo.setResponseStatus(PrintLogInfo.ResponseStatus.FAIL);
        logInfo.setResponseMessage(e.getMessage());
        callbackService.after(logInfo);
        if (property.getPrint().getAfterEnable() != null && !property.getPrint().getAfterEnable()) {
            return;
        }
        log.info(getLogStr(property.getPrint().getAfterPattern(), logInfo));
    }

    public boolean skipSwagger() {
        if (property.getIgnoreSpringdoc() == null || property.getIgnoreSpringdoc()) {
            AntPathMatcher matcher = new AntPathMatcher();
            String requestUri = SpringUtils.getRequestUri();
            for (String pattern : Constants.SWAGGER_LIST) {
                if (matcher.match(pattern, requestUri)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getLogStr(String pattern, PrintLogInfo logInfo) {
        String responseData = StrUtil.blankToDefault(logInfo.getResponseData(), "-");
        if (property.getPrint().getResponseDataLength() == 0) {
            responseData = "";
        } else if (property.getPrint().getResponseDataLength() > 0) {
            if (StrUtil.isNotBlank(responseData) && responseData.length() > property.getPrint().getResponseDataLength()) {
                responseData = responseData.substring(0, property.getPrint().getResponseDataLength()) + "...";
            }
        }
        String requestTime = logInfo.getRequestTime() != null ?
                LocalDateTimeUtil.format(logInfo.getRequestTime(), "yyyy-MM-dd HH:mm:ss") : null;
        String responseTime = logInfo.getResponseTime() != null ?
                LocalDateTimeUtil.format(logInfo.getResponseTime(), "yyyy-MM-dd HH:mm:ss") : null;
        return pattern.replace("$clientIp", StrUtil.blankToDefault(logInfo.getClientIp(), "-"))
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
                .replace("$requestTime", StrUtil.blankToDefault(requestTime, "-"))
                .replace("$responseData", responseData)
                .replace("$responseMessage", StrUtil.blankToDefault(logInfo.getResponseMessage(), "-"))
                .replace("$spendMillis", String.valueOf(logInfo.getSpendMillis()))
                .replace("$responseStatus", StrUtil.blankToDefault(logInfo.getResponseStatus().name(), "-"))
                .replace("$remarks", StrUtil.blankToDefault(logInfo.getRemarks(), "-"))
                .replace("$responseTime", StrUtil.blankToDefault(responseTime, "-"));
    }
}
