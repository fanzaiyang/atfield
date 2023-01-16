package cn.fanzy.breeze.admin.module.system.attachments.controller;

import cn.fanzy.breeze.admin.module.entity.SysFile;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentBatchArgs;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentQueryArgs;
import cn.fanzy.breeze.admin.module.system.attachments.service.BreezeAdminAttachmentService;
import cn.fanzy.breeze.admin.module.system.attachments.vo.TinyMCEVo;
import cn.fanzy.breeze.admin.module.system.attachments.vo.WangEditorVo;
import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.StringPool;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.sagacity.sqltoy.model.Page;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 微风管理附件控制器
 *
 * @author fanzaiyang
 * @since 2022-11-01
 */
@Tag(name = "「微风组件」附件管理")
@ApiSupport(author = "微风组件", order = 993001)
@AllArgsConstructor
@ConditionalOnClass(BreezeMinioConfiguration.class)
@RestController
@RequestMapping("${breeze.admin.prefix.api:/}${breeze.admin.prefix.account:sys/attachment}")
public class BreezeAdminAttachmentController {
    private final BreezeAdminAttachmentService breezeAdminAttachmentService;

    @Operation(summary = "上传", description = "支持批量上传，无需指定文件名。")
    @ApiOperationSupport(order = 1)
    @Parameter(name = "prefix", description = "上传到服务器前缀路径")
    @PostMapping("/upload")
    public JsonContent<List<SysFile>> upload(String prefix, HttpServletRequest request) throws IOException {
        return breezeAdminAttachmentService.upload(prefix, request);
    }

    @Operation(summary = "分片上传初始化", description = "基于minio的分片上传，支持断点续传、秒传等。")
    @PostMapping("/upload/multipart/init")
    public JsonContent<BreezePutMultipartFileResponse> uploadMultipartInit(@Valid @RequestBody BreezePutMultipartFileArgs args) {
        return breezeAdminAttachmentService.uploadMultipartInit(args);
    }

    @Operation(summary = "分片合并", description = "基于minio的分片上传，支持断点续传、秒传等。")
    @Parameters({
            @Parameter(name = "identifier", description = "文件的MD5值"),
            @Parameter(name = "minioConfigName", description = "后端多minio服务端是填写，默认第一个。")
    })
    @GetMapping("/upload/multipart/merge")
    public JsonContent<SysFile> uploadMultipartMerge(String identifier, String minioConfigName) {
        return breezeAdminAttachmentService.uploadMultipartMerge(identifier, minioConfigName);
    }

    @Operation(summary = "上传TinyMCE", description = "支持TinyMCE的上传。")
    @ApiOperationSupport(order = 2)
    @Parameter(name = "prefix", description = "上传到服务器前缀路径")
    @PostMapping("/upload/tiny")
    public TinyMCEVo uploadTinyMCE(String prefix, HttpServletRequest request) throws IOException {
        JsonContent<List<SysFile>> upload = upload(prefix, request);
        if (!upload.isSuccess()) {
            throw new RuntimeException(upload.getMessage());
        }
        String previewUrl = upload.getData().get(0).getPreviewUrl();
        Assert.notBlank(previewUrl, "预览地址不能为空！");
        return new TinyMCEVo(previewUrl);
    }

    @Operation(summary = "上传wangeditor", description = "支持wangeditor的上传。")
    @ApiOperationSupport(order = 3)
    @Parameter(name = "prefix", description = "上传到服务器前缀路径")
    @PostMapping("/upload/wangeditor")
    public WangEditorVo uploadWangEditor(String prefix, HttpServletRequest request) throws IOException {
        JsonContent<List<SysFile>> upload = upload(prefix, request);
        if (!upload.isSuccess()) {
            return WangEditorVo.builder().errno(1).message(upload.getMessage()).build();
        }
        String previewUrl = upload.getData().get(0).getPreviewUrl();
        Assert.notBlank(previewUrl, "预览地址不能为空！");
        WangEditorVo vo = new WangEditorVo();
        vo.setErrno(0);
        vo.setMessage(upload.getMessage());
        WangEditorVo.Body body = new WangEditorVo.Body();
        if (StrUtil.contains(previewUrl, StringPool.QUESTION_MARK)) {
            previewUrl = StrUtil.split(previewUrl, StringPool.QUESTION_MARK).get(0);
        }
        body.setUrl(previewUrl);
        body.setAlt(upload.getData().get(0).getFileName());
        body.setHref(previewUrl);
        vo.setData(body);
        return vo;
    }

    @Operation(summary = "获取单个")
    @ApiOperationSupport(order = 4)
    @Parameter(name = "prefix", description = "文件ID")
    @GetMapping("/get")
    public JsonContent<SysFile> getFileInfo(String id) {
        return breezeAdminAttachmentService.getFileInfo(id);
    }

    @Operation(summary = "分页查询")
    @ApiOperationSupport(order = 5)
    @PostMapping("/query")
    public JsonContent<Page<SysFile>> queryPage(@Valid @RequestBody BreezeAdminAttachmentQueryArgs args) {
        return breezeAdminAttachmentService.queryPage(args);
    }

    @Operation(summary = "删除")
    @ApiOperationSupport(order = 6)
    @PostMapping("/delete")
    public JsonContent<Object> delete(@Valid @RequestBody BreezeAdminAttachmentBatchArgs args) {
        return breezeAdminAttachmentService.delete(args);
    }
}
