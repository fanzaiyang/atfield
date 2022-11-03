package cn.fanzy.breeze.admin.module.entity;


import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;

/**
 * (SysDict)表实体类
 *
 * @author fanzaiyang
 * @since 2022-06-21 14:19:21
 */
@ApiModel(value = "SysDict实体类")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(tableName = "sys_dict")
public class SysDict extends IBaseEntity {
    private static final long serialVersionUID = 4345459075662070933L;
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id", type = Types.VARCHAR, length = 36)
    @ApiModelProperty(value = "id", position = 1)
    private String id;
    /**
     * 键名
     */
    @Column(name = "key_name", type = Types.VARCHAR, length = 100)
    @ApiModelProperty(value = "键名", position = 2)
    private String keyName;
    /**
     * 键值
     */
    @Column(name = "key_value", type = Types.VARCHAR, length = 900)
    @ApiModelProperty(value = "键值", position = 3)
    private String keyValue;
    /**
     * 备注
     */
    @Column(name = "remarks", type = Types.VARCHAR, length = 900)
    @ApiModelProperty(value = "备注", position = 4)
    private String remarks;
    /**
     * 上级ID
     */
    @Column(name = "parent_id", type = Types.VARCHAR, length = 36)
    @ApiModelProperty(value = "上级ID", position = 5)
    private String parentId;
    /**
     * 等级
     */
    @Column(name = "node_level", type = Types.INTEGER, length = 11)
    @ApiModelProperty(value = "等级", position = 6)
    private Integer nodeLevel;
    /**
     * 所有上级ID
     */
    @Column(name = "node_route", type = Types.LONGVARCHAR)
    @ApiModelProperty(value = "所有上级ID", position = 7)
    private String nodeRoute;
    /**
     * 是否是叶子节点
     */
    @Column(name = "is_leaf", type = Types.SMALLINT, length = 1)
    @ApiModelProperty(value = "是否是叶子节点", position = 8)
    private Integer isLeaf;

    @Column(name = "status", type = Types.SMALLINT, length = 1,defaultValue = "1")
    @ApiModelProperty(value = "状态，1-有效，0-禁用", position = 9)
    private Integer status;
    @Column(name = "order_number", type = Types.INTEGER,defaultValue = "1")
    @ApiModelProperty(value = "序号", position = 11)
    private Integer orderNumber;
    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}

