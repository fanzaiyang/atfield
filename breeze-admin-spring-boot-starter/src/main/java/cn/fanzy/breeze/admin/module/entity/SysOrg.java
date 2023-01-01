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
 * 组织表(SysOrg)表实体类
 *
 * @author fanzaiyang
 * @since 2021-09-27 18:09:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "组织表")
@Entity(tableName = "sys_org")
public class SysOrg extends IBaseEntity {
    private static final long serialVersionUID = -7727798661931138108L;
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id",type = Types.VARCHAR)
    @Schema(description = "id")
    private String id;
    /**
     * 编码
     */
    @Column(name = "code",type = Types.VARCHAR)
    @Schema(description = "编码")
    private String code;
    /**
     * 名称
     */
    @Column(name = "name",type = Types.VARCHAR)
    @Schema(description = "名称")
    private String name;
    /**
     * 简称
     */
    @Column(name = "short_name",type = Types.VARCHAR)
    @Schema(description = "简称")
    private String shortName;
    /**
     * 上级ID
     */
    @Column(name = "parent_id",type = Types.VARCHAR)
    @Schema(description = "上级ID")
    private String parentId;
    /**
     * 父节点编码
     */
    @Column(name = "parent_code",type = Types.VARCHAR)
    @Schema(description = "父节点编码")
    private String parentCode;
    /**
     * 类别:corp=公司，dept=部门，job=职务
     */
    @Column(name = "org_type",type = Types.VARCHAR)
    @Schema(description = "类别:corp=公司，dept=部门，job=职务")
    private String orgType;
    /**
     * 上级全路径
     */
    @Column(name = "node_route",type = Types.LONGVARCHAR)
    @Schema(description = "上级全路径")
    private String nodeRoute;
    /**
     * 级别
     */
    @Column(name = "node_level",type = Types.SMALLINT)
    @Schema(description = "级别")
    private Integer nodeLevel;
    /**
     * 排序序号
     */
    @Column(name = "order_number",type = Types.INTEGER)
    @Schema(description = "排序序号")
    private Integer orderNumber;
    /**
     * 叶子节点：1-是，0-否
     */
    @Column(name = "is_leaf",type = Types.SMALLINT)
    @Schema(description = "叶子节点：1-是，0-否")
    private Integer isLeaf;
    /**
     * 备注
     */
    @Column(name = "remarks",type = Types.VARCHAR)
    @Schema(description = "备注")
    private String remarks;
    /**
     * 状态，1-正常，0-禁用
     */
    @Column(name = "status",type = Types.SMALLINT)
    @Schema(description = "状态，1-正常，0-禁用")
    private Integer status;
}
