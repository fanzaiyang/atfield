package cn.fanzy.breeze.admin.module.system.account.service;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.entity.SysAccountRole;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountQueryArgs;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountRoleSaveArgs;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountSaveArgs;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class BreezeAdminAccountServiceImpl implements BreezeAdminAccountService {
    private final SqlToyHelperDao sqlToyHelperDao;

    @Transactional
    @Override
    public JsonContent<Object> save(BreezeAdminAccountSaveArgs args) {
        //校验
        // 校验用户名
        long count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysAccount.class)
                .eq(IBaseEntity::getDelFlag, 0)
                .eq(SysAccount::getUsername, args.getUsername())
                .ne(StrUtil.isNotBlank(args.getId()), SysAccount::getId, args.getId()));
        Assert.isTrue(count == 0, "用户名「{}」已存在！", args.getUsername());
        if (StrUtil.isNotBlank(args.getTelnum())) {
            count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysAccount.class)
                    .eq(IBaseEntity::getDelFlag, 0)
                    .eq(SysAccount::getTelnum, args.getTelnum())
                    .ne(StrUtil.isNotBlank(args.getId()), SysAccount::getId, args.getId()));
            Assert.isTrue(count == 0, "手机号「{}」已存在！", args.getUsername());
        }
        if (StrUtil.isNotBlank(args.getWorkTelnum())) {
            count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysAccount.class)
                    .eq(IBaseEntity::getDelFlag, 0)
                    .eq(SysAccount::getWorkTelnum, args.getTelnum())
                    .ne(StrUtil.isNotBlank(args.getId()), SysAccount::getId, args.getId()));
            Assert.isTrue(count == 0, "工作手机号「{}」已存在！", args.getUsername());
        }
        SysAccount account = BeanUtil.copyProperties(args, SysAccount.class);
        sqlToyHelperDao.saveOrUpdate(account);
        //保存角色
        if (CollUtil.isNotEmpty(args.getRoleIdList())) {
            return saveAccountRole(BreezeAdminAccountRoleSaveArgs.builder()
                    .id(account.getId())
                    .roleIdList(args.getRoleIdList())
                    .build());
        }
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> delete(String id) {
        Assert.notBlank(id, "请求参数不能为空！");
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysAccount.class)
                .set(IBaseEntity::getDelFlag, 1)
                .eq(SysAccount::getId, id));
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> deleteBatch(List<String> idList) {
        Assert.notEmpty(idList, "请求参数不能为空！");
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysAccount.class)
                .set(IBaseEntity::getDelFlag, 1)
                .in(SysAccount::getId, idList));
        return JsonContent.success();
    }

    @Override
    public JsonContent<Page<SysAccount>> query(BreezeAdminAccountQueryArgs args) {
        Page<SysAccount> page = sqlToyHelperDao.findPage(Wrappers.lambdaWrapper(SysAccount.class)
                .and(StrUtil.isNotBlank(args.getSearchWord()), i -> i.like(SysAccount::getNickName, args.getSearchWord())
                        .or()
                        .like(SysAccount::getTelnum, args.getSearchWord())
                        .or()
                        .like(SysAccount::getWorkTelnum, args.getSearchWord())
                ), new Page<>(args.getPageSize(), args.getPageNum()));
        return JsonContent.success(page);
    }

    @Transactional
    @Override
    public JsonContent<Object> saveAccountRole(BreezeAdminAccountRoleSaveArgs args) {
        sqlToyHelperDao.deleteByIds(SysAccountRole.class, args.getRoleIdList());
        if (CollUtil.isNotEmpty(args.getRoleIdList())) {
            List<SysAccountRole> saveList = new ArrayList<>();
            for (String roleId : args.getRoleIdList()) {
                saveList.add(SysAccountRole.builder()
                        .roleId(roleId).accountId(args.getId())
                        .build());
            }
            sqlToyHelperDao.saveAll(saveList);
        }
        return JsonContent.success();
    }
}
