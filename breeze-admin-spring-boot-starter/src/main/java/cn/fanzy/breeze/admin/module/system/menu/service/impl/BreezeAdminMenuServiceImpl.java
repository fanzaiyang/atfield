package cn.fanzy.breeze.admin.module.system.menu.service.impl;


import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuEnableArgs;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuQueryArgs;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuSaveArgs;
import cn.fanzy.breeze.admin.module.system.menu.service.BreezeAdminMenuService;
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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.model.TreeTableModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class BreezeAdminMenuServiceImpl implements BreezeAdminMenuService {
    private final SqlToyHelperDao sqlToyHelperDao;

    @Transactional
    @Override
    public JsonContent<Object> save(BreezeAdminMenuSaveArgs args) {
        long count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysMenu.class)
                .eq(SysMenu::getCode, args.getCode())
                .ne(StrUtil.isNotBlank(args.getId()), SysMenu::getId, args.getId()));
        Assert.isTrue(count == 0, "菜单编码「{}」已存在！", args.getCode());
        SysMenu menu = BeanUtil.copyProperties(args, SysMenu.class);
        sqlToyHelperDao.saveOrUpdate(menu);
        TreeTableModel tableModel = new TreeTableModel(menu);
        tableModel.rootId(BreezeConstants.TREE_ROOT_ID);
        tableModel.pidField(BreezeConstants.TREE_PARENT_ID);
        sqlToyHelperDao.wrapTreeTableRoute(tableModel);
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> delete(String id) {
        Assert.notBlank(id, "请求参数不能为空！");
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysMenu.class)
                .set(IBaseEntity::getDelFlag, 1)
                .in(SysMenu::getId, id).or()
                .like(SysMenu::getNodeRoute, id));
        return JsonContent.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonContent<Object> deleteBatch(List<String> idList) {
        Assert.notEmpty(idList, "请求参数不能为空！");
        for (String id : idList) {
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysMenu.class)
                    .set(IBaseEntity::getDelFlag, 1)
                    .in(SysMenu::getId, id).or()
                    .like(SysMenu::getNodeRoute, id));
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> enable(String id) {
        Assert.notBlank(id, "请求参数不能为空！");
        SysMenu menu = sqlToyHelperDao.load(SysMenu.builder().id(id).build());
        Assert.notNull(menu, "未找到ID为「{}」的菜单！", id);
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysMenu.class)
                .set(SysMenu::getStatus, menu.getStatus() != null && menu.getStatus() == 1 ? 0 : 1)
                .eq(menu.getStatus() != null, SysMenu::getStatus, menu.getStatus())
                .and(i -> i.eq(SysMenu::getId, id).or()
                        .like(SysMenu::getNodeRoute, id)));
        return JsonContent.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonContent<Object> enableBatch(BreezeAdminMenuEnableArgs args) {
        List<SysMenu> menuList = sqlToyHelperDao.loadByIds(SysMenu.class, args.getIdList().toArray());
        Assert.notEmpty(menuList, "未找到对应记录！");
        for (SysMenu menu : menuList) {
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysMenu.class)
                    .set(SysMenu::getStatus, menu.getStatus() != null && menu.getStatus() == 1 ? 0 : 1)
                    .eq(menu.getStatus() != null, SysMenu::getStatus, menu.getStatus())
                    .and(i -> i.eq(SysMenu::getId, menu.getId())
                            .or()
                            .like(SysMenu::getNodeRoute, menu.getId())));
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<Page<SysMenu>> queryPage(BreezeAdminMenuQueryArgs args) {
        Page<SysMenu> page = sqlToyHelperDao.findPage(Wrappers.lambdaWrapper(SysMenu.class)
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByAsc(SysMenu::getOrderNumber), new Page<>(args.getPageSize(), args.getPageNum()));
        return JsonContent.success(page);
    }

    @Override
    public JsonContent<List<SysMenu>> queryAll(BreezeAdminMenuQueryArgs args) {
        args.setPageSize(Integer.MAX_VALUE);
        return JsonContent.success(queryPage(args).getData().getRows());
    }

    @Override
    public JsonContent<List<Tree<String>>> queryTree(BreezeAdminMenuQueryArgs args) {
        List<SysMenu> menuList = queryAll(args).getData();
        List<Tree<String>> treeList = TreeUtils.buildTree(menuList);
        if (treeList == null) {
            treeList = new ArrayList<>();
        }
        return JsonContent.success(treeList);
    }
}
