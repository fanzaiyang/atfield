package cn.fanzy.breeze.admin.module.system.account.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminAccountQueryArgs extends BaseArgs {
    @ApiModelProperty(value = "姓名或手机号", position = 1)
    private String searchWord;
}
