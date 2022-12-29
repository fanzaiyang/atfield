package cn.fanzy.breeze.admin.module.system.roles.args;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminRoleSaveArgs {
    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;
    /**
     * 角色标识
     */
    @Schema(description = "角色标识")
    private String code;
    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;
    /**
     * 备注说明
     */
    @Schema(description = "备注说明")
    private String remarks;
    /**
     * 数据范围
     */
    @Schema(description = "数据范围（ALL：全部数据权限 CUSTOM：自定数据权限 DEPT：本部门数据权限 DEPT_ALL：本部门及以下数据权限）")
    private String dataScope;

    @Schema(description = "上级ID")
    private String parentId;

    private List<String> menuIdList;

    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}
