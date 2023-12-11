package cn.fanzy.infra.captcha;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.enums.ICaptchaType;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 验证码服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface CaptchaService {

    /**
     * 创建并发送
     *
     * @param type   类型{@link ICaptchaType}
     * @param target 目标
     * @return {@link CaptchaCode}
     */
    CaptchaCode createAndSend(ICaptchaType type, String target);

    /**
     * 创建并发送
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
     * @param captchaType   类型
     * @param target 目标
     * @param code   验证码
     */
    void verify(ICaptchaType captchaType, String target, String code);

    /**
     * 验证
     *
     * @param captchaType    类型
     * @param request 要求
     */
    void verify(ICaptchaType captchaType, HttpServletRequest request);
}
