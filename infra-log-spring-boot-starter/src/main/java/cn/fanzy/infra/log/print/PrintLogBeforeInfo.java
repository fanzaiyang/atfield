package cn.fanzy.infra.log.print;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 打印日志bean
 *
 * @author fanzaiyang
 * @date 2023/12/05
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrintLogBeforeInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -4174632141880512798L;
    /**
     * 链路id
     */
    private String traceId;
    /**
     * 应用程序名称
     */
    private String appName;
    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 操作人UID
     */
    private String userId;

    /**
     * 操作人名称
     */
    private String userName;

    /**
     * 操作类型
     */
    private String operateType;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求类型;GET/POST...
     */
    private String requestType;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestData;

    /**
     * 请求时间
     */
    private LocalDateTime requestTime;

    /**
     * 响应时间
     */
    private LocalDateTime responseTime;

    /**
     * 备注
     */
    private String remarks;

    public enum ResponseStatus {
        /**
         * 成功
         */
        SUCCESS,
        /**
         * 失败
         */
        FAIL,
        /**
         * 错误
         */
        ERROR
    }
}
