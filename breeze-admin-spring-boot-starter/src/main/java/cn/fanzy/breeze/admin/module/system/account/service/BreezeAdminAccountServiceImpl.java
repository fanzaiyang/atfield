package cn.fanzy.breeze.admin.module.system.account.service;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.entity.SysAccountRole;
import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountBatchArgs;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountQueryArgs;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountRoleSaveArgs;
import cn.fanzy.breeze.admin.module.system.account.args.BreezeAdminAccountSaveArgs;
import cn.fanzy.breeze.admin.properties.BreezeAdminProperties;
import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.password.PasswordEncoder;
import cn.fanzy.breeze.web.password.bcrypt.BCryptPasswordEncoder;
import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 微风impl管理员帐户服务
 *
 * @author fanzaiyang
 * @date 2022-11-03
 */
@Slf4j
@AllArgsConstructor
public class BreezeAdminAccountServiceImpl implements BreezeAdminAccountService {
    private final SqlToyHelperDao sqlToyHelperDao;
    private final BreezeAdminProperties properties;

    @Transactional(rollbackFor = {Exception.class})
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
        if (StrUtil.isNotBlank(args.getCode())) {
            count = sqlToyHelperDao.count(Wrappers.lambdaWrapper(SysAccount.class)
                    .eq(IBaseEntity::getDelFlag, 0)
                    .eq(SysAccount::getCode, args.getCode())
                    .ne(StrUtil.isNotBlank(args.getId()), SysAccount::getId, args.getId()));
            Assert.isTrue(count == 0, "工作手机号「{}」已存在！", args.getUsername());
        }
        SysAccount account = BeanUtil.copyProperties(args, SysAccount.class);
        // 查询单位/部门/岗位
        if (StrUtil.isNotBlank(account.getJobCode())) {
            SysOrg job = sqlToyHelperDao.findOne(Wrappers.lambdaWrapper(SysOrg.class).eq(SysOrg::getCode, account.getJobCode())
                    .eq(SysOrg::getStatus, 1)
                    .eq(IBaseEntity::getDelFlag, 0));
            Assert.notNull(job, "未找到编码为「{}」的有效岗位！", account.getJobCode());
            account.setJobName(job.getName());
            List<String> parentIdList = StrUtil.split(job.getNodeRoute(), StrPool.COMMA);
            if (CollUtil.isEmpty(parentIdList)) {
                throw new RuntimeException("组织结构数据异常！");
            }
            parentIdList.remove(job.getId());
            List<SysOrg> orgList = sqlToyHelperDao.loadByIds(SysOrg.class, parentIdList.toArray());
            for (int i = orgList.size() - 1; i >= 0; i--) {
                SysOrg item = orgList.get(i);
                if (StrUtil.equalsIgnoreCase(item.getOrgType(), "dept")) {
                    // 如果是部门
                    account.setDeptCode(item.getCode());
                    account.setDeptName(item.getName());
                    break;
                }
            }
            for (int i = orgList.size() - 1; i >= 0; i--) {
                SysOrg item = orgList.get(i);
                if (StrUtil.equalsIgnoreCase(item.getOrgType(), "corp")) {
                    // 如果是部门
                    account.setCorpCode(item.getCode());
                    account.setCorpName(item.getName());
                    break;
                }
            }
        }
        if (StrUtil.isNotBlank(args.getId())) {
            SysAccount sysAccount = sqlToyHelperDao.load(SysAccount.builder().id(args.getId()).build());
            Assert.notNull(sysAccount, "未找到ID为「{}」的账户！", args.getId());
            sqlToyHelperDao.update(account);
        } else {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            account.setPassword(encoder.encode(properties.getDefaultPassword()));
            sqlToyHelperDao.save(account);
        }
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
                        )
                        .likeRight(StrUtil.equalsIgnoreCase(args.getOrgType(), "corp"), SysAccount::getCorpCode, args.getOrgCode())
                        .likeRight(StrUtil.equalsIgnoreCase(args.getOrgType(), "dept"), SysAccount::getDeptCode, args.getOrgCode())
                        .likeRight(StrUtil.equalsIgnoreCase(args.getOrgType(), "job"), SysAccount::getJobCode, args.getOrgCode())
                        .eq(args.getStatus() != null, SysAccount::getStatus, args.getStatus())
                        .eq(IBaseEntity::getDelFlag, 0)
                        .orderByAsc(CollUtil.newArrayList(SysAccount::getCorpCode,SysAccount::getDeptCode,SysAccount::getJobCode,SysAccount::getCode))
                , new Page<>(args.getPageSize(), args.getPageNum()));
        return JsonContent.success(page);
    }

    @Transactional
    @Override
    public JsonContent<Object> saveAccountRole(BreezeAdminAccountRoleSaveArgs args) {
        sqlToyHelperDao.delete(Wrappers.lambdaWrapper(SysAccountRole.class)
                .eq(SysAccountRole::getAccountId, args.getId()));
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

    @Override
    public JsonContent<List<String>> queryAccountRoleList(String id) {
        List<SysAccountRole> roleList = sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysAccountRole.class)
                .select(SysAccountRole::getRoleId)
                .eq(SysAccountRole::getAccountId, id));
        return JsonContent.success(roleList.stream().map(SysAccountRole::getRoleId).collect(Collectors.toList()));
    }

    @Override
    public JsonContent<Object> enableBatch(List<String> idList) {
        List<SysAccount> accountList = sqlToyHelperDao.loadByIds(SysAccount.class, idList.toArray());
        Assert.notEmpty(accountList, "未找到ID为「{}」的账户！", JSONUtil.toJsonStr(idList));
        accountList.forEach(item -> {
            item.setStatus(item.getStatus() != null && item.getStatus() == 1 ? 0 : 1);
        });
        sqlToyHelperDao.updateAll(accountList);
        return JsonContent.success();
    }

    @Override
    public JsonContent<Object> doRestAccountPwd(BreezeAdminAccountBatchArgs args) {
        BreezeAdminProperties properties = SpringUtils.getBean(BreezeAdminProperties.class);
        String password = StrUtil.blankToDefault(properties.getDefaultPassword(), BreezeConstants.DEFAULT_PASSWORD);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysAccount.class)
                .set(SysAccount::getPassword, passwordEncoder.encode(password))
                .in(SysAccount::getId, args.getIdList()));
        return JsonContent.success("密码已重置为「" + password + "」");
    }
}
