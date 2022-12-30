package cn.fanzy.breeze.admin.module.auth.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UsernamePasswordLoginArgs {
    @NotBlank(message = "用户名不能为空！")
    @Schema(description = "用户名")
    private String username;
    @NotBlank(message = "密码不能为空！")
    @Schema(description = "密码")
    private String password;
    @Schema(description = "验证码")
    private String code;
    @Schema(description = "客户端唯一ID")
    private String clientId;
}
