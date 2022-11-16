package cn.fanzy.breeze.admin.module.system.dict.service.impl;

import cn.fanzy.breeze.admin.module.entity.SysDict;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictBatchArgs;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictQueryArgs;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictSaveArgs;
import cn.fanzy.breeze.admin.module.system.dict.service.BreezeAdminDictService;
import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.core.utils.TreeUtils;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.model.TreeTableModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class BreezeAdminDictServiceImpl implements BreezeAdminDictService {
    private final SqlToyHelperDao sqlToyHelperDao;

    @Transactional
    @Override
    public JsonContent<Object> save(BreezeAdminDictSaveArgs args) {
        SysDict dict = BeanUtil.copyProperties(args, SysDict.class);
        long count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysDict.class)
                .eq(IBaseEntity::getDelFlag, 0)
                .eq(SysDict::getKeyName, args.getKeyName())
                .ne(StrUtil.isNotBlank(args.getId()), SysDict::getId, args.getId()));
        Assert.isTrue(count == 0, "字典名称「{}」已存在！", args.getKeyName());
        sqlToyHelperDao.saveOrUpdate(dict);
        TreeTableModel tableModel = new TreeTableModel(dict);
        tableModel.pidField(BreezeConstants.TREE_PARENT_ID);
        sqlToyHelperDao.wrapTreeTableRoute(tableModel);
        return JsonContent.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonContent<Object> delete(BreezeAdminDictBatchArgs args) {
        Assert.notEmpty(args.getIdList(), "请求参数不能为空！");
        for (String id : args.getIdList()) {
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysDict.class)
                    .set(IBaseEntity::getDelFlag, 1)
                    .like(SysDict::getNodeRoute, id));
        }
        return JsonContent.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonContent<Object> enable(BreezeAdminDictBatchArgs args) {
        List<SysDict> dictList = sqlToyHelperDao.loadByIds(SysDict.class, args.getIdList().toArray());
        Assert.notEmpty(dictList, "未找到ID为「{}」的数据！", JSONUtil.toJsonStr(args.getIdList()));
        for (SysDict dict : dictList) {
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysDict.class)
                    .set(SysDict::getStatus, dict.getStatus() != null && dict.getStatus() == 1 ? 0 : 1)
                    .eq(dict.getStatus() != null, SysDict::getStatus, dict.getStatus())
                    .and(i -> i.eq(SysDict::getId, dict.getId())
                            .or()
                            .like(SysDict::getNodeRoute, dict.getId())));
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<List<SysDict>> queryAsync(String id, boolean showDisable) {
        List<SysDict> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysDict.class)
                .eq(SysDict::getParentId, StrUtil.blankToDefault(id, BreezeConstants.TREE_ROOT_ID))
                .eq(!showDisable, SysDict::getStatus, 1)
                .orderByAsc(SysDict::getOrderNumber));
        return JsonContent.success(list);
    }

    @Override
    public JsonContent<Page<Tree<String>>> queryPageTree(BreezeAdminDictQueryArgs args) {
        Page<Tree<String>> result = new Page<>(args.getPageSize(), args.getPageNum());
        Page<SysDict> dictPage = sqlToyHelperDao.findPage(Wrappers.lambdaWrapper(SysDict.class)
                .eq(IBaseEntity::getDelFlag, 0)
                .eq(!args.isShowDisable(), SysDict::getStatus, 1)
                .likeRight(StrUtil.isNotBlank(args.getKeyName()), SysDict::getKeyName, args.getKeyName())
                .like(StrUtil.isNotBlank(args.getRemarks()), SysDict::getRemarks, args.getRemarks())
                .eq(SysDict::getParentId, BreezeConstants.TREE_ROOT_ID)
                .orderByAsc(SysDict::getOrderNumber), new Page<>(args.getPageSize(), args.getPageNum()));
        result.setRecordCount(dictPage.getRecordCount());
        if (dictPage.getRecordCount() == 0) {
            return JsonContent.success(result);
        }
        List<SysDict> rows = dictPage.getRows();
        List<SysDict> dictList = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysDict.class)
                .in(SysDict::getParentId, rows.stream().map(SysDict::getId).collect(Collectors.toSet()))
                .eq(IBaseEntity::getDelFlag, 0)
                .eq(!args.isShowDisable(), SysDict::getStatus, 1));
        rows.addAll(dictList);
        List<Tree<String>> treeList = TreeUtils.buildTree(rows);
        result.setRows(treeList);
        return JsonContent.success(result);
    }

    @Override
    public JsonContent<List<SysDict>> queryChildren(String keyName) {
        SysDict dict = sqlToyHelperDao.findOne(Wrappers.lambdaWrapper(SysDict.class)
                .eq(SysDict::getKeyName, keyName));
        Assert.notNull(dict,"未找到keyName为「」的字典值！",keyName);
        List<SysDict> list = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysDict.class).eq(SysDict::getParentId, dict.getId()));
        return JsonContent.success(list);
    }
}
