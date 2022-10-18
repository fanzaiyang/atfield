package cn.fanzy.breeze.admin.module.system.menu.service;

import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuEnableArgs;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuQueryArgs;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuSaveArgs;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
import org.sagacity.sqltoy.model.Page;

import java.util.List;

public interface BreezeAdminMenuService {
    JsonContent<Object> save(BreezeAdminMenuSaveArgs args);

    JsonContent<Object> delete(String id);

    JsonContent<Object> deleteBatch(List<String> id);

    JsonContent<Object> enable(String id);

    JsonContent<Object> enableBatch(BreezeAdminMenuEnableArgs args);

    JsonContent<Page<SysMenu>> queryPage(BreezeAdminMenuQueryArgs args);

    JsonContent<List<SysMenu>> queryAll(BreezeAdminMenuQueryArgs args);

    JsonContent<List<Tree<String>>> queryTree(BreezeAdminMenuQueryArgs args);
}
