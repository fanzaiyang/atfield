package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 角色菜单表(SysRoleMenu)表实体类
 *
 * @author fasnzaiyang
 * @since 2021-09-27 18:09:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysRoleMenu", description = "角色菜单表")
public class SysRoleMenu extends IBaseEntity {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID", position = 2)
    private String menuId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", position = 3)
    private String roleId;

}

