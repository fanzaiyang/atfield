package cn.fanzy.atfield.captcha.sender.impl;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.bean.CaptchaImageCodeInfo;
import cn.fanzy.atfield.captcha.sender.CaptchaImageSenderService;
import cn.fanzy.atfield.core.spring.SpringUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 默认验证码图像发送器服务
 *
 * @author fanzaiyang
 * @date 2024/02/01
 */
@Slf4j
@RequiredArgsConstructor
public class DefaultCaptchaImageSenderService extends CaptchaImageSenderService {
    @Override
    public void sendCode(String target, CaptchaCode codeInfo) {
        HttpServletResponse response = SpringUtils.getResponse();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             OutputStream os = response.getOutputStream();) {
            CaptchaImageCodeInfo code = (CaptchaImageCodeInfo) codeInfo;
            log.debug("【图形验证码发送器】向客户端 {} 发送验证码，验证码的内容为 {} ", target, codeInfo.getCode());

            ImageIO.write(code.getImage(), "PNG", bos);

            byte[] bosByteArray = bos.toByteArray();
            response.setContentLength(bosByteArray.length);
            response.setContentType("image/png");
            os.write(bosByteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
