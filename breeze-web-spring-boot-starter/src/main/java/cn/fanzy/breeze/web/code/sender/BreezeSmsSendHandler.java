package cn.fanzy.breeze.web.code.sender;

import org.springframework.web.context.request.ServletWebRequest;

public interface BreezeSmsSendHandler {

    /**
     * 发送
     *
     * @param mobile  移动
     * @param code    代码
     * @param request 请求
     */
    void send(String mobile, String code, ServletWebRequest request);
}
