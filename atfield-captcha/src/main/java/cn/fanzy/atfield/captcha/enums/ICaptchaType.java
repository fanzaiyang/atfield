package cn.fanzy.atfield.captcha.enums;

/**
 * 验证码类型
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface ICaptchaType {

    /**
     * 获取价值
     *
     * @return {@link String}
     */
    String getValue();


    /**
     * 验证码名称
     *
     * @return {@link String}
     */
    default String getCaptchaName() {
        return "";
    }
}
