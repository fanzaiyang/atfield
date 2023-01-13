package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.*;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import io.minio.ListPartsResponse;
import io.minio.ObjectWriteResponse;
import io.minio.http.Method;
import io.minio.messages.Part;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class BreezeMultipartFileServiceImpl implements BreezeMultipartFileService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BreezePutMultipartFileResponse beforeUpload(BreezePutMultipartFileArgs args) {
        Assert.notBlank(args.getIdentifier(), "参与文件MD5值（identifier）不能为空！");
        String sql = "select * from sys_multipart_file_info t where t.del_flag=0 and t.identifier=? limit 1";
        List<BreezeMultipartFileEntity> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), args.getIdentifier());
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(args.getBucketName());
        List<BreezePutMultipartFileResponse.PartFile> partList = new ArrayList<>();
        if (CollUtil.isEmpty(query)) {
            // 不存在，需要新的上传
            String uploadId = minioService.getUploadId(null, args.getObjectName(), null, null);
            for (int i = 0; i < args.getTotalChunks(); i++) {
                String partName = StrUtil.format("/{}/{}.part", args.getIdentifier(), "chuck_" + i);
                partList.add(BreezePutMultipartFileResponse.PartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, partName, null, null))
                        .finished(false)
                        .build());
            }
            return BreezePutMultipartFileResponse.builder()
                    .uploadId(uploadId).bucketName(args.getBucketName()).finished(false)
                    .objectName(args.getObjectName()).partList(partList)
                    .build();
        }
        // 文件上传过，秒传
        BreezeMultipartFileEntity file = query.get(0);
        if (file.getStatus() == 1) {
            return BreezePutMultipartFileResponse.builder()
                    .uploadId(null).bucketName(file.getBucketName()).finished(true)
                    .objectName(file.getObjectName())
                    .build();
        }
        // 断点续传，已上传部分
        ListPartsResponse multipart = minioService.listMultipart(null, args.getObjectName(), null, null, file.getUploadId(), null, null);
        List<Part> partedList = multipart.result().partList();
        for (int i = 0; i < args.getTotalChunks(); i++) {
            int finalI = i;
            Optional<Part> first = partedList.stream().filter(item -> item.partNumber() == finalI).findFirst();
            // 如果该分片已经上传过了，则返回完成
            if (first.isPresent()) {
                partList.add(BreezePutMultipartFileResponse.PartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(null)
                        .finished(true)
                        .build());
                continue;
            }
            String partName = StrUtil.format("/{}/{}.part", args.getIdentifier(), "chuck_" + i);
            partList.add(BreezePutMultipartFileResponse.PartFile.builder()
                    .currentPartNumber(i)
                    .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, partName, null, null))
                    .finished(false)
                    .build());
        }
        return BreezePutMultipartFileResponse.builder()
                .uploadId(file.getUploadId()).bucketName(args.getBucketName()).finished(false)
                .objectName(args.getObjectName()).partList(partList)
                .build();
    }

    @Override
    public BreezeMinioResponse mergeChunk(BreezeMergeMultipartFileArgs args) {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(args.getBucketName());
        ListPartsResponse multipart = minioService.listMultipart(null, args.getObjectName(), null, null, args.getUploadId(), null, null);
        List<Part> partList = multipart.result().partList();
        Assert.notEmpty(partList, "未找到已上传的分片！");
        ObjectWriteResponse response = minioService.mergeMultipart(null, args.getObjectName(), args.getUploadId(), partList.toArray(new Part[0]), null, null);
        return BreezeMinioResponse.builder()
                .etag(response.etag())
                .bucket(minioService.getBucket())
                .objectName(response.object())
                .previewUrl(minioService.getPreviewUrl(response.object()))
                .build();
    }

}
