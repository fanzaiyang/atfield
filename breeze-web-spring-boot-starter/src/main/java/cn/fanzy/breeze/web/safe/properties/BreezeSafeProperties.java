package cn.fanzy.breeze.web.safe.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.safe")
public class BreezeSafeProperties {
    /**
     * 登录ID的名称
     */
    private String loginKey = "username";
    /**
     * 仅单个IP限制。默认true
     */
    private boolean singleIp = true;
    /**
     * 保存登录次数的key前缀
     */
    private String loginFailedPrefix = "breeze_cloud:safe_auth:";

    /**
     * 登录失败有效期，默认24小时。
     */
    private int loginTimeoutSecond = 24 * 60 * 60;

    /**
     * 允许登录失败的次数。
     */
    private int loginFailedMaxNum = 5;
    /**
     * 是否需要验证码，默认：false
     */
    private boolean needCode = false;

    /**
     * 登录失败x次，后需启用验证码
     */
    private int loginFailedShowCodeMaxNum = 2;
}
