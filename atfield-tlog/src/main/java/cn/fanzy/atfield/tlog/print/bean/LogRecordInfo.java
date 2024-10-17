package cn.fanzy.atfield.tlog.print.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志记录信息
 *
 * @author Administrator
 * @date 2024/10/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRecordInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 8762531033810556200L;

    /**
     * 商业否
     */
    private String bizNo;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 操作员 ID
     */
    private String operatorId;
    /**
     * 运算符名称
     */
    private String operatorName;
    /**
     * 操作类型
     */
    private String operateType;
    /**
     * 内容
     */
    private String content;
    /**
     * 额外
     */
    private String extra;
    /**
     * 操作员 IP
     */
    private String operatorIp;

    /**
     * 工作时间
     */
    private LocalDateTime operateTime;


}
