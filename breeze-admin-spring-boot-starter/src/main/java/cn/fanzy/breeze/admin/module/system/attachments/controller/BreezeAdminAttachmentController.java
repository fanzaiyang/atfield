package cn.fanzy.breeze.admin.module.system.attachments.controller;

import cn.fanzy.breeze.admin.module.entity.SysFile;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentQueryArgs;
import cn.fanzy.breeze.admin.module.system.attachments.service.BreezeAdminAttachmentService;
import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.web.model.JsonContent;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.sagacity.sqltoy.model.Page;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 微风管理附件控制器
 *
 * @author fanzaiyang
 * @date 2022-11-01
 */
@Api(tags = "「微风组件」附件管理")
@ApiSupport(author = "微风组件", order = 993001)
@AllArgsConstructor
@ConditionalOnClass(BreezeMinioConfiguration.class)
@RestController
@RequestMapping("${breeze.admin.prefix.api?:/${breeze.admin.prefix.account?:/sys/attachment}}")
public class BreezeAdminAttachmentController {
    private final BreezeAdminAttachmentService breezeAdminAttachmentService;

    @ApiOperation(value = "上传",notes = "支持批量上传，无需指定文件名。")
    @ApiOperationSupport(order = 1)
    @PostMapping("/upload")
    public JsonContent<List<SysFile>> upload(String prefix, HttpServletRequest request) {
        return breezeAdminAttachmentService.upload(prefix, request);
    }
    @ApiOperation(value = "获取单个")
    @ApiOperationSupport(order = 2)
    @GetMapping("/get")
    public JsonContent<SysFile> getFileInfo(String id) {
        return breezeAdminAttachmentService.getFileInfo(id);
    }

    @ApiOperation(value = "分页查询")
    @ApiOperationSupport(order = 3)
    @PostMapping("/query")
    public JsonContent<Page<SysFile>> queryPage(@Valid @RequestBody BreezeAdminAttachmentQueryArgs args){
        return breezeAdminAttachmentService.queryPage(args);
    }
    @ApiOperation(value = "删除")
    @ApiOperationSupport(order = 4)
    @DeleteMapping("/delete")
    public JsonContent<Object> delete(String id){
        return breezeAdminAttachmentService.delete(id);
    }
}
