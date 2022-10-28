package cn.fanzy.breeze.admin.module.system.corp.service.impl;

import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.admin.module.system.corp.args.BreezeAdminCorpSaveArgs;
import cn.fanzy.breeze.admin.module.system.corp.service.BreezeAdminOrgService;
import cn.fanzy.breeze.core.cache.service.BreezeCacheService;
import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.core.utils.TreeUtils;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.sqltoy.utils.SqlParamUtil;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.MapKit;

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
    public JsonContent<List<Tree<String>>> queryOrgTree(String nodeType) {
        BreezeCacheService cacheService = SpringUtils.getBean(BreezeCacheService.class);
        String key = "breeze_admin_org_tree_" + nodeType;
        Object tree = cacheService.get(key);
        try {
            if (ObjectUtil.isNotEmpty(tree) && tree instanceof List) {
                return JsonContent.success((List<Tree<String>>) tree);
            }
        } catch (Exception ignored) {
        }
        List<String> nodeTypeList = new ArrayList<>();
        if (StrUtil.isNotBlank(nodeType)) {
            nodeTypeList = StrUtil.split(nodeType, StrPool.C_COMMA);
        }
        List<SysOrg> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysOrg.class)
                .in(StrUtil.isNotBlank(nodeType), SysOrg::getParentId, nodeTypeList)
                .eq(SysOrg::getStatus, 1)
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysOrg::getCode));
        List<Tree<String>> treeList = TreeUtils.buildTree(list);
        if (CollUtil.isNotEmpty(treeList)) {
            cacheService.save(key, treeList, 8 * 60 * 60);
        }
        return JsonContent.success(treeList);
    }

    @Override
    public JsonContent<List<Tree<String>>> queryCorpTree() {
        List<SysOrg> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysOrg.class)
                .eq(SysOrg::getStatus, 1)
                .eq(SysOrg::getOrgType, "corp")
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysOrg::getCode));
        List<Tree<String>> treeList = TreeUtils.buildTree(list, BreezeConstants.TREE_ROOT_ID);
        return JsonContent.success(treeList);
    }

    @Override
    public JsonContent<List<Tree<String>>> queryDeptTree(String code) {
        List<SysOrg> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysOrg.class)
                .eq(SysOrg::getStatus, 1)
                .likeRight(SysOrg::getCode, code)
                .eq(SysOrg::getOrgType, "dept")
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysOrg::getCode));
        List<Tree<String>> treeList = TreeUtils.buildTree(list);
        return JsonContent.success(treeList);
    }

    @Override
    public JsonContent<List<Tree<String>>> queryJobTree(String code) {
        List<SysOrg> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysOrg.class)
                .eq(SysOrg::getStatus, 1)
                .likeRight(SysOrg::getCode, code)
                .eq(SysOrg::getOrgType, "job")
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysOrg::getCode));
        return JsonContent.success(TreeUtils.buildTree(list));
    }

    @Override
    public JsonContent<Object> save(BreezeAdminCorpSaveArgs args) {
        long count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysOrg.class)
                .eq(SysOrg::getCode, args.getCode())
                .ne(StrUtil.isNotBlank(args.getId()), SysOrg::getId, args.getId()));
        Assert.isTrue(count == 0, "菜单编码「{}」已存在！", args.getCode());
        SysOrg org = BeanUtil.copyProperties(args, SysOrg.class);
        sqlToyHelperDao.saveOrUpdate(org);
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> delete(String id) {
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysOrg.class)
                .set(IBaseEntity::getDelFlag, 1)
                .like(SysOrg::getNodeRoute, id));
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> deleteBatch(List<String> id) {
        for (String s : id) {
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysOrg.class)
                    .set(IBaseEntity::getDelFlag, 1)
                    .like(SysOrg::getNodeRoute, s));
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> enable(String id) {
        String sql = "update sys_org set status=if(status=1,0,1) where id=:id";
        sqlToyHelperDao.executeSql(sql, MapKit.map("id", id));
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> enableBatch(List<String> id) {
        String sql = "update sys_org set status=if(status=1,0,1) where id in (:id)";
        sqlToyHelperDao.executeSql(sql, MapKit.map("id", SqlParamUtil.buildInArgs(id)));
        return JsonContent.success();
    }
}
