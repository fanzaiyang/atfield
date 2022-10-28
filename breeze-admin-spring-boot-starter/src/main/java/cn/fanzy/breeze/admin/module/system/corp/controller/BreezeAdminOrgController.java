package cn.fanzy.breeze.admin.module.system.corp.controller;

import cn.fanzy.breeze.admin.module.system.corp.args.BreezeAdminCorpSaveArgs;
import cn.fanzy.breeze.admin.module.system.corp.service.BreezeAdminOrgService;
import cn.fanzy.breeze.admin.module.entity.SysOrg;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.tree.Tree;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "「微风组件」组织管理")
@ApiSupport(author = "微风组件", order = 992041)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.org?:/sys/org}}")
public class BreezeAdminOrgController {
    private final BreezeAdminOrgService breezeAdminOrgService;

    @ApiOperation(value = "查询组织异步")
    @ApiOperationSupport(order = 11)
    @ApiImplicitParam(name = "id", value = "主键ID")
    @GetMapping("/query/async")
    public JsonContent<List<SysOrg>> queryCorpAsync(String id) {
        return breezeAdminOrgService.queryCorpAsync(id);
    }

    @ApiOperation(value = "查询组织树")
    @ApiOperationSupport(order = 12)
    @ApiImplicitParam(name = "nodeType", value = "节点类型:corp,dept,job多个用逗号隔开。")
    @GetMapping("/query/tree")
    public JsonContent<List<Tree<String>>> queryOrgTree(String nodeType) {
        return breezeAdminOrgService.queryOrgTree(nodeType);
    }
    @ApiOperation(value = "查询所有公司")
    @ApiOperationSupport(order = 13)
    @GetMapping("/query/corp/tree")
    public JsonContent<List<Tree<String>>> queryCorpTree() {
        return breezeAdminOrgService.queryCorpTree();
    }
    @ApiOperation(value = "查询公司下部门")
    @ApiImplicitParam(name = "code",value = "公司编码")
    @ApiOperationSupport(order = 14)
    @GetMapping("/query/dept/tree")
    public JsonContent<List<Tree<String>>> queryDeptTree(String code) {
        return breezeAdminOrgService.queryDeptTree(code);
    }
    @ApiOperation(value = "查询部门下岗位")
    @ApiImplicitParam(name = "code",value = "部门编码")
    @ApiOperationSupport(order = 15)
    @GetMapping("/query/job/tree")
    public JsonContent<List<Tree<String>>> queryJobTree(String code) {
        return breezeAdminOrgService.queryJobTree(code);
    }
    @ApiOperation(value = "保存修改")
    @ApiOperationSupport(order = 21)
    @PostMapping("/save")
    public JsonContent<Object> save(@Valid @RequestBody BreezeAdminCorpSaveArgs args) {
        return breezeAdminOrgService.save(args);
    }

    @ApiOperation(value = "删除单个", notes = "级联删除其下级")
    @ApiOperationSupport(order = 22)
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id) {
        return breezeAdminOrgService.delete(id);
    }
    @ApiOperation(value = "删除批量", notes = "级联删除其下级")
    @ApiOperationSupport(order = 23)
    @DeleteMapping("/delete/batch")
    public JsonContent<Object> delete(List<String> id) {
        return breezeAdminOrgService.deleteBatch(id);
    }
    @ApiOperation(value = "启用禁用")
    @ApiOperationSupport(order = 24)
    @PostMapping("/enable")
    public JsonContent<Object> enable(String id) {
        return breezeAdminOrgService.enable(id);
    }
    @ApiOperation(value = "启用禁用")
    @ApiOperationSupport(order = 24)
    @PostMapping("/enable/batch")
    public JsonContent<Object> enable(List<String> id) {
        return breezeAdminOrgService.enableBatch(id);
    }
}
