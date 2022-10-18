package cn.fanzy.breeze.admin.module.system.corp.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "「微风组件」组织管理")
@ApiSupport(author = "微风组件", order = 992021)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.account?:/sys/org}}")
public class BreezeAdminOrgController {
    private final BreezeAdminOrgService breezeAdminOrgService;
    @ApiOperation(value = "查询组织异步")
    @ApiOperationSupport(order = 101)
    @ApiImplicitParam(name = "id",value = "主键ID")
    @GetMapping("/query/async")
    public JsonContent<List<SysOrg>> queryCorpAsync(String id){
        return breezeAdminOrgService.queryCorpAsync(id);
    }
    @ApiOperation(value = "查询组织异步")
    @ApiOperationSupport(order = 101)
    @ApiImplicitParam(name = "nodeType",value = "节点类型:corp,dept,job多个用逗号隔开。")
    @GetMapping("/query/tree")
    public JsonContent<List<Tree<String>>> queryCorpTree(String nodeType){
        return breezeAdminOrgService.queryCorpTree(nodeType);
    }
}
