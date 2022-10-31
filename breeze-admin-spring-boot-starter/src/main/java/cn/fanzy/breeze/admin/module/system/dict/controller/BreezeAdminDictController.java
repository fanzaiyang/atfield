package cn.fanzy.breeze.admin.module.system.dict.controller;

import cn.fanzy.breeze.admin.module.entity.SysDict;
import cn.fanzy.breeze.admin.module.system.dict.args.BreezeAdminDictSaveArgs;
import cn.fanzy.breeze.admin.module.system.dict.service.BreezeAdminDictService;
import cn.fanzy.breeze.web.model.JsonContent;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "「微风组件」字典管理")
@ApiSupport(author = "微风组件", order = 992051)
@AllArgsConstructor
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.dict?:/sys/dict}}")
public class BreezeAdminDictController {
    private final BreezeAdminDictService breezeAdminDictService;

    @ApiOperation(value = "保存和修改")
    @ApiOperationSupport(order = 1)
    @PostMapping("/save")
    public JsonContent<Object> save(@Valid @RequestBody BreezeAdminDictSaveArgs args) {
        return breezeAdminDictService.save(args);
    }

    @ApiOperation(value = "删除", notes = "级联删除")
    @ApiOperationSupport(order = 2)
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id) {
        return breezeAdminDictService.delete(id);
    }

    @ApiOperation(value = "启用禁用", notes = "级联启用禁用删除")
    @ApiOperationSupport(order = 3)
    @PostMapping("/enable")
    public JsonContent<Object> enable(String id) {
        return breezeAdminDictService.enable(id);
    }

    @ApiOperation(value = "异步查询")
    @ApiOperationSupport(order = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键"),
            @ApiImplicitParam(name = "showDisable", value = "是否显示禁用，false-不显示（默认），true-显示。"),
    })
    @GetMapping("/query/async")
    public JsonContent<List<SysDict>> queryAsync(String id, boolean showDisable) {
        return breezeAdminDictService.queryAsync(id, showDisable);
    }
}