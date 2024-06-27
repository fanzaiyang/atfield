/**
 * @Generated by sagacity-quickvo 5.0
 */
package cn.fanzy.atfield.infra.entity;

import cn.fanzy.atfield.sqltoy.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.io.Serial;
import java.io.Serializable;

/**
 * @project spider-starter
 * @author fanzaiyang
 * @version 1.0.0
 */
@Schema(description = "系统配置表")
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Entity(tableName = "sys_config", comment = "系统配置表", pk_constraint = "PRIMARY")
public class SysConfigDO extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2017341440962618628L;
    /*---begin-auto-generate-don't-update-this-area--*/

    @Schema(description = "主键", nullable = false)
    @Id(strategy = "generator", generator = "org.sagacity.sqltoy.plugins.id.impl.SnowflakeIdGenerator")
    @Column(name = "id", comment = "主键", length = 36L, type = java.sql.Types.VARCHAR, nativeType = "VARCHAR", nullable = false)
    private String id;

    @Schema(description = "分组名称", nullable = true)
    @Column(name = "group_name", comment = "分组名称", length = 90L, type = java.sql.Types.VARCHAR, nativeType = "VARCHAR", nullable = true)
    private String groupName;

    @Schema(description = "参数名称", nullable = true)
    @Column(name = "key_label", comment = "参数名称", length = 90L, type = java.sql.Types.VARCHAR, nativeType = "VARCHAR", nullable = true)
    private String keyLabel;

    @Schema(description = "参数键名", nullable = true)
    @Column(name = "key_name", comment = "参数键名", length = 90L, type = java.sql.Types.VARCHAR, nativeType = "VARCHAR", nullable = true)
    private String keyName;

    @Schema(description = "参数键值", nullable = true)
    @Column(name = "key_value", comment = "参数键值", length = 900L, type = java.sql.Types.VARCHAR, nativeType = "VARCHAR", nullable = true)
    private String keyValue;

    @Schema(description = "备注说明", nullable = true)
    @Column(name = "remarks", comment = "备注说明", length = 900L, type = java.sql.Types.VARCHAR, nativeType = "VARCHAR", nullable = true)
    private String remarks;

    @Schema(description = "状态;1-有效，0-无效", nullable = true)
    @Column(name = "status", comment = "状态;1-有效，0-无效", length = 5L, defaultValue = "1", type = java.sql.Types.SMALLINT, nativeType = "SMALLINT", nullable = true)
    private Integer status;

    /**
     * default constructor
     */
    public SysConfigDO() {
    }

    /**
     * pk constructor
     */
    public SysConfigDO(String id) {
        this.id = id;
    }
    /*---end-auto-generate-don't-update-this-area--*/
}
