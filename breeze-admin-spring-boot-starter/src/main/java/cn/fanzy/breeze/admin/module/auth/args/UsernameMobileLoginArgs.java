package cn.fanzy.breeze.admin.module.auth.args;

import lombok.Data;

@Data
public class UsernameMobileLoginArgs {
    private String mobile;
    private String code;
}