package cn.fanzy.breeze.web.logs.model;

import cn.fanzy.breeze.web.logs.enums.LogTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

/**
 * @author fanzaiyang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeRequestArgs {

    /**
     * 应用程序id
     */
    private String appId;

    /**
     * 应用程序名称
     */
    private String appName;
    /**
     * 模块名称
     */
    private String module;
    /**
     * 业务名称
     */
    private String bizName;

    /**
     * 日志类型
     */
    private LogTypeEnum logType;
    /**
     * 线程ID
     */
    private String traceId;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户信息
     */
    private String userInfo;
    /**
     * 请求的URL
     */
    private String requestUrl;
    /**
     * 执行方式GET/POST
     */
    private String requestMethod;

    /**
     * 执行的方法「类.方法」
     */
    private String classMethod;
    /**
     * 请求参数
     */
    private Map<String, Object> requestData;

    /**
     * 响应结果
     */
    private Object responseData;


    /**
     * 请求开始时间
     */
    private Date startTime;

    /**
     * 请求结束时间
     */
    private Date endTime;

    /**
     * 消耗时间，单位：秒
     */
    private long proceedSecond;

    /**
     * 是否成功。
     */
    private boolean success;

    private boolean ignore;

    public LogTypeEnum getLogType() {
        if(logType==null){
            return LogTypeEnum.NONE;
        }
        return logType;
    }
}
