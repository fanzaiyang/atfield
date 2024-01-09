package cn.fanzy.atfield.captcha.sender.impl;

import cn.fanzy.atfield.captcha.bean.CaptchaCode;
import cn.fanzy.atfield.captcha.property.CaptchaProperty;
import cn.fanzy.atfield.captcha.sender.CaptchaEmailSenderService;
import cn.fanzy.atfield.core.exception.GlobalException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Slf4j
@RequiredArgsConstructor
public class DefaultCaptchaEmailSenderService extends CaptchaEmailSenderService {
    private final JavaMailSender javaMailSender;
    private final CaptchaProperty property;
    @Value("${spring.mail.username}")
    private String from;
    @Override
    public void sendCode(String target, CaptchaCode codeInfo) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            log.debug("【邮箱验证码发送器】向客户端 {} 发送验证码，验证码的内容为 {} ", target, codeInfo.getCode());
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            // 发送方邮箱
            helper.setFrom(from);
            // 接收方邮箱
            helper.setTo(target);
            // 主题
            helper.setSubject(property.getEmail().getEmailTitle());
            // 生成邮件内容
            String content = buildContent(codeInfo);
            // 内容
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.warn("发送邮件验证码失败，失败的原因为 {}", e.getMessage());
            throw new GlobalException("-1", "邮件验证码发送失败！");
        }
    }
}
