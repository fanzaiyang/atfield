package cn.fanzy.breeze.admin.module.system.account.args;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BreezeAdminAccountEnableBatchArgs {
    @NotEmpty(message = "账户ID集合不能为空！")
    private List<String> idList;
}
