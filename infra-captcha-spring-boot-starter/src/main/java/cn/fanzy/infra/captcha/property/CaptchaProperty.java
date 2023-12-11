package cn.fanzy.infra.captcha.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码配置类
 *
 * @author fanzaiyang
 * @date 2023/12/08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "infra.captcha")
public class CaptchaProperty {
    private String prefix = "infra-captcha:";
    /**
     * 过期秒,默认：60秒,-1不过期
     */
    private Integer expireSeconds = 60;

    private boolean equalsIgnoreCase = true;
    /**
     * 图片验证码配置
     */
    private Image image = new Image();
    /**
     * 手机验证码配置
     */
    private Mobile mobile = new Mobile();
    /**
     * 电子邮件配置
     */
    private Email email = new Email();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Image {

        /**
         * 宽度
         */
        private Integer width = 100;
        /**
         * 高度
         */
        private Integer height = 30;
        /**
         * 验证码长度
         */
        private Integer length = 4;
        /**
         * 包含数字
         */
        private boolean containsNumber = true;
        /**
         * 包含字母
         */
        private boolean containsLetter = false;
        /**
         * 从请求中获取短信验证码的发送目标(手机号)的参数，默认值：clientId
         */
        private String codeKey = "clientId";

        /**
         * 请求中获取短信验证码对应的短信内容的参数，默认值：code
         */
        private String codeValue = "code";
        /**
         * 重试次数,默认值：1，前端输入错误次数超过该值时，将不再重试，直接返回错误。
         * -1或0：在过期时间内，不限次数
         */
        private Integer retryCount = 1;
        /**
         * 过期秒,默认：60秒
         */
        private Integer expireSeconds = 60;
        /**
         * 是否生成干扰条纹背景，默认为false
         */
        private boolean fringe = false;

    }

    /**
     * 手机号验证码配置
     *
     * @author fanzaiyang
     * @date 2023/12/08
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Mobile {
        /**
         * 验证码的长度,默认为4
         */
        private Integer length = 6;
        /**
         * 验证码的失效时间，单位秒，默认:60s
         */
        private Integer expireSeconds = 60;
        /**
         * 验证码是否包含字母,默认不包含
         */
        private boolean containLetter = false;
        /**
         * 验证码是否包含数字,默认包含
         */
        private boolean containNumber = true;

        /**
         * 从请求中获取短信验证码的发送目标(手机号)的参数，默认值为 mobile
         */
        private String codeKey = "mobile";
        /**
         * 请求中获取短信验证码对应的短信内容的参数，默认值为 code
         */
        private String codeValue = "code";

        /**
         * 重试次数,默认值：1，前端输入错误次数超过该值时，将不再重试，直接返回错误。
         * -1或0：在过期时间内，不限次数
         */
        private Integer retryCount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Email {
        /**
         * 验证码的长度,默认为4
         */
        private Integer length = 4;
        /**
         * 验证码的失效时间，单位秒，默认:5分钟
         */
        private Integer expireSeconds = 300;
        /**
         * 验证码是否包含字母,默认不包含
         */
        private boolean containLetter = false;
        /**
         * 验证码是否包含数字,默认包含
         */
        private boolean containNumber = true;
        /**
         * 验证码邮箱的标题
         */
        private String emailTitle = "帐号保护验证";
        /**
         * 验证码邮箱的内容模板
         */
        private String contentTemplate = "您的验证码的内容为{0} ,验证码的有效时间为{1}。";
        /**
         * 重试次数,默认值：1，前端输入错误次数超过该值时，将不再重试，直接返回错误。
         * -1或0：在过期时间内，不限次数
         */
        private Integer retryCount;
        /**
         * 从请求中获取短信验证码的发送目标(手机号)的参数，默认值为 email
         */
        private String codeKey = "email";
        /**
         * 请求中获取短信验证码对应的短信内容的参数，默认值为 code
         */
        private String codeValue = "code";

    }
}
