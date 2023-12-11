package cn.fanzy.infra.captcha.creator;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.enums.ICaptchaType;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.storage.CaptchaStorageService;
import cn.fanzy.infra.core.spring.SpringUtils;
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
