package cn.fanzy.breeze.admin.module.auth.args;

import lombok.Data;

@Data
public class UsernamePasswordLoginArgs {
    private String username;
    private String password;
    private String code;
    private String clientId;
}
