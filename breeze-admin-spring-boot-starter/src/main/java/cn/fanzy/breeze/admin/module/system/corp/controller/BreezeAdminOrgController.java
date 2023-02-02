package cn.fanzy.breeze.admin.module.system.corp.controller;

import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.admin.module.system.corp.args.BreezeAdminCorpBatchArgs;
import cn.fanzy.breeze.admin.module.system.corp.args.BreezeAdminCorpSaveArgs;
import cn.fanzy.breeze.admin.module.system.corp.service.BreezeAdminOrgService;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "「微风组件」组织管理")
@ApiSupport(author = "微风组件", order = 992041)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api:/}${breeze.admin.prefix.org:sys/org}")
public class BreezeAdminOrgController {
    private final BreezeAdminOrgService breezeAdminOrgService;

    @Operation(summary = "查询组织异步")
    @ApiOperationSupport(order = 11)
    @Parameter(name = "id", description = "主键ID")
    @GetMapping("/query/async")
    public JsonContent<List<SysOrg>> queryCorpAsync(String id) {
        return breezeAdminOrgService.queryCorpAsync(id);
    }

    @Operation(summary = "查询组织树")
    @ApiOperationSupport(order = 12)
    @Parameter(name = "nodeType", description = "节点类型:corp,dept,job多个用逗号隔开。")
    @GetMapping("/query/tree")
    public JsonContent<List<Tree<String>>> queryOrgTree(String nodeType) {
        return breezeAdminOrgService.queryOrgTree(nodeType);
    }
    @Operation(summary = "查询所有公司")
    @ApiOperationSupport(order = 13)
    @GetMapping("/query/corp/tree")
    public JsonContent<List<Tree<String>>> queryCorpTree() {
        return breezeAdminOrgService.queryCorpTree();
    }
    @Operation(summary = "查询公司下部门")
    @Parameter(name = "code",description = "公司编码")
    @ApiOperationSupport(order = 14)
    @GetMapping("/query/dept/tree")
    public JsonContent<List<Tree<String>>> queryDeptTree(String code) {
        return breezeAdminOrgService.queryDeptTree(code);
    }
    @Operation(summary = "查询部门下岗位")
    @Parameter(name = "code",description = "部门编码")
    @ApiOperationSupport(order = 15)
    @GetMapping("/query/job/tree")
    public JsonContent<List<Tree<String>>> queryJobTree(String code) {
        return breezeAdminOrgService.queryJobTree(code);
    }
    @Operation(summary = "保存修改")
    @ApiOperationSupport(order = 21)
    @PostMapping("/save")
    public JsonContent<Object> save(@Valid @RequestBody BreezeAdminCorpSaveArgs args) {
        return breezeAdminOrgService.save(args);
    }

    @Operation(summary = "删除单个", description = "级联删除其下级")
    @ApiOperationSupport(order = 22)
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id) {
        return breezeAdminOrgService.delete(id);
    }
    @Operation(summary = "删除批量", description = "级联删除其下级")
    @ApiOperationSupport(order = 23)
    @PostMapping("/delete/batch")
    public JsonContent<Object> delete(@Valid @RequestBody BreezeAdminCorpBatchArgs args) {
        return breezeAdminOrgService.deleteBatch(args);
    }
    @Operation(summary = "启用禁用")
    @ApiOperationSupport(order = 24)
    @Parameter(name = "id",description = "组织ID")
    @PostMapping("/enable")
    public JsonContent<Object> enable(String id) {
        return breezeAdminOrgService.enable(id);
    }
    @Operation(summary = "启用禁用")
    @ApiOperationSupport(order = 25)
    @PostMapping("/enable/batch")
    public JsonContent<Object> enable(@Valid @RequestBody BreezeAdminCorpBatchArgs args) {
        return breezeAdminOrgService.enableBatch(args.getIdList());
    }
}
