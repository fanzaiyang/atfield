package com.example.breeze.web.auth;

import cn.fanzy.breeze.web.sensitive.BreezeSensitive;
import cn.fanzy.breeze.web.sensitive.BreezeSensitiveEnum;
import lombok.Data;

@Data
public class SysUser {
    private String id;
    /**
     * 姓名第一位脱敏(不考虑复姓，特殊姓氏)
     * 如：李**
     */
    @BreezeSensitive(BreezeSensitiveEnum.NAME)
    private String name;
    /**
     * 银行卡脱敏，保留前4位和后4位，中间*代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.BANK_NUMBER)
    private String bankNum;
    /**
     * 身份证脱敏，保留前3位和后4位，中间*代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.ID_CARD)
    private String idnum;
    /**
     * 手机号码脱敏，前三后四脱敏，中间*代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.MOBILE_PHONE)
    private String phone;

    /**
     * 密码脱敏：***代替
     */
    @BreezeSensitive(BreezeSensitiveEnum.PASSWORD)
    private String password;
}
