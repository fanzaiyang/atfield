package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;

/**
 * 角色菜单表(SysRoleMenu)表实体类
 *
 * @author fanzaiyang
 * @since 2021-09-27 18:09:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色菜单表")
@Entity(tableName = "sys_role_menu")
public class SysRoleMenu extends IBaseEntity {
    private static final long serialVersionUID = 8357831398457392327L;
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id", type = Types.VARCHAR)
    @Schema(description = "主键")
    private String id;
    /**
     * 菜单ID
     */
    @Column(name = "menu_id", type = Types.VARCHAR)
    @Schema(description = "菜单ID")
    private String menuId;
    /**
     * 角色ID
     */
    @Column(name = "role_id", type = Types.VARCHAR)
    @Schema(description = "角色ID")
    private String roleId;
}

