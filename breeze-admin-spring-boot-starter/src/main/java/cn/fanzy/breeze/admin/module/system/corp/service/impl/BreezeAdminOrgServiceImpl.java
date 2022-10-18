package cn.fanzy.breeze.admin.module.system.corp.service.impl;

import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.admin.module.system.corp.service.BreezeAdminOrgService;
import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.core.utils.TreeUtils;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class BreezeAdminOrgServiceImpl implements BreezeAdminOrgService {
    private final SqlToyHelperDao sqlToyHelperDao;

    @Override
    public JsonContent<List<SysOrg>> queryCorpAsync(String id) {
        List<SysOrg> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysOrg.class)
                .eq(SysOrg::getParentId, StrUtil.blankToDefault(id, BreezeConstants.TREE_ROOT_ID))
                .eq(SysOrg::getStatus, 1)
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysOrg::getCode));
        return JsonContent.success(list);
    }

    @Override
    public JsonContent<List<Tree<String>>> queryCorpTree(String nodeType) {
        List<String> nodeTypeList = new ArrayList<>();
        if (StrUtil.isNotBlank(nodeType)) {
            nodeTypeList = StrUtil.split(nodeType, StrPool.C_COMMA);
        }
        List<SysOrg> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysOrg.class)
                .in(StrUtil.isNotBlank(nodeType), SysOrg::getParentId, nodeTypeList)
                .eq(SysOrg::getStatus, 1)
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysOrg::getCode));
        return JsonContent.success(TreeUtils.buildTree(list));
    }
}
