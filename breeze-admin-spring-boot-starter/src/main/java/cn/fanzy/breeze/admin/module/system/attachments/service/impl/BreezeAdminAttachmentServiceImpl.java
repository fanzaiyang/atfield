package cn.fanzy.breeze.admin.module.system.attachments.service.impl;

import cn.fanzy.breeze.admin.module.entity.SysFile;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentBatchArgs;
import cn.fanzy.breeze.admin.module.system.attachments.args.BreezeAdminAttachmentQueryArgs;
import cn.fanzy.breeze.admin.module.system.attachments.service.BreezeAdminAttachmentService;
import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.utils.BreezeFileTypeUtil;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.model.JsonContent;
import cn.fanzy.breeze.web.utils.HttpUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.sagacity.sqltoy.model.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 微风管理附件服务impl
 *
 * @author fanzaiyang
 * @since 2022-11-01
 */
@AllArgsConstructor
public class BreezeAdminAttachmentServiceImpl implements BreezeAdminAttachmentService {
    private final SqlToyHelperDao sqlToyHelperDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public JsonContent<List<SysFile>> upload(String prefix, HttpServletRequest request) {
        List<SysFile> list = new ArrayList<>();
        List<MultipartFile> fileList = HttpUtil.getMultipartFileList(request);
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();

        for (MultipartFile file : fileList) {
            String objectName = "";
            BreezeMinioResponse response = null;
            if (StrUtil.isNotBlank(prefix)) {
                // 前缀不为空
                objectName = prefix + IdUtil.objectId() + "." + BreezeFileTypeUtil.getFileType(file);
                response = minioService.upload(file, objectName);
            } else {
                response = minioService.upload(file);
            }
            list.add(SysFile.builder()
                    .bucketHost(response.getEndpoint())
                    .fileName(response.getFileName())
                    .bucketName(response.getBucket())
                    .fileMbSize(response.getFileMbSize())
                    .objectName(response.getObjectName())
                    .previewUrl(response.getPreviewUrl())
                    .build());
        }
        sqlToyHelperDao.saveAll(list);
        return JsonContent.success(list);
    }

    @Override
    public JsonContent<SysFile> getFileInfo(String id) {
        if (StrUtil.startWith(id,"http")) {
            return JsonContent.success(SysFile.builder().previewUrl(id).build());
        }
        SysFile sysFile = sqlToyHelperDao.findOne(Wrappers.lambdaWrapper(SysFile.class).eq(SysFile::getId, id));
        Assert.notNull(sysFile, "未找到id为「{}」的附件！", id);
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        String previewUrl = minioService.getPreviewUrl(sysFile.getObjectName());
        sysFile.setPreviewUrl(previewUrl);
        return JsonContent.success(sysFile);
    }

    @Override
    public JsonContent<Page<SysFile>> queryPage(BreezeAdminAttachmentQueryArgs args) {
        Page<SysFile> page = sqlToyHelperDao.findPage(Wrappers.lambdaWrapper(SysFile.class)
                .like(StrUtil.isNotBlank(args.getFileName()), SysFile::getFileName, args.getFileName())
                .like(StrUtil.isNotBlank(args.getBucketName()), SysFile::getBucketName, args.getBucketName())
                .ge(StrUtil.isNotBlank(args.getStartTime()), SysFile::getCreateTime, args.getStartTime())
                .le(StrUtil.isNotBlank(args.getEndTime()), SysFile::getCreateTime, args.getEndTime())
                .eq(IBaseEntity::getDelFlag, 0)
                .orderByDesc(IBaseEntity::getCreateTime), new Page<>(args.getPageSize(), args.getPageNum()));
        return JsonContent.success(page);
    }

    @Override
    public JsonContent<Object> delete(BreezeAdminAttachmentBatchArgs args) {
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysFile.class)
                .set(IBaseEntity::getDelFlag, 1)
                .in(SysFile::getId, args.getIdList()));
        return JsonContent.success();
    }
}
