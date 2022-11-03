package cn.fanzy.breeze.admin.module.system.roles.service.impl;


import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.entity.SysRole;
import cn.fanzy.breeze.admin.module.entity.SysRoleMenu;
import cn.fanzy.breeze.admin.module.system.roles.args.BreezeAdminRoleMenuBindArgs;
import cn.fanzy.breeze.admin.module.system.roles.args.BreezeAdminRoleQueryPageArgs;
import cn.fanzy.breeze.admin.module.system.roles.args.BreezeAdminRoleSaveArgs;
import cn.fanzy.breeze.admin.module.system.roles.service.BreezeAdminRoleService;
import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.core.utils.TreeUtils;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.MapKit;
import org.sagacity.sqltoy.model.Page;
import org.sagacity.sqltoy.model.TreeTableModel;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class BreezeAdminRoleServiceImpl implements BreezeAdminRoleService {
    private final SqlToyHelperDao sqlToyHelperDao;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonContent<Object> save(BreezeAdminRoleSaveArgs args) {
        long count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysRole.class)
                .eq(SysRole::getCode, args.getCode())
                .ne(StrUtil.isNotBlank(args.getId()), SysRole::getId, args.getId())
                .eq(IBaseEntity::getDelFlag, 0));
        Assert.isTrue(count == 0, "角色编码「{}」已存在！", args.getCode());
        SysRole role = BeanUtil.copyProperties(args, SysRole.class);
        sqlToyHelperDao.saveOrUpdate(role);
        TreeTableModel tableModel = new TreeTableModel(role);
        tableModel.pidField("parentId");
        tableModel.rootId(BreezeConstants.TREE_ROOT_ID);
        sqlToyHelperDao.wrapTreeTableRoute(tableModel);
        return menuBind(BreezeAdminRoleMenuBindArgs.builder()
                .id(role.getId())
                .menuIdList(args.getMenuIdList())
                .build());
    }

    @Override
    public JsonContent<Object> delete(String id) {
        Assert.notBlank(id, "角色ID不能为空！");
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysRole.class)
                .set(IBaseEntity::getDelFlag, 1)
                .like(SysRole::getNodeRoute, id)
                .or()
                .eq(SysRole::getId, id));
        return JsonContent.success();
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public JsonContent<Object> deleteBatch(List<String> idList) {
        Assert.notEmpty(idList, "角色ID不能为空！");
        for (String id : idList) {
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysRole.class)
                    .set(IBaseEntity::getDelFlag, 1)
                    .like(SysRole::getNodeRoute, id)
                    .or()
                    .eq(SysRole::getId, id));
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<Page<SysRole>> queryPage(BreezeAdminRoleQueryPageArgs args) {
        Page<SysRole> page = sqlToyHelperDao.findPage(Wrappers.lambdaWrapper(SysRole.class)
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByDesc(IBaseEntity::getCreateTime), new Page<>(args.getPageSize(), args.getPageNum()));
        return JsonContent.success(page);
    }

    @Override
    public JsonContent<List<SysRole>> queryAll(BreezeAdminRoleQueryPageArgs args) {
        args.setPageSize(Integer.MAX_VALUE);
        JsonContent<Page<SysRole>> page = queryPage(args);
        return JsonContent.success(page.getData().getRows());
    }

    @Override
    public JsonContent<Object> enable(String id) {
        List<SysRole> roleList = sqlToyHelperDao.loadByIds(SysRole.class, id);
        Assert.notEmpty(roleList, "未找到ID为「{}」的角色！", id);
        SysRole role = roleList.get(0);
        int status = role.getStatus() == 1 ? 0 : 1;
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysRole.class)
                .set(SysRole::getStatus, status)
                .eq(role.getStatus() != null, SysRole::getStatus, role.getStatus())
                .and(i -> i.eq(SysRole::getId, id).or()
                        .like(SysRole::getNodeRoute, id)));
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> enableBatch(List<String> idList) {
        List<SysRole> roleList = sqlToyHelperDao.loadByIds(SysRole.class, idList.toArray());
        Assert.notEmpty(roleList, "未找到ID为「{}」的角色！", JSONUtil.toJsonStr(idList));
        for (SysRole role : roleList) {
            int status = role.getStatus() != null && role.getStatus() == 1 ? 0 : 1;
            sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysRole.class)
                    .set(SysRole::getStatus, status)
                    .eq(role.getStatus() != null, SysRole::getStatus, role.getStatus())
                    .and(i -> i.eq(SysRole::getId, role.getId()).or()
                            .like(SysRole::getNodeRoute, role.getId())));
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<List<Tree<String>>> queryTree(BreezeAdminRoleQueryPageArgs args) {
        JsonContent<List<SysRole>> content = queryAll(args);
        Assert.isTrue(content.isSuccess(), content.getMessage());
        return JsonContent.success(TreeUtils.buildTree(content.getData()));
    }

    @Override
    public JsonContent<Object> menuBind(BreezeAdminRoleMenuBindArgs args) {
        List<SysRole> roleList = sqlToyHelperDao.loadByIds(SysRole.class, args.getId());
        Assert.notEmpty(roleList, "未找到ID为「{}」的角色！", args.getId());
        sqlToyHelperDao.delete(Wrappers.lambdaWrapper(SysRoleMenu.class)
                .eq(SysRoleMenu::getRoleId, args.getId()));
        if (CollUtil.isNotEmpty(args.getMenuIdList())) {
            List<SysRoleMenu> menuList = new ArrayList<>();
            for (String menuId : args.getMenuIdList()) {
                menuList.add(SysRoleMenu.builder()
                        .roleId(args.getId())
                        .menuId(menuId)
                        .build());
            }
            sqlToyHelperDao.saveAll(menuList);
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<List<SysMenu>> getBindMenu(String id) {
        // 查询已绑定的菜单ID
        String sql = "select t.id from sys_menu t inner join sys_role_menu rm on rm.menu_id=t.id and rm.role_id=:roleId where t.del_flag=0 and t.is_leaf=1";
        List<SysMenu> menuList = sqlToyHelperDao.findBySql(sql, MapKit.map("roleId", id), SysMenu.class);
        if (CollUtil.isEmpty(menuList)) {
            menuList = new ArrayList<>();
        }
        return JsonContent.success(menuList);
    }
}
