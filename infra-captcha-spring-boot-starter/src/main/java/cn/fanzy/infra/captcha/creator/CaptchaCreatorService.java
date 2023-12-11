package cn.fanzy.infra.captcha.creator;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.enums.ICaptchaType;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.hutool.core.util.RandomUtil;

/**
 * 创建验证码的服务
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
public interface CaptchaCreatorService{

    CaptchaCode generate(CaptchaProperty captchaProperty);


    /**
     * 支持
     *
     * @param type 类型{@link ICaptchaType}
     * @return boolean
     */
    boolean isSupported(ICaptchaType type);
    default String getRandomCode(int length, boolean containLetter, boolean containNumber) {
        if (containLetter && containNumber) {
            // 包含字符和数字
            return RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER_LOWER, length);
        }
        if (containLetter) {
            // 包含字符,且不包含数字
            return RandomUtil.randomString(length);
        }
        if (containNumber) {
            // 不包含字符,且包含数字
            return RandomUtil.randomNumbers(length);
        }
        // 不包含字符,不包含数字
        throw new RuntimeException("验证码配置错误!");
    }
}
