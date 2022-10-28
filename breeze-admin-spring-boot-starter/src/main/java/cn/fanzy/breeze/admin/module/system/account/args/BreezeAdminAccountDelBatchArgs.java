package cn.fanzy.breeze.admin.module.system.account.args;

import lombok.Data;

import java.util.List;

@Data
public class BreezeAdminAccountDelBatchArgs {
    private List<String> idList;
}
