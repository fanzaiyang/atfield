package cn.fanzy.breeze.admin.module.auth.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsernameMobileLoginArgs {
    @Schema(title = "手机号")
    private String mobile;
    @Schema(title = "验证码")
    private String code;
}