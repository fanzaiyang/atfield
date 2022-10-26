package cn.fanzy.breeze.admin.module.system.dict.service.impl;

import cn.fanzy.breeze.admin.module.entity.SysDict;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictSaveArgs;
import cn.fanzy.breeze.admin.module.system.dict.service.BreezeAdminDictService;
import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.TreeTableModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public JsonContent<Object> delete(String id) {
        Assert.notBlank(id, "请求参数不能为空！");
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysDict.class)
                .set(IBaseEntity::getDelFlag, 1)
                .like(SysDict::getNodeRoute, id));
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> enable(String id) {
        SysDict dict = sqlToyHelperDao.load(SysDict.builder().id(id).build());
        Assert.notNull(dict, "未找到ID为「{}」的数据！", id);
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysDict.class)
                .set(SysDict::getStatus, dict.getStatus() == 1 ? 0 : 1)
                .like(SysDict::getNodeRoute, id));
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
}
