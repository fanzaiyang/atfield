package cn.fanzy.infra.captcha.enums;

import cn.hutool.core.util.StrUtil;

/**
 * 验证码类型
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public enum CaptchaType implements ICaptchaType {
    /**
     * 图片验证码
     */
    IMAGE,
    /**
     * 手机验证码
     */
    MOBILE,
    /**
     * 电子邮件验证码
     */
    EMAIL;

    @Override
    public String getValue() {
        return this.name();
    }

    public static ICaptchaType getType(String value) {
        for (CaptchaType type : CaptchaType.values()) {
            if(StrUtil.equalsIgnoreCase(type.getValue(),value)){
                return type;
            }
        }
        throw new IllegalArgumentException("验证码类型不存在!");
    }
}
