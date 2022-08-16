package cn.fanzy.breeze.web.code.config;

import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import cn.fanzy.breeze.web.code.sender.BreezeCodeSender;
import cn.fanzy.breeze.web.code.sender.impl.BreezeEmailCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

/**
 * 邮箱验证码发送器自动配置
 *
 * @author fanzaiyang
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({MimeMessage.class, MimeType.class, JavaMailSender.class})
@ConditionalOnProperty(prefix = "spring.mail", name = {"host", "username", "password"})
public class BreezeMailExtendAutoConfiguration {

    /**
     * 注入一个名为emailCodeSender的邮箱验证码发送器
     *
     * @param env            环境配置
     * @param javaMailSender java邮件发送器
     * @param codeProperties 验证码属性配置
     * @return 名为emailCodeSender的邮箱验证码发送器
     */
    @Bean("emailCodeSender")
    @ConditionalOnMissingBean(name = "emailCodeSender")
    public BreezeCodeSender emailCodeSender(Environment env, JavaMailSender javaMailSender, BreezeCodeProperties codeProperties) {
        BreezeEmailCodeSender emailCodeSender = new BreezeEmailCodeSender();
        emailCodeSender.setCodeProperties(codeProperties);
        emailCodeSender.setEmailSender(env.getProperty("spring.mail.username"));
        emailCodeSender.setJavaMailSender(javaMailSender);
        return emailCodeSender;
    }
}
