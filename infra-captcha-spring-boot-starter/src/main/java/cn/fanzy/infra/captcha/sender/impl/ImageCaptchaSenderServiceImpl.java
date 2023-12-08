package cn.fanzy.infra.captcha.sender.impl;

import cn.fanzy.infra.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.infra.captcha.sender.CaptchaSenderService;
import cn.fanzy.infra.core.spring.SpringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ImageCaptchaSenderServiceImpl implements CaptchaSenderService<CaptchaImageCodeInfo> {
    @Override
    public void send(String target, CaptchaImageCodeInfo codeInfo) {
        try {
            log.debug("【图形验证码发送器】向客户端 {} 发送验证码，验证码的内容为 {} ", target, codeInfo.getCode());
            ImageIO.write(codeInfo.getImage(), "PNG", SpringUtils.getResponse().getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
