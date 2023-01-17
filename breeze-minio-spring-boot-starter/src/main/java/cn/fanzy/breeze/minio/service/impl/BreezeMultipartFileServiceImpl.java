package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.*;
import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.minio.utils.BreezeFileTypeUtil;
import cn.fanzy.breeze.minio.utils.BreezeObjectGenerate;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.PartSummary;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@AllArgsConstructor
public class BreezeMultipartFileServiceImpl implements BreezeMultipartFileService {
    private final JdbcTemplate jdbcTemplate;
    private final BreezeMinIOProperties properties;

    @Override
    public BreezePutMultipartFileResponse beforeUpload(BreezePutMultipartFileArgs args) {
        Assert.notBlank(args.getIdentifier(), "参与文件MD5值（identifier）不能为空！");
        String sql = "select * from " + getTableName() + " t where t.del_flag=0 and t.identifier=? limit 1";
        List<BreezeMultipartFileEntity> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), args.getIdentifier());
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(args.getMinioConfigName());
        List<BreezePutMultiPartFile> partList = new ArrayList<>();
        if (StrUtil.isBlank(args.getObjectName())) {
            args.setObjectName(BreezeObjectGenerate.objectName(BreezeFileTypeUtil.getFileType(args.getFileName())));
        }
        if (CollUtil.isEmpty(query)) {
            // 不存在，需要新的上传
            String uploadId = minioService.initiateMultipartUpload(args.getObjectName());
            Map<String, String> param = new HashMap<>();
            param.put("uploadId", uploadId);
            for (int i = 1; i <= args.getTotalChunks(); i++) {
                param.put("partNumber", i + "");
                partList.add(BreezePutMultiPartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, args.getObjectName(), null, null, param))
                        .finished(false)
                        .build());
            }
            String insSql = "insert into " + getTableName() + " (id, identifier, upload_id, file_name, bucket_host, bucket_name, object_name, total_chunk_num, total_file_size,chunk_size, begin_time, end_time, spend_second, status, create_by, create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(insSql, IdUtil.getSnowflakeNextIdStr(), args.getIdentifier(), uploadId, args.getFileName(), minioService.getBucketHost(),
                    minioService.getBucket(), args.getObjectName(), args.getTotalChunks(), args.getFileSize(), args.getChunkSize(), new Date(),
                    null, null, 0, "MINIO_SERVER", new Date());
            return BreezePutMultipartFileResponse.builder()
                    .bucketName(minioService.getBucket()).finished(false)
                    .totalChunks(args.getTotalChunks())
                    .objectName(args.getObjectName())
                    .partList(partList)
                    .identifier(args.getIdentifier())
                    .chunkSize(args.getChunkSize())
                    .build();
        }
        // 文件上传过，秒传
        BreezeMultipartFileEntity file = query.get(0);
        if (file.getStatus() == 1) {
            return BreezePutMultipartFileResponse.builder()
                    .bucketName(file.getBucketName())
                    .finished(true)
                    .objectName(file.getObjectName())
                    .identifier(args.getIdentifier())
                    .chunkSize(args.getChunkSize())
                    .totalChunks(args.getTotalChunks())
                    .build();
        }
        // 断点续传，已上传部分
        List<PartSummary> partedList = minioService.listParts(file.getObjectName(), file.getUploadId());
        if (CollUtil.isNotEmpty(partedList)) {
            Map<String, String> param = new HashMap<>();
            param.put("uploadId", file.getUploadId());
            for (int i = 1; i <= args.getTotalChunks(); i++) {
                int finalI = i;
                Optional<PartSummary> first = partedList.stream().filter(item -> item.getPartNumber() == finalI).findFirst();
                // 如果该分片已经上传过了，则返回完成
                if (first.isPresent()) {
                    partList.add(BreezePutMultiPartFile.builder()
                            .currentPartNumber(i)
                            .uploadUrl(null)
                            .finished(true)
                            .etag(first.get().getETag())
                            .size(first.get().getSize())
                            .build());
                    continue;
                }
                param.put("partNumber", i + "");
                partList.add(BreezePutMultiPartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, param))
                        .finished(false)
                        .build());
            }
        } else {
            Map<String, String> param = new HashMap<>();
            param.put("uploadId", file.getUploadId());
            for (int i = 1; i <= args.getTotalChunks(); i++) {
                param.put("partNumber", i + "");
                partList.add(BreezePutMultiPartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, param))
                        .finished(false)
                        .build());
            }
        }
        return BreezePutMultipartFileResponse.builder()
                .bucketName(args.getBucketName()).finished(false)
                .objectName(args.getObjectName())
                .identifier(args.getIdentifier())
                .totalChunks(args.getTotalChunks())
                .chunkSize(args.getChunkSize())
                .partList(partList)
                .build();
    }

    @Override
    public BreezePutMultiPartFile getPresignedObjectUrl(String identifier, int partNumber, String minioConfigName) {
        if (partNumber < 1) {
            throw new RuntimeException("partNumber不能小于1");
        }
        String sql = "select * from " + getTableName() + " t where t.del_flag=0 and t.identifier=? limit 1";
        List<BreezeMultipartFileEntity> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), identifier);
        Assert.notEmpty(query, "该上传任务不存在，请先初始化！");
        BreezeMultipartFileEntity file = query.get(0);
        List<PartSummary> partSummaryList = queryListPart(file.getUploadId(), file.getObjectName(), minioConfigName);
        if (CollUtil.isNotEmpty(partSummaryList)) {
            Optional<PartSummary> first = partSummaryList.stream().filter(item -> item.getPartNumber() == partNumber).findFirst();
            if (first.isPresent()) {
                // 已上传完成
                return BreezePutMultiPartFile.builder()
                        .finished(true).size(first.get().getSize())
                        .etag(first.get().getETag())
                        .currentPartNumber(partNumber)
                        .build();
            }
        }
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(minioConfigName);
        Map<String, String> param = new HashMap<>();
        param.put("uploadId", file.getUploadId());
        param.put("partNumber", partNumber + "");
        String url = minioService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, param);
        return BreezePutMultiPartFile.builder()
                .finished(false)
                .currentPartNumber(partNumber)
                .uploadUrl(url)
                .build();
    }

    @Override
    public List<PartSummary> queryListPart(String uploadId, String objectName, String minioConfigName) {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(minioConfigName);
        return minioService.listParts(objectName, uploadId);
    }

    @Override
    public BreezeMinioResponse mergeChunk(String identifier, String minioConfigName) {
        String sql = "select * from " + getTableName() + " t where t.del_flag=0 and (t.identifier=? or t.upload_id=?) limit 1";
        List<BreezeMultipartFileEntity> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), identifier, identifier);
        Assert.notEmpty(query, "未找到合并文件！");
        BreezeMultipartFileEntity file = query.get(0);
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(minioConfigName);
        List<PartSummary> partList = minioService.listParts(file.getObjectName(), file.getUploadId());
        Assert.notEmpty(partList, "分片尚未完成，无法执行合并！");
        Assert.isTrue(partList.size() == file.getTotalChunkNum(), "文件分片未上传完成「{}/{}」，请稍后在试！", partList.size(), file.getTotalChunkNum());
        CompleteMultipartUploadResult response = minioService.completeMultipartUpload(file.getObjectName(), file.getUploadId(), partList);
        jdbcTemplate.update("update " + getTableName() + " set status = 1,update_by='MINIO_SERVER',update_time=now() where id=?", file.getId());
        BigDecimal decimal = new BigDecimal(file.getTotalFileSize()).divide(new BigDecimal(1048576));
        return BreezeMinioResponse.builder()
                .etag(response.getETag())
                .bucket(minioService.getBucket())
                .objectName(response.getKey())
                .previewUrl(minioService.getPreviewUrl(response.getKey()))
                .fileName(file.getFileName())
                .fileMbSize(decimal.setScale(2, RoundingMode.HALF_UP).doubleValue())
                .build();
    }

    private String getTableName() {
        return properties.getApi().getTableName();
    }
}
