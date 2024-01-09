package cn.fanzy.field.captcha.creator;

import cn.fanzy.field.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.field.captcha.enums.CaptchaType;
import cn.fanzy.field.captcha.enums.ICaptchaType;
import cn.fanzy.field.captcha.property.CaptchaProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 图像创建者服务impl
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Slf4j
@RequiredArgsConstructor
public abstract class CaptchaImageCreatorService implements CaptchaCreatorService {

    public abstract CaptchaImageCodeInfo generateCode(CaptchaProperty captchaProperty);

    @Override
    public CaptchaImageCodeInfo generate(CaptchaProperty captchaProperty) {
        return generateCode(captchaProperty);
    }

    @Override
    public boolean isSupported(ICaptchaType type) {
        return CaptchaType.IMAGE.equals(type);
    }

}
