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
 * (SysDict)表实体类
 *
 * @author fanzaiyang
 * @since 2022-06-21 14:19:21
 */
@Schema(description = "SysDict实体类")
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
    @Schema(description = "id")
    private String id;
    /**
     * 键名
     */
    @Column(name = "key_name", type = Types.VARCHAR, length = 100)
    @Schema(description = "键名")
    private String keyName;
    /**
     * 键值
     */
    @Column(name = "key_value", type = Types.VARCHAR, length = 900)
    @Schema(description = "键值")
    private String keyValue;
    /**
     * 备注
     */
    @Column(name = "remarks", type = Types.VARCHAR, length = 900)
    @Schema(description = "备注")
    private String remarks;
    /**
     * 上级ID
     */
    @Column(name = "parent_id", type = Types.VARCHAR, length = 36)
    @Schema(description = "上级ID")
    private String parentId;
    /**
     * 等级
     */
    @Column(name = "node_level", type = Types.INTEGER, length = 11)
    @Schema(description = "等级")
    private Integer nodeLevel;
    /**
     * 所有上级ID
     */
    @Column(name = "node_route", type = Types.LONGVARCHAR)
    @Schema(description = "所有上级ID")
    private String nodeRoute;
    /**
     * 是否是叶子节点
     */
    @Column(name = "is_leaf", type = Types.SMALLINT, length = 1)
    @Schema(description = "是否是叶子节点")
    private Integer isLeaf;

    @Column(name = "status", type = Types.SMALLINT, length = 1,defaultValue = "1")
    @Schema(description = "状态，1-有效，0-禁用")
    private Integer status;
    @Column(name = "order_number", type = Types.INTEGER,defaultValue = "1")
    @Schema(description = "序号")
    private Integer orderNumber;
    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}

