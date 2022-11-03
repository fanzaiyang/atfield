package cn.fanzy.breeze.admin.module.system.account.args;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 微风del批arg游戏管理员帐户
 *
 * @author fanzaiyang
 * @date 2022-11-03
 */
@Data
public class BreezeAdminAccountBatchArgs {
    @NotEmpty(message = "请求参数不能为空！")
    private List<String> idList;
}
