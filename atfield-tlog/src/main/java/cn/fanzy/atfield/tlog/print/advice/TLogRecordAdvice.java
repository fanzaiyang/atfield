package cn.fanzy.atfield.tlog.print.advice;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.core.utils.AopUtil;
import cn.fanzy.atfield.tlog.configuration.property.TLogProperty;
import cn.fanzy.atfield.tlog.print.annotation.LogRecord;
import cn.fanzy.atfield.tlog.print.bean.LogRecordInfo;
import cn.fanzy.atfield.tlog.print.callback.LogOperatorService;
import cn.fanzy.atfield.tlog.print.callback.LogRecordService;
import cn.fanzy.atfield.tlog.print.utils.SpElUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
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
public class TLogRecordAdvice {

    private final LogOperatorService logOperatorService;
    private final LogRecordService logRecordService;

    @Pointcut("@annotation(cn.fanzy.atfield.tlog.print.annotation.LogRecord)")
    public void cut() {
    }

    @After(value = "cut()")
    public void after(JoinPoint joinPoint) {
        LogRecord annotation = AopUtil.getAnnotation(joinPoint, LogRecord.class);
        if (annotation == null) {
            return;
        }
        String content = SpElUtils.parse(annotation.content(), joinPoint);
        String bizNo = SpElUtils.parse(annotation.bizNo(), joinPoint);
        LogRecordInfo record = new LogRecordInfo();
        record.setAppName(annotation.appName());
        record.setOperatorId(logOperatorService.getUserId(null));
        record.setOperatorName(logOperatorService.getUserName(null));
        record.setOperateType(annotation.operateType());
        record.setContent(content);
        record.setOperateTime(LocalDateTime.now());
        record.setBizNo(bizNo);
        record.setOperatorIp(SpringUtils.getClientIp());
        record.setExtra(annotation.extra());
        logRecordService.write(record);
    }
}
