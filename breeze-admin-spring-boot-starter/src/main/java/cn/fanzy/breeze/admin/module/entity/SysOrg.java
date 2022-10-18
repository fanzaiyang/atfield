package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class SysOrg extends IBaseEntity {
    @ApiModelProperty(value = "", position = 1)
    private String id;
    /**
     * 编码
     */
    @ApiModelProperty(value = "编码", position = 2)
    private String code;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", position = 3)
    private String name;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称", position = 3)
    private String shortName;
    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID", position = 5)
    private String parentId;
    /**
     * 父节点编码
     */
    @ApiModelProperty(value = "父节点编码", position = 6)
    private String parentCode;
    /**
     * 类别:corp=公司，dept=部门，job=职务
     */
    @ApiModelProperty(value = "类别:corp=公司，dept=部门，job=职务", position = 7)
    private String orgType;
    /**
     * 上级全路径
     */
    @ApiModelProperty(value = "上级全路径", position = 8)
    private String nodeRoute;
    /**
     * 级别
     */
    @ApiModelProperty(value = "级别", position = 9)
    private Integer nodeLevel;
    /**
     * 排序序号
     */
    @ApiModelProperty(value = "排序序号", position = 10)
    private Integer orderNumber;
    /**
     * 叶子节点：1-是，0-否
     */
    @ApiModelProperty(value = "叶子节点：1-是，0-否", position = 11)
    private Integer leaf;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 12)
    private String remarks;
    /**
     * 状态，1-正常，0-禁用
     */
    @ApiModelProperty(value = "状态，1-正常，0-禁用", position = 14)
    private Integer status;
}
