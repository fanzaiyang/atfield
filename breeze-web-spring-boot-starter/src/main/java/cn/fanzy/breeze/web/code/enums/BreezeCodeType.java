/**
 *
 */
package cn.fanzy.breeze.web.code.enums;


/**
 * 验证码类型
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
public enum BreezeCodeType implements IBreezeCodeTypeEnum {

    /**
     * 短信验证码
     */
    SMS("breezeSmsCodeGenerator","breezeSmsCodeSender"),
    /**
     * 图片验证码
     */
    IMAGE("breezeImageCodeGenerator","breezeImageCodeSender"),
    /**
     * 邮件验证码
     */
    EMAIL("breezeEmailCodeGenerator","breezeEmailCodeSender");

    private final String generator;
    private final String sender;

    BreezeCodeType(String generator, String sender) {
        this.generator = generator;
        this.sender = sender;
    }

    @Override
    public String getGenerator() {
        return generator;
    }

    @Override
    public String getSender() {
        return sender;
    }
}
