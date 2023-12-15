package cn.fanzy.infra.captcha.enums;

/**
 * 验证码类型
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface ICaptchaType {

    String getValue();

    ICaptchaType getType(String value);
}
