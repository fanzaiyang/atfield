package cn.fanzy.atfield.satoken.login.model;

import cn.fanzy.atfield.satoken.login.property.LoginProperty;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录AOP信息数据传送对象
 *
 * @author fanzaiyang
 * @date 2024/01/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginAopInfoDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -1393368949137783896L;

    /**
     * 登录密钥
     */
    private String loginKey;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 客户端 IP
     */
    private String clientIp;

    /**
     * 错误计数
     */
    private int errorCount;

    /**
     * 存储密钥
     */
    private String storageKey;

    /**
     * 重试登录计数
     */
    private int retryCount;
    /**
     * 显示验证码计数
     */
    private Integer showCaptchaCount;
    /**
     * 锁定秒数
     */
    private Integer lockSeconds;

    /**
     * 锁定时间
     */
    private Date lockTime;

    public String getUnlockTime() {
        return DateUtil.offsetSecond(lockTime, getLockSeconds()).toString("yyyy年MM月dd日 HH点mm分");
    }

    public boolean isShowCaptcha() {
        return showCaptchaCount >= 0;
    }
}
