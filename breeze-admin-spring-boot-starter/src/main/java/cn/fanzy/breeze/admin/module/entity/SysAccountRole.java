package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class SysAccountRole extends IBaseEntity {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 账户ID
     */
    @ApiModelProperty(value = "账户ID", position = 2)
    private String accountId;
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID", position = 3)
    private String roleId;

}

