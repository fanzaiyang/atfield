package cn.fanzy.breeze.web.code.sender.impl;


import cn.fanzy.breeze.web.code.model.BreezeImageCode;
import cn.fanzy.breeze.web.code.sender.BreezeCodeSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;


/**
 * 图形验证码发送器
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
public class BreezeImageCodeSender implements BreezeCodeSender<BreezeImageCode> {

    @Override
    public void send(ServletWebRequest request, String target, BreezeImageCode code) {
        try {
            log.debug("【图形验证码发送器】向客户端 {} 发送验证码，验证码的内容为 {} ", target, code.getCode());
            ImageIO.write(code.getImage(), "JPEG", request.getResponse().getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
