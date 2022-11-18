package cn.fanzy.breeze.admin.module.system.account.args;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BreezeAdminAccountPwdChangeArgs {
    private String id;
    @NotBlank(message = "新密码不能为空！")
    private String password;
}
