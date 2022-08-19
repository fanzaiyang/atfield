/**
 *
 */
package cn.fanzy.breeze.web.code.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 验证码属性配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.code")
public class BreezeCodeProperties {
    private Boolean enable=false;
    /**
     * 将验证码存储到Redis时的key的前缀，默认值为 validate_code_
     */
    private String prefix = "validate_code_";

    /**
     * 是否显示加载日志，默认为false
     */
    private Boolean showLog = false;

    /**
     * 是否在验证成功后删除验证过的验证码
     */
    protected Boolean deleteOnSuccess = true;

    /**
     * 重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
     */
    private Integer retryCount = 1;
    /**
     * 图形验证码相关的配置
     */
    protected ImageCodeProperties image = new ImageCodeProperties();
    /**
     * 短信验证码相关的配置
     */
    protected SmsCodeProperties sms = new SmsCodeProperties();
    /**
     * 邮箱验证码的相关配置
     */
    protected EmailCodeProperties email = new EmailCodeProperties();

    /**
     * 短信验证码的配置参数
     *
     * @author fanzaiyang
     */
    @Data
    public static class SmsCodeProperties {
        /**
         * 验证码的长度,默认为4
         */
        private Integer length = 4;
        /**
         * 验证码的失效时间，单位秒，默认为300s
         */
        private Integer expireIn = 300;
        /**
         * 验证码是否包含字母,默认不包含
         */
        private Boolean containLetter = false;
        /**
         * 验证码是否包含数字,默认包含
         */
        private Boolean containNumber = true;

        /**
         * 从请求中获取短信验证码的发送目标(手机号)的参数，默认值为 phone
         */
        private String codeKey = "phone";
        /**
         * 请求中获取短信验证码对应的短信内容的参数，默认值为 phone_code
         */
        private String codeValue = "code";

        /**
         * 重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
         */
        private Integer retryCount;

    }

    /**
     * 邮件验证码相关的配置
     *
     * @author fanzaiyang
     */
    @Data
    public static class EmailCodeProperties {

        /**
         * 验证码邮箱的内容模板
         */
        protected String contentTemplate = "您的验证码的内容为{0} ,验证码的有效时间为 {1} 秒";

        /**
         * 验证码邮箱的标题
         */
        protected String title = "帐号保护验证";

        /**
         * 从请求中获取邮件验证码的邮箱的参数，默认值为 email
         */
        protected String codeKey = "email";
        /**
         * 请求中获取邮件验证码对应的值的参数，默认值为 emailCode
         */
        protected String codeValue = "code";
        /**
         * 验证码的长度,默认为4
         */
        private Integer length = 4;
        /**
         * 验证码的失效时间，单位秒，默认为300s
         */
        private Integer expireIn = 300;
        /**
         * 验证码是否包含字母,默认不包含
         */
        private Boolean containLetter = false;
        /**
         * 验证码是否包含数字,默认包含
         */
        private Boolean containNumber = true;

        /**
         * 重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
         */
        private Integer retryCount;

        private String senderAddress;

    }

    /**
     * 图形验证码的参数配置
     *
     * @author fanzaiyang
     */
    @Data
    public static class ImageCodeProperties {
        /**
         * 从请求中获取邮件验证码的邮箱的参数，默认值为 email
         */
        protected String codeKey = "image";
        /**
         * 请求中获取邮件验证码对应的值的参数，默认值为 imageCode
         */
        protected String codeValue = "code";
        /**
         * 验证码的长度,默认为4
         */
        private Integer length = 4;
        /**
         * 验证码的失效时间，单位秒，默认为300s
         */
        private Integer expireIn = 300;
        /**
         * 验证码是否包含字母,默认不包含
         */
        private Boolean containLetter = false;
        /**
         * 验证码是否包含数字,默认包含
         */
        private Boolean containNumber = true;

        /**
         * 重试计数,默认1，即验证次数超过此参数时，删除验证码，需要使用新码。
         */
        private Integer retryCount;
        /**
         * 验证码的宽度,默认为70
         */
        protected Integer width = 70;
        /**
         * 验证码的高度,默认为 28
         */
        protected Integer height = 28;

        /**
         * 是否生成干扰条纹背景，默认为false
         */
        protected Boolean fringe = false;
    }

}
