package cn.fanzy.breeze.admin.module.system.roles.args;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminRoleSaveArgs {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识", position = 2)
    private String code;
    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称", position = 3)
    private String name;
    /**
     * 备注说明
     */
    @ApiModelProperty(value = "备注说明", position = 4)
    private String remarks;
    /**
     * 数据范围
     */
    @ApiModelProperty(value = "数据范围（ALL：全部数据权限 CUSTOM：自定数据权限 DEPT：本部门数据权限 DEPT_ALL：本部门及以下数据权限）", position = 6)
    private String dataScope;

    @ApiModelProperty(value = "上级ID", position = 6)
    private String parentId;

    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}
