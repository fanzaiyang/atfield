/**
 *
 */
package cn.fanzy.breeze.web.code.enums;


import org.springframework.util.Assert;


/**
 * 验证码类型
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
public enum BreezeCodeType {

    /**
     * 短信验证码
     */
    SMS("短信验证码"),
    /**
     * 图片验证码
     */
    IMAGE("图片验证码"),
    /**
     * 邮件验证码
     */
    EMAIL("邮件验证码");

    /**
     * 类型名称
     */
    private final String name;

    private BreezeCodeType(String name) {
        this.name = name;
    }

    /**
     * 根据名字解析出验证码类型
     *
     * @param code 名字
     * @return 验证码类型
     */
    public static BreezeCodeType parse(String code) {
        Assert.notNull(code, "参数不能为空");
        BreezeCodeType type = null;
        switch (code.trim()) {
            case "SMS":
                type = BreezeCodeType.SMS;
                break;
            case "IMAGE":
                type = BreezeCodeType.IMAGE;
                break;
            case "EMAIL":
                type = BreezeCodeType.EMAIL;
                break;
            default:
                break;
        }
        return type;
    }

    /**
     * 获取类型名称
     *
     * @return 类型名称
     */
    public String getName() {
        return name;
    }

}
