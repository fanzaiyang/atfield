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
 * 组织表(SysOrg)表实体类
 *
 * @author fasnzaiyang
 * @since 2021-09-27 18:09:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "SysOrg", description = "组织表")
@Entity(tableName = "sys_org")
public class SysOrg extends IBaseEntity {
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id",type = Types.VARCHAR)
    @ApiModelProperty(value = "", position = 1)
    private String id;
    /**
     * 编码
     */
    @Column(name = "code",type = Types.VARCHAR)
    @ApiModelProperty(value = "编码", position = 2)
    private String code;
    /**
     * 名称
     */
    @Column(name = "name",type = Types.VARCHAR)
    @ApiModelProperty(value = "名称", position = 3)
    private String name;
    /**
     * 简称
     */
    @Column(name = "short_name",type = Types.VARCHAR)
    @ApiModelProperty(value = "简称", position = 3)
    private String shortName;
    /**
     * 上级ID
     */
    @Column(name = "parent_id",type = Types.VARCHAR)
    @ApiModelProperty(value = "上级ID", position = 5)
    private String parentId;
    /**
     * 父节点编码
     */
    @Column(name = "parent_code",type = Types.VARCHAR)
    @ApiModelProperty(value = "父节点编码", position = 6)
    private String parentCode;
    /**
     * 类别:corp=公司，dept=部门，job=职务
     */
    @Column(name = "org_type",type = Types.VARCHAR)
    @ApiModelProperty(value = "类别:corp=公司，dept=部门，job=职务", position = 7)
    private String orgType;
    /**
     * 上级全路径
     */
    @Column(name = "node_route",type = Types.LONGVARCHAR)
    @ApiModelProperty(value = "上级全路径", position = 8)
    private String nodeRoute;
    /**
     * 级别
     */
    @Column(name = "node_level",type = Types.SMALLINT)
    @ApiModelProperty(value = "级别", position = 9)
    private Integer nodeLevel;
    /**
     * 排序序号
     */
    @Column(name = "order_number",type = Types.INTEGER)
    @ApiModelProperty(value = "排序序号", position = 10)
    private Integer orderNumber;
    /**
     * 叶子节点：1-是，0-否
     */
    @Column(name = "is_leaf",type = Types.SMALLINT)
    @ApiModelProperty(value = "叶子节点：1-是，0-否", position = 11)
    private Integer isLeaf;
    /**
     * 备注
     */
    @Column(name = "remarks",type = Types.VARCHAR)
    @ApiModelProperty(value = "备注", position = 12)
    private String remarks;
    /**
     * 状态，1-正常，0-禁用
     */
    @Column(name = "status",type = Types.SMALLINT)
    @ApiModelProperty(value = "状态，1-正常，0-禁用", position = 14)
    private Integer status;
}
