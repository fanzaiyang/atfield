package cn.fanzy.field.captcha.util;

import cn.fanzy.field.captcha.enums.CaptchaType;
import cn.fanzy.field.captcha.enums.ICaptchaType;
import cn.fanzy.field.captcha.property.CaptchaProperty;

/**
 * captcha型util
 *
 * @author fanzaiyang
 * @date 2023/12/11
 */
public class CaptchaTypeUtil {

    public static String getCodeKey(ICaptchaType captchaType, CaptchaProperty property) {
        if (CaptchaType.IMAGE.equals(captchaType)) {
            return property.getImage().getCodeKey();
        }
        if (CaptchaType.MOBILE.equals(captchaType)) {
            return property.getMobile().getCodeKey();
        }
        if (CaptchaType.EMAIL.equals(captchaType)) {
            return property.getEmail().getCodeKey();
        }
        return null;
    }
    public static String getCodeValue(ICaptchaType captchaType, CaptchaProperty property) {
        if (CaptchaType.IMAGE.equals(captchaType)) {
            return property.getImage().getCodeValue();
        }
        if (CaptchaType.MOBILE.equals(captchaType)) {
            return property.getMobile().getCodeValue();
        }
        if (CaptchaType.EMAIL.equals(captchaType)) {
            return property.getEmail().getCodeValue();
        }
        return null;
    }
}
