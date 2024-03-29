package cn.fanzy.atfield.upload.service.impl;

import cn.fanzy.atfield.upload.configuration.UploadSingleConfiguration;
import cn.fanzy.atfield.upload.model.*;
import cn.fanzy.atfield.upload.property.UploadProperty;
import cn.fanzy.atfield.upload.service.AtFieldPartUploadService;
import cn.fanzy.atfield.upload.service.AtFieldUploadService;
import cn.fanzy.atfield.upload.utils.BreezeFileTypeUtil;
import cn.fanzy.atfield.upload.utils.BreezeObjectGenerate;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
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
public class AtFieldPartUploadServiceImpl implements AtFieldPartUploadService {
    private final JdbcTemplate jdbcTemplate;
    private final UploadProperty properties;

    @Override
    public FilePartUploadResponse beforeUpload(ParamFilePartPutDto param) {
        AtFieldUploadService atFieldUploadService = UploadSingleConfiguration.instance();
        Assert.notBlank(param.getIdentifier(), "参与文件MD5值（identifier）不能为空！");
        String sql = "select * from " + getTableName() + " t where t.del_flag=0 and t.identifier=? limit 1";
        List<FilePartUploadDO> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(FilePartUploadDO.class),
                param.getIdentifier());
        List<FilePartUploadVo> partList = new ArrayList<>();
        if (StrUtil.isBlank(param.getObjectName())) {
            param.setObjectName(BreezeObjectGenerate.objectName(BreezeFileTypeUtil.getFileType(param.getFileName())));
        }
        if (CollUtil.isEmpty(query)) {
            // 不存在，需要新的上传
            String uploadId = atFieldUploadService.initiateMultipartUpload(param.getObjectName());
            Map<String, String> mapParam = new HashMap<>();
            mapParam.put("uploadId", uploadId);
            for (int i = 1; i <= param.getTotalChunks(); i++) {
                mapParam.put("partNumber", i + "");
                partList.add(FilePartUploadVo.builder()
                        .currentPartNumber(i)
                        .uploadUrl(atFieldUploadService.getPresignedObjectUrl(Method.PUT, param.getObjectName(), null, null, mapParam))
                        .finished(false)
                        .build());
            }
            String insSql = "insert into " + getTableName() + " (id, identifier, upload_id, file_name, bucket_host, bucket_name, object_name, total_chunk_num, total_file_size,chunk_size, begin_time, end_time, spend_second, status, create_by, create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(insSql, IdUtil.getSnowflakeNextIdStr(),
                    param.getIdentifier(),
                    uploadId,
                    param.getFileName(),
                    atFieldUploadService.getBucketHost(),
                    atFieldUploadService.getBucket(),
                    param.getObjectName(),
                    param.getTotalChunks(),
                    param.getFileSize(),
                    param.getChunkSize(),
                    new Date(), null, null, 0, "MINIO_SERVER", new Date());
            return FilePartUploadResponse.builder()
                    .bucketName(atFieldUploadService.getBucket()).finished(false)
                    .totalChunks(param.getTotalChunks())
                    .objectName(param.getObjectName())
                    .partList(partList)
                    .identifier(param.getIdentifier())
                    .chunkSize(param.getChunkSize())
                    .build();
        }
        // 文件上传过，秒传
        FilePartUploadDO file = query.get(0);
        if (file.getStatus() == 1) {
            BigDecimal decimal = new BigDecimal(file.getTotalFileSize())
                    .divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP);

            return FilePartUploadResponse.builder()
                    .bucketName(file.getBucketName())
                    .finished(true)
                    .objectName(file.getObjectName())
                    .identifier(param.getIdentifier())
                    .chunkSize(param.getChunkSize())
                    .totalChunks(param.getTotalChunks())
                    .finishedFile(FileUploadResponse.builder()
                            .id(file.getId())
                            .etag(file.getId())
                            .endpoint(file.getBucketHost())
                            .bucket(file.getBucketName())
                            .objectName(file.getObjectName())
                            .previewUrl(atFieldUploadService.getPreviewUrl(file.getObjectName()))
                            .fileName(file.getFileName())
                            .fileMbSize(decimal.setScale(2, RoundingMode.HALF_UP).doubleValue())
                            .build())
                    .build();
        }
        // 断点续传，已上传部分
        try {
            List<PartSummary> partedList = atFieldUploadService.listParts(file.getObjectName(), file.getUploadId());
            if (CollUtil.isNotEmpty(partedList)) {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("uploadId", file.getUploadId());
                for (int i = 1; i <= param.getTotalChunks(); i++) {
                    int finalI = i;
                    Optional<PartSummary> first = partedList.stream().filter(item -> item.getPartNumber() == finalI).findFirst();
                    // 如果该分片已经上传过了，则返回完成
                    if (first.isPresent()) {
                        partList.add(FilePartUploadVo.builder()
                                .currentPartNumber(i)
                                .uploadUrl(null)
                                .finished(true)
                                .etag(first.get().getETag())
                                .size(first.get().getSize())
                                .build());
                        continue;
                    }
                    paramMap.put("partNumber", i + "");
                    partList.add(FilePartUploadVo.builder()
                            .currentPartNumber(i)
                            .uploadUrl(atFieldUploadService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, paramMap))
                            .finished(false)
                            .build());
                }
            } else {
                Map<String, String> paramMap = new HashMap<>();
                paramMap.put("uploadId", file.getUploadId());
                for (int i = 1; i <= param.getTotalChunks(); i++) {
                    paramMap.put("partNumber", i + "");
                    partList.add(FilePartUploadVo.builder()
                            .currentPartNumber(i)
                            .uploadUrl(atFieldUploadService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, paramMap))
                            .finished(false)
                            .build());
                }
            }
        } catch (Exception e) {
            // 这里是断点续传失败，新增模式
            String uploadId = atFieldUploadService.initiateMultipartUpload(param.getObjectName());
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("uploadId", uploadId);
            for (int i = 1; i <= param.getTotalChunks(); i++) {
                paramMap.put("partNumber", i + "");
                partList.add(FilePartUploadVo.builder()
                        .currentPartNumber(i)
                        .uploadUrl(atFieldUploadService.getPresignedObjectUrl(Method.PUT, param.getObjectName(), null, null, paramMap))
                        .finished(false)
                        .build());
            }
            String insSql = "update " + getTableName() + " set upload_id=?,update_time=? where id=?";
            jdbcTemplate.update(insSql, uploadId, new Date(), file.getId());
            return FilePartUploadResponse.builder()
                    .bucketName(atFieldUploadService.getBucket()).finished(false)
                    .totalChunks(param.getTotalChunks())
                    .objectName(param.getObjectName())
                    .partList(partList)
                    .identifier(param.getIdentifier())
                    .chunkSize(param.getChunkSize())
                    .build();
        }

        return FilePartUploadResponse.builder()
                .bucketName(param.getBucketName()).finished(false)
                .objectName(param.getObjectName())
                .identifier(param.getIdentifier())
                .totalChunks(param.getTotalChunks())
                .chunkSize(param.getChunkSize())
                .partList(partList)
                .build();
    }

    @Override
    public FilePartUploadVo getPreSignedObjectUrl(String identifier, int partNumber, String minioConfigName) {
        if (partNumber < 1) {
            throw new RuntimeException("partNumber不能小于1");
        }
        AtFieldUploadService atFieldUploadService = UploadSingleConfiguration.instance();
        String sql = "select * from " + getTableName() + " t where t.del_flag=0 and t.identifier=? limit 1";
        List<FilePartUploadDO> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(FilePartUploadDO.class), identifier);
        Assert.notEmpty(query, "该上传任务不存在，请先初始化！");
        FilePartUploadDO file = query.get(0);
        List<PartSummary> partSummaryList = queryListPart(file.getUploadId(), file.getObjectName(), minioConfigName);
        if (CollUtil.isNotEmpty(partSummaryList)) {
            Optional<PartSummary> first = partSummaryList.stream().filter(item -> item.getPartNumber() == partNumber).findFirst();
            if (first.isPresent()) {
                // 已上传完成
                return FilePartUploadVo.builder()
                        .finished(true)
                        .size(first.get().getSize())
                        .etag(first.get().getETag())
                        .currentPartNumber(partNumber)
                        .build();
            }
        }
        Map<String, String> param = new HashMap<>();
        param.put("uploadId", file.getUploadId());
        param.put("partNumber", partNumber + "");
        String url = atFieldUploadService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, param);
        return FilePartUploadVo.builder()
                .finished(false)
                .currentPartNumber(partNumber)
                .uploadUrl(url)
                .build();
    }

    @Override
    public List<PartSummary> queryListPart(String uploadId, String objectName, String minioConfigName) {
        AtFieldUploadService atFieldUploadService = UploadSingleConfiguration.instance();
        return atFieldUploadService.listParts(objectName, uploadId);
    }

    @Override
    public FileUploadResponse mergeChunk(String identifier, String minioConfigName) {
        AtFieldUploadService atFieldUploadService = UploadSingleConfiguration.instance();
        String sql = "select * from " + getTableName() + " t where t.del_flag=0 and (t.identifier=? or t.upload_id=?) limit 1";
        List<FilePartUploadDO> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(FilePartUploadDO.class), identifier, identifier);
        Assert.notEmpty(query, "未找到合并文件！");
        FilePartUploadDO file = query.get(0);
        List<PartSummary> partList = atFieldUploadService.listParts(file.getObjectName(), file.getUploadId());
        Assert.notEmpty(partList, "分片尚未完成，无法执行合并！");
        Assert.isTrue(partList.size() == file.getTotalChunkNum(), "文件分片未上传完成「{}/{}」，请稍后在试！", partList.size(), file.getTotalChunkNum());
        CompleteMultipartUploadResult response = atFieldUploadService.completeMultipartUpload(file.getObjectName(), file.getUploadId(), partList);
        Date now = new Date();
        long between = DateUtil.between(file.getBeginTime(), now, DateUnit.SECOND);
        jdbcTemplate.update("update " + getTableName() + " set status = 1,end_time=?,spend_second=?,update_by='MINIO_SERVER',update_time=? where id=?", now, between, now, file.getId());
        BigDecimal decimal = new BigDecimal(file.getTotalFileSize())
                .divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP);
        return FileUploadResponse.builder()
                .id(file.getId())
                .etag(response.getETag())
                .endpoint(atFieldUploadService.getBucketHost())
                .bucket(atFieldUploadService.getBucket())
                .objectName(response.getKey())
                .previewUrl(atFieldUploadService.getPreviewUrl(response.getKey()))
                .fileName(file.getFileName())
                .fileMbSize(decimal.setScale(2, RoundingMode.HALF_UP).doubleValue())
                .build();
    }

    private String getTableName() {
        return properties.getApi().getTableName();
    }
}
