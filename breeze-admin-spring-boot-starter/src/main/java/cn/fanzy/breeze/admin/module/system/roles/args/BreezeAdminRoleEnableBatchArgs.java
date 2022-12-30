package cn.fanzy.breeze.admin.module.system.roles.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BreezeAdminRoleEnableBatchArgs {
    @NotEmpty(message = "角色ID集合不能为空！")
    @Schema(description = "主键集合")
    private List<String> idList;
}
