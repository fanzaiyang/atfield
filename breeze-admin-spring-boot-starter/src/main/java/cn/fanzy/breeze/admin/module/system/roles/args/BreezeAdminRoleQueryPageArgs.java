package cn.fanzy.breeze.admin.module.system.roles.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminRoleQueryPageArgs extends BaseArgs {
    @Schema(description = "编码")
    private String code;
    @Schema(description = "名称")
    private String name;
}
