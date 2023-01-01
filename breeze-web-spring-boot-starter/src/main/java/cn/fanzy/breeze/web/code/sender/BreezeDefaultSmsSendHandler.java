package cn.fanzy.breeze.web.code.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author fanzaiyang
 */
@Slf4j
public class BreezeDefaultSmsSendHandler implements BreezeSmsSendHandler {
    @Override
    public void send(String mobile, String code, ServletWebRequest request) {
        log.error("大哥，请实现发送短信验证码的逻辑。请实现接口BreezeSmsSendHandler，并注入到spring中。");
        throw new RuntimeException("未找到发送短信验证码的逻辑。");
    }
}
