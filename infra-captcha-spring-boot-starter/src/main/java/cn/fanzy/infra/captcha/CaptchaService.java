package cn.fanzy.infra.captcha;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;

/**
 * 验证码服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface CaptchaService{

    /**
     * 创建并发送
     *
     * @param type   类型
     * @param target 目标
     * @return {@link CaptchaCode}
     */
    CaptchaCode createAndSend(CaptchaType type, String target);

    /**
     * 验证
     *
     * @param type   类型
     * @param target 目标
     * @param code   验证码
     * @return boolean
     */
    boolean verify(CaptchaType type, String target, String code);
}
