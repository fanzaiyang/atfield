package cn.fanzy.breeze.web.logs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeRequestArgs {
    /**
     * 业务名称
     */
    private String bizName;
    /**
     * 线程ID
     */
    private String traceId;
    /**
     * 客户端IP
     */
    private String clientIp;
    /**
     * 当前用户信息
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

}
