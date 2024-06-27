package cn.fanzy.atfield.captcha;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;
import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 验证码服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 * @since 17
 */
public interface CaptchaService {

    /**
     * 创建二维码
     *
     * @param type   类型
     * @param target 目标
     * @return {@link CaptchaCode}
     */
    CaptchaCode create(ICaptchaType type, String target);

    /**
     * 获取验证码
     *
     * @param target 目标
     * @return {@link CaptchaCode}
     */
    CaptchaCode get(String target);

    /**
     * 邮寄
     * 发送验证码
     *
     * @param type        类型
     * @param target      目标
     * @param captchaCode 验证码
     */
    void send(ICaptchaType type, String target, CaptchaCode captchaCode);

    /**
     * 创建二维码后并发送
     *
     * @param type   类型{@link ICaptchaType}
     * @param target 目标
     * @return {@link CaptchaCode}
     */
    CaptchaCode createAndSend(ICaptchaType type, String target);

    /**
     * 创建二维码后并发送
     *
     * @param type     类型
     * @param target   目标
     * @param property 验证码配置
     * @return {@link CaptchaCode}
     */
    CaptchaCode createAndSend(ICaptchaType type, String target, CaptchaProperty property);

    /**
     * 验证
     *
     * @param captchaType 类型
     * @param target      目标
     * @param code        验证码
     */
    void verify(ICaptchaType captchaType, String target, String code);

    /**
     * 验证
     *
     * @param captchaType 类型
     * @param request     要求
     */
    void verify(ICaptchaType captchaType, HttpServletRequest request);
}
