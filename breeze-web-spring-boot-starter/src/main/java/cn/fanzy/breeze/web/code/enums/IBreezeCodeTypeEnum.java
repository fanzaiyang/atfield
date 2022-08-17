package cn.fanzy.breeze.web.code.enums;

import java.io.Serializable;

public interface IBreezeCodeTypeEnum{
    /**
     * 获取验证码生成器
     * @return String
     */
    String getGenerator();

    /**
     * 获取验证码发送器
     * @return String
     */
    String getSender();
}

