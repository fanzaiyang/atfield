package cn.fanzy.breeze.admin.module.system.roles.service;

import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.entity.SysRole;
import cn.fanzy.breeze.admin.module.system.roles.args.BreezeAdminRoleMenuBindArgs;
import cn.fanzy.breeze.admin.module.system.roles.args.BreezeAdminRoleQueryPageArgs;
import cn.fanzy.breeze.admin.module.system.roles.args.BreezeAdminRoleSaveArgs;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
import org.sagacity.sqltoy.model.Page;

import java.util.List;

public interface BreezeAdminRoleService {
    JsonContent<Object> save(BreezeAdminRoleSaveArgs args);
    JsonContent<Object> delete(String id);
    JsonContent<Object> deleteBatch(List<String> idList);

    JsonContent<Page<SysRole>> queryPage(BreezeAdminRoleQueryPageArgs args);

    JsonContent<List<SysRole>> queryAll(BreezeAdminRoleQueryPageArgs args);


    JsonContent<Object> enable(String id);

    JsonContent<Object> enableBatch(List<String> idList);

    JsonContent<Object> menuBind(BreezeAdminRoleMenuBindArgs args);

    JsonContent<List<Tree<String>>> queryTree(BreezeAdminRoleQueryPageArgs args);

    JsonContent<List<SysMenu>> getBindMenu(String id);
}
