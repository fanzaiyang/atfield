package cn.fanzy.atfield.captcha.creator;

import cn.fanzy.atfield.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.atfield.captcha.enums.CaptchaType;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;
import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 电子邮件创建者服务impl
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Slf4j
@RequiredArgsConstructor
public abstract class CaptchaEmailCreatorService implements CaptchaCreatorService {
    public abstract CaptchaCodeInfo generateCode(CaptchaProperty captchaProperty);
    @Override
    public CaptchaCodeInfo generate(CaptchaProperty captchaProperty){
        return generateCode(captchaProperty);
    }

    @Override
    public boolean isSupported(ICaptchaType type) {
        return CaptchaType.EMAIL.equals(type);
    }

}
