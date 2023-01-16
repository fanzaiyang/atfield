package cn.fanzy.breeze.admin.module.system.attachments.service;

import cn.fanzy.breeze.admin.module.entity.SysFile;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentBatchArgs;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentQueryArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
import cn.fanzy.breeze.web.model.JsonContent;
import org.sagacity.sqltoy.model.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 微风管理附件服务
 *
 * @author fanzaiyang
 * @since 2022-11-01
 */
public interface BreezeAdminAttachmentService {
    JsonContent<List<SysFile>> upload(String prefix, HttpServletRequest request) throws IOException;

    JsonContent<SysFile> getFileInfo(String id);

    JsonContent<Page<SysFile>> queryPage(BreezeAdminAttachmentQueryArgs args);

    JsonContent<Object> delete(BreezeAdminAttachmentBatchArgs args);

    JsonContent<BreezePutMultipartFileResponse> uploadMultipartInit(BreezePutMultipartFileArgs args);

    JsonContent<SysFile> uploadMultipartMerge(String identifier,String minioConfigName);

    JsonContent<String> getPresignedObjectUrl(String identifier, Integer partNumber, String minioConfigName);
}
