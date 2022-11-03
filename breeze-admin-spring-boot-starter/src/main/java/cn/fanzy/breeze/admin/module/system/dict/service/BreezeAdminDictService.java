package cn.fanzy.breeze.admin.module.system.dict.service;

import cn.fanzy.breeze.admin.module.entity.SysDict;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictBatchArgs;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictQueryArgs;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictSaveArgs;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
import org.sagacity.sqltoy.model.Page;

import java.util.List;

public interface BreezeAdminDictService {
    JsonContent<Object> save(BreezeAdminDictSaveArgs args);

    JsonContent<Object> delete(BreezeAdminDictBatchArgs args);

    JsonContent<Object> enable(BreezeAdminDictBatchArgs args);
    JsonContent<List<SysDict>> queryAsync(String id, boolean showDisable);

    JsonContent<Page<Tree<String>>> queryPageTree(BreezeAdminDictQueryArgs args);
}
