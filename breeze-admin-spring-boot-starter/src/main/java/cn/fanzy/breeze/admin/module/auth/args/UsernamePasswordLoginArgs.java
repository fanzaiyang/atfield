package cn.fanzy.breeze.admin.module.auth.args;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsernamePasswordLoginArgs {
    @NotBlank(message = "用户名不能为空！")
    private String username;
    @NotBlank(message = "密码不能为空！")
    private String password;
    private String code;
    private String clientId;
}
