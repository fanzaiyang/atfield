package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezeMultipartFileEntity;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
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

import java.util.*;

@Slf4j
@AllArgsConstructor
public class BreezeMultipartFileServiceImpl implements BreezeMultipartFileService {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public BreezePutMultipartFileResponse beforeUpload(BreezePutMultipartFileArgs args) {
        Assert.notBlank(args.getIdentifier(), "参与文件MD5值（identifier）不能为空！");
        String sql = "select * from sys_multipart_file_info t where t.del_flag=0 and t.identifier=? limit 1";
        List<BreezeMultipartFileEntity> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), args.getIdentifier());
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(args.getMinioConfigName());
        List<BreezePutMultipartFileResponse.PartFile> partList = new ArrayList<>();
        if (StrUtil.isBlank(args.getObjectName())) {
            args.setObjectName(BreezeObjectGenerate.objectName(BreezeFileTypeUtil.getFileType(args.getFileName())));
        }
        if (CollUtil.isEmpty(query)) {
            // 不存在，需要新的上传
            String uploadId = minioService.getUploadId(args.getObjectName());
            Map<String, String> param = new HashMap<>();
            param.put("uploadId", uploadId);
            for (int i = 1; i <= args.getTotalChunks(); i++) {
                param.put("partNumber", i + "");
                partList.add(BreezePutMultipartFileResponse.PartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, args.getObjectName(), null, null, param))
                        .finished(false)
                        .build());
            }
            String insSql = "insert into sys_multipart_file_info (id, identifier, upload_id, file_name, bucket_host, bucket_name, object_name, total_chunk_num, total_file_size,chunk_size, begin_time, end_time, spend_second, status, create_by, create_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(insSql, IdUtil.getSnowflakeNextIdStr(), args.getIdentifier(), uploadId, args.getFileName(), minioService.getBucketHost(),
                    minioService.getBucket(), args.getObjectName(), args.getTotalChunks(), args.getFileSize(), args.getChunkSize(), new Date(),
                    null, null, 0, "MINIO_SERVER", new Date());
            return BreezePutMultipartFileResponse.builder()
                    .uploadId(uploadId).bucketName(minioService.getBucket()).finished(false)
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
                    .uploadId(file.getUploadId()).bucketName(file.getBucketName()).finished(true)
                    .objectName(file.getObjectName())
                    .identifier(args.getIdentifier())
                    .chunkSize(args.getChunkSize())
                    .totalChunks(args.getTotalChunks())
                    .build();
        }
        // 断点续传，已上传部分
        List<PartSummary> partedList = minioService.listMultipart(file.getObjectName(), file.getUploadId());
        if (CollUtil.isNotEmpty(partedList)) {
            Map<String, String> param = new HashMap<>();
            param.put("uploadId", file.getUploadId());
            for (int i = 1; i <= args.getTotalChunks(); i++) {
                int finalI = i;
                Optional<PartSummary> first = partedList.stream().filter(item -> item.getPartNumber() == finalI).findFirst();
                // 如果该分片已经上传过了，则返回完成
                if (first.isPresent()) {
                    partList.add(BreezePutMultipartFileResponse.PartFile.builder()
                            .currentPartNumber(i)
                            .uploadUrl(null)
                            .finished(true)
                            .build());
                    continue;
                }
                param.put("partNumber", i + "");
                partList.add(BreezePutMultipartFileResponse.PartFile.builder()
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
                partList.add(BreezePutMultipartFileResponse.PartFile.builder()
                        .currentPartNumber(i)
                        .uploadUrl(minioService.getPresignedObjectUrl(Method.PUT, file.getObjectName(), null, null, param))
                        .finished(false)
                        .build());
            }
        }
        return BreezePutMultipartFileResponse.builder()
                .uploadId(file.getUploadId()).bucketName(args.getBucketName()).finished(false)
                .objectName(args.getObjectName()).partList(partList)
                .identifier(args.getIdentifier())
                .totalChunks(args.getTotalChunks())
                .chunkSize(args.getChunkSize())
                .build();
    }

    @Override
    public List<PartSummary> queryListPart(String uploadId) {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        return minioService.listMultipart("2023/01/16/63c4bf2bdf9447a45b35e754.log", uploadId);
    }

    @Override
    public BreezeMinioResponse mergeChunk(String identifier, String minioConfigName) {
        String sql = "select * from sys_multipart_file_info t where t.del_flag=0 and (t.identifier=? or t.upload_id=?) limit 1";
        List<BreezeMultipartFileEntity> query = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), identifier, identifier);
        Assert.notEmpty(query, "未找到合并文件！");
        BreezeMultipartFileEntity file = query.get(0);
        BreezeMinioService minioService = BreezeMinioConfiguration.instance(minioConfigName);
        List<PartSummary> partList = minioService.listMultipart(file.getObjectName(), file.getUploadId());
        Assert.notEmpty(partList, "分片尚未完成，无法执行合并！");
        Assert.isTrue(partList.size() == file.getTotalChunkNum(), "文件分片未上传完成「{}/{}」，请稍后在试！", partList.size(), file.getTotalChunkNum());
        CompleteMultipartUploadResult response = minioService.mergeMultipart(file.getObjectName(), file.getUploadId(), partList);
        jdbcTemplate.update("update sys_multipart_file_info set status = 1,update_by='MINIO_SERVER',update_time=now() where id=?", file.getId());
        return BreezeMinioResponse.builder()
                .etag(response.getETag())
                .bucket(minioService.getBucket())
                .objectName(response.getKey())
                .previewUrl(minioService.getPreviewUrl(response.getKey()))
                .build();
    }

}
