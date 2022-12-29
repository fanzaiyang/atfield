package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;

/**
 * 系统菜单表(SysMenu)表实体类
 *
 * @author fasnzaiyang
 * @since 2021-09-27 18:09:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity(tableName = "sys_menu")
@Schema(description = "系统菜单表")
public class SysMenu extends IBaseEntity {
    private static final long serialVersionUID = 663922890702276220L;
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id", type = Types.VARCHAR)
    @Schema(description = "主键")
    private String id;
    /**
     * 菜单标识
     */
    @Column(name = "code", type = Types.VARCHAR)
    @Schema(description = "菜单标识")
    private String code;
    /**
     * 菜单标题
     */
    @Column(name = "title", type = Types.VARCHAR)
    @Schema(description = "菜单标题")
    private String title;
    /**
     * 菜单图标
     */
    @Column(name = "icon", type = Types.VARCHAR)
    @Schema(description = "菜单图标")
    private String icon;
    /**
     * 上级菜单ID
     */
    @Column(name = "parent_id", type = Types.VARCHAR)
    @Schema(description = "上级菜单ID")
    private String parentId;
    /**
     * 菜单类型;menu-菜单，button-按钮
     */
    @Column(name = "menu_type", type = Types.VARCHAR)
    @Schema(description = "菜单类型;M目录 C菜单 F按钮")
    private String menuType;
    /**
     * 是否隐藏;0-否，1-是
     */
    @Column(name = "hidden", type = Types.INTEGER, defaultValue = "0")
    @Schema(description = "是否隐藏;0-否，1-是")
    private Integer hidden;
    /**
     * 菜单路径
     */
    @Column(name = "uri", type = Types.VARCHAR)
    @Schema(description = "菜单路径")
    private String uri;
    /**
     * 备注
     */
    @Column(name = "remarks", type = Types.VARCHAR)
    @Schema(description = "备注")
    private String remarks;
    /**
     * 状态;0-禁用，1-启用
     */
    @Column(name = "status", type = Types.INTEGER, defaultValue = "1")
    @Schema(description = "状态;0-禁用，1-启用")
    private Integer status;

    @Column(name = "order_number", type = Types.INTEGER, defaultValue = "1")
    @Schema(description = "序号")
    private Integer orderNumber;

    /**
     * 权限标识
     */
    @Column(name = "permission_code", type = Types.VARCHAR)
    @Schema(description = "权限标识")
    private String permissionCode;

    /**
     * 权限标识
     */
    @Column(name = "callback_url", type = Types.VARCHAR)
    @Schema(description = "回调地址")
    private String callbackUrl;
    @Column(name = "auth_login_token_uri", type = Types.VARCHAR)
    @Schema(description = "临时token登录地址")
    private String authLoginTokenUri;
    @Column(name = "auth_user_check_uri", type = Types.VARCHAR)
    @Schema(description = "子系统用户验证地址")
    private String authUserCheckUri;

    @Column(name = "node_level", type = Types.INTEGER)
    @Schema(description = "等级")
    private Integer nodeLevel;
    /**
     * 所有上级ID
     */
    @Column(name = "node_route", type = Types.VARCHAR)
    @Schema(description = "所有上级ID")
    private String nodeRoute;
    /**
     * 是否是叶子节点
     */
    @Column(name = "is_leaf", type = Types.INTEGER)
    @Schema(description = "是否是叶子节点")
    private Integer isLeaf;

    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}

