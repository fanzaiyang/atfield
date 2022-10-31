package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;

/**
 * 系统角色表(SysRole)表实体类
 *
 * @author fasnzaiyang
 * @since 2021-09-27 18:09:30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysRole", description = "系统角色表")
@Entity(tableName = "sys_role")
public class SysRole extends IBaseEntity {
    private static final long serialVersionUID = -8702497642451739792L;
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id",type = Types.VARCHAR)
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 角色标识
     */
    @Column(name = "code",type = Types.VARCHAR)
    @ApiModelProperty(value = "角色标识", position = 2)
    private String code;
    /**
     * 角色名称
     */
    @Column(name = "name",type = Types.VARCHAR)
    @ApiModelProperty(value = "角色名称", position = 3)
    private String name;
    /**
     * 备注说明
     */
    @Column(name = "remarks",type = Types.VARCHAR)
    @ApiModelProperty(value = "备注说明", position = 4)
    private String remarks;
    /**
     * 状态;状态，0-禁用，1-启用
     */
    @Column(name = "status",type = Types.SMALLINT,defaultValue = "1")
    @ApiModelProperty(value = "状态;状态，0-禁用，1-启用", position = 5)
    private Integer status;

    /**
     * 数据范围
     */
    @Column(name = "data_scope",type = Types.VARCHAR)
    @ApiModelProperty(value = "数据范围（ALL：全部数据权限 CUSTOM：自定数据权限 DEPT：本部门数据权限 DEPT_ALL：本部门及以下数据权限）", position = 6)
    private String dataScope;

    @Column(name = "parent_id",type = Types.VARCHAR)
    private String parentId;

    @Column(name = "order_number", type = Types.INTEGER)
    @ApiModelProperty(value = "序号", position = 11)
    private Integer orderNumber;

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
}

