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
 * 账户角色绑定表(SysAccountRole)表实体类
 *
 * @author fasnzaiyang
 * @since 2021-09-27 18:09:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysAccountRole", description = "账户角色绑定表")
@Entity(tableName = "sys_account_role")
public class SysAccountRole extends IBaseEntity {
    private static final long serialVersionUID = 5614908436841616371L;
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id",type = Types.VARCHAR,length = 36,comment = "主键")
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 账户ID
     */
    @Column(name = "account_id",type = Types.VARCHAR,length = 36)
    @ApiModelProperty(value = "账户ID", position = 2)
    private String accountId;
    /**
     * 角色ID
     */
    @Column(name = "role_id",type = Types.VARCHAR,length = 36)
    @ApiModelProperty(value = "角色ID", position = 3)
    private String roleId;

}

