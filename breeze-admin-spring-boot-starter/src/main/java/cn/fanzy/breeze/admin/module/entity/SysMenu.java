package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "SysMenu", description = "系统菜单表")
public class SysMenu extends IBaseEntity {
    /**
     * 主键
     */
    @Id(strategy = "generator", generator = "org.sagacity.sqltoy.plugins.id.impl.DefaultIdGenerator")
    @Column(name = "id", type = Types.VARCHAR)
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 菜单标识
     */
    @Column(name = "code", type = Types.VARCHAR)
    @ApiModelProperty(value = "菜单标识", position = 2)
    private String code;
    /**
     * 菜单标题
     */
    @Column(name = "title", type = Types.VARCHAR)
    @ApiModelProperty(value = "菜单标题", position = 3)
    private String title;
    /**
     * 菜单图标
     */
    @Column(name = "icon", type = Types.VARCHAR)
    @ApiModelProperty(value = "菜单图标", position = 4)
    private String icon;
    /**
     * 上级菜单ID
     */
    @Column(name = "parent_id", type = Types.VARCHAR)
    @ApiModelProperty(value = "上级菜单ID", position = 5)
    private String parentId;
    /**
     * 菜单类型;menu-菜单，button-按钮
     */
    @Column(name = "menu_type", type = Types.VARCHAR)
    @ApiModelProperty(value = "菜单类型;M目录 C菜单 F按钮", position = 6)
    private String menuType;
    /**
     * 是否隐藏;0-否，1-是
     */
    @Column(name = "hidden", type = Types.INTEGER)
    @ApiModelProperty(value = "是否隐藏;0-否，1-是", position = 7)
    private Integer hidden;
    /**
     * 菜单路径
     */
    @Column(name = "uri", type = Types.VARCHAR)
    @ApiModelProperty(value = "菜单路径", position = 8)
    private String uri;
    /**
     * 备注
     */
    @Column(name = "remarks", type = Types.VARCHAR)
    @ApiModelProperty(value = "备注", position = 9)
    private String remarks;
    /**
     * 状态;0-禁用，1-启用
     */
    @Column(name = "status", type = Types.INTEGER)
    @ApiModelProperty(value = "状态;0-禁用，1-启用", position = 10)
    private Integer status;
    @ApiModelProperty(value = "序号", position = 11)
    private Integer orderNumber;

    /**
     * 权限标识
     */
    @Column(name = "permission_code", type = Types.VARCHAR)
    @ApiModelProperty(value = "权限标识", position = 12)
    private String permissionCode;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "回调地址", position = 12)
    private String callbackUrl;
    @ApiModelProperty(value = "临时token登录地址", position = 13)
    private String authLoginTokenUri;
    @ApiModelProperty(value = "子系统用户验证地址", position = 14)
    private String authUserCheckUri;

    @Column(name = "node_level", type = Types.INTEGER)
    @ApiModelProperty(value = "等级", position = 6)
    private Integer nodeLevel;
    /**
     * 所有上级ID
     */
    @Column(name = "node_route", type = Types.VARCHAR)
    @ApiModelProperty(value = "所有上级ID", position = 7)
    private String nodeRoute;
    /**
     * 是否是叶子节点
     */
    @Column(name = "is_leaf", type = Types.INTEGER)
    @ApiModelProperty(value = "是否是叶子节点", position = 8)
    private Integer isLeaf;

    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}

