package cn.fanzy.atfield.tlog.print.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.tlog.print.bean.LogRecordInfo;
import cn.fanzy.atfield.tlog.print.callback.LogOperatorService;
import cn.fanzy.atfield.tlog.print.callback.LogRecordService;

import java.time.LocalDateTime;

/**
 * 日志记录实用程序
 *
 * @author Administrator
 * @date 2024/10/17
 */
public class LogRecordUtils {

    /**
     * 写
     *
     * @param appName     应用名称
     * @param operateType 操作类型
     * @param content     内容
     * @param bizNo       商业否
     */
    public static void write(String appName, String operateType, String content, String bizNo) {
        LogOperatorService operatorService = SpringUtils.getBean(LogOperatorService.class);
        LogRecordInfo record = new LogRecordInfo();
        record.setAppName(appName);
        record.setOperatorId(operatorService.getUserId(null));
        record.setOperatorName(operatorService.getUserName(null));
        record.setOperateType(operateType);
        record.setContent(content);
        record.setOperateTime(LocalDateTime.now());
        record.setBizNo(bizNo);
        record.setOperatorIp(SpringUtils.getClientIp());
        SpringUtils.getBean(LogRecordService.class)
                .write(record);
    }

}
