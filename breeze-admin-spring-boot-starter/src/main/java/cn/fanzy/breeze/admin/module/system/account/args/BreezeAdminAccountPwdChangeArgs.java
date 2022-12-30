package cn.fanzy.breeze.admin.module.system.account.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BreezeAdminAccountPwdChangeArgs {
    @NotBlank(message = "账户ID不能为空！")
    @Schema(description = "账户ID")
    private String id;
    @NotBlank(message = "旧密码不能为空！")
    @Schema(description = "旧密码")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空！")
    @Schema(description = "新密码")
    private String newPassword;

}
