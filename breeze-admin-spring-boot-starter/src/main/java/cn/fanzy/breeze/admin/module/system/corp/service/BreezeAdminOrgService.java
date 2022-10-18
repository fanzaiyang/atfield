package cn.fanzy.breeze.admin.module.system.corp.service;

import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;

import java.util.List;

public interface BreezeAdminOrgService {
    JsonContent<List<SysOrg>> queryCorpAsync(String id);

    JsonContent<List<Tree<String>>> queryCorpTree(String nodeType);
}
