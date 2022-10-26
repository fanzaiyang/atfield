package cn.fanzy.breeze.admin.module.system.dict.service;

import cn.fanzy.breeze.admin.module.entity.SysDict;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictSaveArgs;
import cn.fanzy.breeze.web.model.JsonContent;

import java.util.List;

public interface BreezeAdminDictService {
    JsonContent<Object> save(BreezeAdminDictSaveArgs args);

    JsonContent<Object> delete(String id);

    JsonContent<Object> enable(String id);
    JsonContent<List<SysDict>> queryAsync(String id, boolean showDisable);

}
