package cn.fanzy.atfield.satoken.login.property;

import cn.fanzy.atfield.captcha.enums.CaptchaType;
import cn.fanzy.atfield.captcha.enums.ICaptchaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 登录配置类
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "atfield.login")
public class LoginProperty {
    private String prefix = "atfield-login:";

    /**
     * 重试登录次数，默认：-1-不限制
     * 1. 小于0=不限制。
     * 2. 0-禁止所有登录。
     * 3. 大于0-登录失败后，允许重试登录次数。
     */
    private Integer retryCount = -1;

    /**
     * 显示验证码的时机，默认：3-登录失败3次后，出现验证码
     * 1. 小于0=不显示验证码。
     * 2. 0 - 一直显示验证码
     * 2. 大于0-登录失败x后，允许显示验证码。
     */
    private Integer showCaptchaCount = 3;

    /**
     * 锁定秒数,默认：24小时。
     */
    private Integer lockSeconds = 24 * 60 * 60;

    /**
     * 验证码类型，默认：图片
     */
    private ICaptchaType captchaType = CaptchaType.IMAGE;


    /**
     * 基于IP隔离，只锁定该IP，其他IP仍然可以登录。默认：true
     * false-全局锁定。
     */
    private boolean isolationForIp = true;

}
