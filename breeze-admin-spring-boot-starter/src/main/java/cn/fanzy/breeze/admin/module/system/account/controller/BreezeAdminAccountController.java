package cn.fanzy.breeze.admin.module.system.account.controller;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.system.account.args.*;
import cn.fanzy.breeze.admin.module.system.account.service.BreezeAdminAccountService;
import cn.fanzy.breeze.admin.module.system.account.vo.SysAccountVo;
import cn.fanzy.breeze.web.model.JsonContent;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.sagacity.sqltoy.model.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 微风管理员帐户控制器
 *
 * @author fanzaiyang
 * @since 2022-11-03
 */
@Tag(name = "「微风组件」账户管理")
@ApiSupport(author = "微风组件", order = 992001)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api:/}${breeze.admin.prefix.account:sys/account}")
public class BreezeAdminAccountController {

    private final BreezeAdminAccountService breezeAdminAccountService;

    @Operation(summary = "新增修改")
    @ApiOperationSupport(order = 1)
    @PostMapping("/save")
    public JsonContent<Object> save(@Valid @RequestBody BreezeAdminAccountSaveArgs args) {
        return breezeAdminAccountService.save(args);
    }

    @Operation(summary = "绑定角色")
    @ApiOperationSupport(order = 1)
    @PostMapping("/role/bind")
    public JsonContent<Object> saveAccountRole(@Valid @RequestBody BreezeAdminAccountRoleSaveArgs args) {
        return breezeAdminAccountService.saveAccountRole(args);
    }

    @Operation(summary = "删除单个")
    @ApiOperationSupport(order = 2)
    @Parameter(name = "id", description = "账户ID")
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id) {
        return breezeAdminAccountService.delete(id);
    }

    @Operation(summary = "删除批量")
    @ApiOperationSupport(order = 3)
    @PostMapping("/delete/batch")
    public JsonContent<Object> deleteBatch(@Valid @RequestBody BreezeAdminAccountBatchArgs args) {
        return breezeAdminAccountService.deleteBatch(args.getIdList());
    }
    @Operation(summary = "启用批量")
    @ApiOperationSupport(order = 3)
    @PostMapping("/enable/batch")
    public JsonContent<Object> enableBatch(@Valid @RequestBody BreezeAdminAccountBatchArgs args) {
        return breezeAdminAccountService.enableBatch(args.getIdList());
    }
    @Operation(summary = "分页查询")
    @ApiOperationSupport(order = 4)
    @PostMapping("/query/page")
    public JsonContent<Page<SysAccount>> query(@Valid @RequestBody BreezeAdminAccountQueryArgs args) {
        return breezeAdminAccountService.query(args);
    }
    @Operation(summary = "查询账户详情")
    @Parameter(name = "id",description = "账户ID")
    @GetMapping("/info")
    public JsonContent<SysAccountVo> getAccountInfo(String id) {
        return breezeAdminAccountService.getAccountInfo(id);
    }
    @Operation(summary = "查询账号绑定的角色")
    @ApiOperationSupport(order = 5)
    @Parameter(name = "id",description = "账户ID")
    @GetMapping("/role/list")
    public JsonContent<List<String>> queryAccountRoleList(String id) {
        return breezeAdminAccountService.queryAccountRoleList(id);
    }
    @Operation(summary = "重置密码")
    @ApiOperationSupport(order = 6)
    @PostMapping("/pwd/reset")
    public JsonContent<Object> doRestAccountPwd(@Valid @RequestBody BreezeAdminAccountBatchArgs args) {
        return breezeAdminAccountService.doRestAccountPwd(args);
    }

    @Operation(summary = "修改密码")
    @ApiOperationSupport(order = 7)
    @PostMapping("/pwd/update")
    public JsonContent<Object> doChangeAccountPwd(@Valid @RequestBody BreezeAdminAccountPwdChangeArgs args) {
        return breezeAdminAccountService.doChangeAccountPwd(args);
    }
}
