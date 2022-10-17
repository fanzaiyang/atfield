package cn.fanzy.breeze.admin.module.system.account.args;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminAccountRoleSaveArgs {
    @NotBlank(message = "账户ID不能为空！")
    @ApiModelProperty(value = "账户ID")
    private String id;
    @NotEmpty(message = "角色集合不能为空！")
    @ApiModelProperty(value = "角色ID集合")
    private List<String> roleIdList;
}
