package cn.fanzy.breeze.admin.module.system.account.service;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.system.account.args.*;
import cn.fanzy.breeze.web.model.JsonContent;
import org.sagacity.sqltoy.model.Page;

import java.util.List;

public interface BreezeAdminAccountService {
    JsonContent<Object> save(BreezeAdminAccountSaveArgs args);

    JsonContent<Object> delete(String id);

    JsonContent<Object> deleteBatch(List<String> idList);

    JsonContent<Page<SysAccount>> query(BreezeAdminAccountQueryArgs args);

    JsonContent<Object> saveAccountRole(BreezeAdminAccountRoleSaveArgs args);

    JsonContent<List<String>> queryAccountRoleList(String id);

    JsonContent<Object> enableBatch(List<String> idList);

    JsonContent<Object> doRestAccountPwd(BreezeAdminAccountBatchArgs args);

    JsonContent<Object> doChangeAccountPwd(BreezeAdminAccountPwdChangeArgs args);
}
