package cn.fanzy.infra.captcha.sender;

import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.hutool.core.util.RandomUtil;

/**
 * 创建验证码的服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface CaptchaSenderService<T extends CaptchaCodeInfo> {

    void send(String target,T codeInfo);

}
