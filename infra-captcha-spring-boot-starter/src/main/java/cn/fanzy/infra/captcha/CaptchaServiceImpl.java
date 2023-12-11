package cn.fanzy.infra.captcha;

import cn.fanzy.infra.captcha.bean.CaptchaCode;
import cn.fanzy.infra.captcha.bean.CaptchaCodeInfo;
import cn.fanzy.infra.captcha.creator.CaptchaCreatorService;
import cn.fanzy.infra.captcha.enums.CaptchaType;
import cn.fanzy.infra.captcha.enums.ICaptchaType;
import cn.fanzy.infra.captcha.exception.CaptchaExpiredException;
import cn.fanzy.infra.captcha.exception.NoCaptchaException;
import cn.fanzy.infra.captcha.property.CaptchaProperty;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import cn.fanzy.infra.captcha.storage.CaptchaStorageService;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class CaptchaServiceImpl implements CaptchaService {
    private final List<CaptchaCreatorService> creatorServiceList;
    private final List<CaptchaSenderService> senderServiceList;
    private final CaptchaStorageService captchaStorageService;
    private final CaptchaProperty property;

    @Override
    public CaptchaCode createAndSend(ICaptchaType type, String target) {
        Optional<CaptchaCreatorService> creatorService = creatorServiceList.stream()
                .filter(creator -> creator.isSupported(type)).findFirst();
        if (creatorService.isEmpty()) {
            throw new RuntimeException("该类型验证码的生成器不存在！");
        }
        Optional<CaptchaSenderService> senderService = senderServiceList.stream()
                .filter(sender -> sender.isSupported(type)).findFirst();
        if (senderService.isEmpty()) {
            throw new RuntimeException("该类型验证码的发送器不存在！");
        }
        CaptchaCode codeInfo = creatorService.get().generate(property);
        captchaStorageService.save(target, codeInfo);
        senderService.get().send(target, codeInfo);
        return codeInfo;
    }

    @Override
    public void verify(CaptchaType type, String target, String code) {
        CaptchaCode captchaCode = captchaStorageService.get(target, CaptchaCode.class);
        if (captchaCode == null) {
            throw new NoCaptchaException("-5001", "验证码不存在或已过期！");
        }
        if (captchaCode.isExpired()) {
            throw new CaptchaExpiredException("-5002", "验证码已过期！");
        }
        // 次数+1
        captchaCode.setUseCountIncrease();
        captchaStorageService.save(target, captchaCode);
        if (property.isEqualsIgnoreCase()) {
            if (StrUtil.equalsIgnoreCase(code, captchaCode.getCode())) {
                captchaStorageService.delete(target);
            }else{
                throw new CaptchaExpiredException("-5003", "验证码输入错误！");
            }
        } else {
            if (code.equals(captchaCode.getCode())) {
                captchaStorageService.delete(target);
            }else{
                throw new CaptchaExpiredException("-5003", "验证码输入错误！");
            }
        }
    }
}
