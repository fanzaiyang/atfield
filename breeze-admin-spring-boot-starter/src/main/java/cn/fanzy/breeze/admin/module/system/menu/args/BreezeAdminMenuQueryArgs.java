package cn.fanzy.breeze.admin.module.system.menu.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BreezeAdminMenuQueryArgs extends BaseArgs {
    @Schema(description = "菜单编码")
    private String code;
    @Schema(description = "菜单标题")
    private String title;
}
