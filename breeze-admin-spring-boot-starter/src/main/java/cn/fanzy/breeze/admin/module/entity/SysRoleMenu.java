package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;

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
@Entity(tableName = "sys_role_menu")
public class SysRoleMenu extends IBaseEntity {
    /**
     * 主键
     */
    @Id
    @Column(name = "id", type = Types.VARCHAR)
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 菜单ID
     */
    @Column(name = "menu_id", type = Types.VARCHAR)
    @ApiModelProperty(value = "菜单ID", position = 2)
    private String menuId;
    /**
     * 角色ID
     */
    @Column(name = "role_id", type = Types.VARCHAR)
    @ApiModelProperty(value = "角色ID", position = 3)
    private String roleId;
}

