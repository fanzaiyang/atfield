package cn.fanzy.breeze.admin.module.system.menu.controller;

import cn.fanzy.breeze.admin.module.entity.SysMenu;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuEnableArgs;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuQueryArgs;
import cn.fanzy.breeze.admin.module.system.menu.args.BreezeAdminMenuSaveArgs;
import cn.fanzy.breeze.admin.module.system.menu.service.BreezeAdminMenuService;
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

@Tag(name = "「微风组件」菜单管理")
@ApiSupport(author = "微风组件", order = 992021)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.menu?:sys/menu}}")
public class BreezeAdminMenuController {
    private final BreezeAdminMenuService breezeAdminMenuService;

    @Operation(summary = "保存修改")
    @ApiOperationSupport(order = 1)
    @PostMapping("/save")
    public JsonContent<Object> save(@Valid @RequestBody BreezeAdminMenuSaveArgs args) {
        return breezeAdminMenuService.save(args);
    }

    @Operation(summary = "删除单个")
    @Parameter(name = "id", description = "菜单ID")
    @ApiOperationSupport(order = 5)
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id) {
        return breezeAdminMenuService.delete(id);
    }

    @Operation(summary = "删除批量")
    @ApiOperationSupport(order = 10)
    @PostMapping("/delete/batch")
    public JsonContent<Object> delete(@Valid @RequestBody BreezeAdminMenuEnableArgs args) {
        return breezeAdminMenuService.deleteBatch(args.getIdList());
    }

    @Operation(summary = "启禁用单个")
    @Parameter(name = "id", description = "菜单ID")
    @ApiOperationSupport(order = 15)
    @PostMapping("/enable")
    public JsonContent<Object> enable(String id) {
        return breezeAdminMenuService.enable(id);
    }

    @Operation(summary = "启禁用批量")
    @ApiOperationSupport(order = 20)
    @PostMapping("/enable/batch")
    public JsonContent<Object> enable(@Valid @RequestBody BreezeAdminMenuEnableArgs args) {
        return breezeAdminMenuService.enableBatch(args);
    }

    @Operation(summary = "分页查询")
    @ApiOperationSupport(order = 25)
    @PostMapping("/query/page")
    public JsonContent<Page<SysMenu>> query(@Valid @RequestBody BreezeAdminMenuQueryArgs args) {
        return breezeAdminMenuService.queryPage(args);
    }

    @Operation(summary = "查询所有")
    @ApiOperationSupport(order = 30)
    @PostMapping("/query/all")
    public JsonContent<List<SysMenu>> queryAll(@Valid @RequestBody BreezeAdminMenuQueryArgs args) {
        return breezeAdminMenuService.queryAll(args);
    }

    @Operation(summary = "查询树")
    @ApiOperationSupport(order = 35)
    @PostMapping("/query/tree")
    public JsonContent<List<Tree<String>>> queryTree(@Valid @RequestBody BreezeAdminMenuQueryArgs args) {
        return breezeAdminMenuService.queryTree(args);
    }
}
