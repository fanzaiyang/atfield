package cn.fanzy.breeze.admin.module.system.corp.service;

import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.admin.module.system.corp.args.BreezeAdminCorpSaveArgs;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;

import java.util.List;

public interface BreezeAdminOrgService {
    JsonContent<List<SysOrg>> queryCorpAsync(String id);

    JsonContent<List<Tree<String>>> queryOrgTree(String nodeType);
    JsonContent<List<Tree<String>>> queryCorpTree();

    JsonContent<List<Tree<String>>> queryDeptTree(String code);

    JsonContent<List<Tree<String>>> queryJobTree(String code);
    JsonContent<Object> save(BreezeAdminCorpSaveArgs args);

    JsonContent<Object> delete(String id);

    JsonContent<Object> deleteBatch(List<String> id);

    JsonContent<Object> enable(String id);

    JsonContent<Object> enableBatch(List<String> id);


}
