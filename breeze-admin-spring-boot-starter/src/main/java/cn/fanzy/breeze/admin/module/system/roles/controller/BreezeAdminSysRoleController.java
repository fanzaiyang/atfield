package cn.fanzy.breeze.admin.module.system.roles.controller;

import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.entity.SysRole;
import cn.fanzy.breeze.admin.module.system.roles.args.*;
import cn.fanzy.breeze.admin.module.system.roles.service.BreezeAdminRoleService;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
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
 * 微风控制器管理系统作用
 *
 * @author fanzaiyang
 * @version 2022-11-02
 */
@Tag(name = "「微风组件」角色管理")
@ApiSupport(author = "微风组件", order = 992011)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.role?:/sys/role}}")
public class BreezeAdminSysRoleController {
    private final BreezeAdminRoleService breezeAdminRoleService;

    @Operation(summary = "保存修改")
    @ApiOperationSupport(order = 1)
    @PostMapping("/save")
    public JsonContent<Object> save(@Valid @RequestBody BreezeAdminRoleSaveArgs args) {
        return breezeAdminRoleService.save(args);
    }

    @Operation(summary = "删除单个",description = "级联下级也会删除！")
    @ApiOperationSupport(order = 10)
    @Parameter(name = "id", description = "角色ID")
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id) {
        return breezeAdminRoleService.delete(id);
    }

    @Operation(summary = "删除批量",description = "级联下级也会删除！")
    @ApiOperationSupport(order = 15)
    @PostMapping("/delete/batch")
    public JsonContent<Object> delete(@RequestBody BreezeAdminRoleDeleteArgs args) {
        return breezeAdminRoleService.deleteBatch(args.getIdList());
    }

    @Operation(summary = "启禁用单个")
    @ApiOperationSupport(order = 20)
    @Parameter(name = "id", description = "角色ID")
    @PostMapping("/enable")
    public JsonContent<Object> enable(String id) {
        return breezeAdminRoleService.enable(id);
    }

    @Operation(summary = "启禁用批量")
    @ApiOperationSupport(order = 25)
    @PostMapping("/enable/batch")
    public JsonContent<Object> enableBatch(@Valid @RequestBody BreezeAdminRoleEnableBatchArgs args) {
        return breezeAdminRoleService.enableBatch(args.getIdList());
    }

    @Operation(summary = "分页查询")
    @ApiOperationSupport(order = 30)
    @PostMapping("/query")
    public JsonContent<Page<SysRole>> queryPage(@Valid @RequestBody BreezeAdminRoleQueryPageArgs args) {
        return breezeAdminRoleService.queryPage(args);
    }

    @Operation(summary = "查询全部")
    @ApiOperationSupport(order = 35)
    @PostMapping("/query/all")
    public JsonContent<List<SysRole>> queryAll(@Valid @RequestBody BreezeAdminRoleQueryPageArgs args) {
        return breezeAdminRoleService.queryAll(args);
    }

    @Operation(summary = "查询角色树")
    @ApiOperationSupport(order = 35)
    @PostMapping("/query/tree")
    public JsonContent<List<Tree<String>>> queryTree(@Valid @RequestBody BreezeAdminRoleQueryPageArgs args) {
        return breezeAdminRoleService.queryTree(args);
    }

    @Operation(summary = "绑定菜单")
    @ApiOperationSupport(order = 50)
    @PostMapping("/menu/bind")
    public JsonContent<Object> menuBind(@Valid @RequestBody BreezeAdminRoleMenuBindArgs args) {
        return breezeAdminRoleService.menuBind(args);
    }

    @Operation(summary = "已绑定菜单")
    @ApiOperationSupport(order = 60)
    @Parameter(name = "id", description = "角色ID")
    @GetMapping("/menu/list")
    public JsonContent<List<SysMenu>> getBindMenu(String id) {
        return breezeAdminRoleService.getBindMenu(id);
    }
}
