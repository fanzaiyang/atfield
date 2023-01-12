package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezeMultipartFileEntity;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.ComposeObjectArgs;
import io.minio.ComposeSource;
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

    @Override
    public List<BreezeMultipartFileEntity> beforeUpload(String identifier) {
        return jdbcTemplate.query("select * from sys_multipart_file_info t where t.del_flag=0 and t.status=1 and t.identifier=? order by t.current_chunk_index desc", BeanPropertyRowMapper.newInstance(BreezeMultipartFileEntity.class), identifier);
    }

    @Override
    public BreezeMinioResponse chunkUpload(BreezePutMultipartFileArgs args) {
        Date beginTime = new Date();
        List<BreezeMultipartFileEntity> entityList = beforeUpload(args.getIdentifier());
        // 当数据库保存已上传分片个数与上传分片总个相等时，说明上传完成。
        if (CollUtil.isNotEmpty(entityList) && entityList.size() == args.getTotalChunks()) {
            return mergeChunk(entityList, args.getMinioConfigName(), args.getFinalBucketName(), args.getFinalObjectName());
        }
        // 校验当前分片是否已经上传成功
        Optional<BreezeMultipartFileEntity> any = entityList.stream().filter(item -> item.getCurrentChunkIndex() == args.getChunkNumber()).findAny();
        if (any.isPresent()) {
            // todo
            return null;
        }
        // 执行上传逻辑
        BreezeMinioServiceImpl minioService = BreezeMinioConfiguration.instance(args.getMinioConfigName()).bucket(args.getChunkBucketName());
        BreezeMinioResponse response = minioService.upload(args.getFile(), args.getChunkObjectName());
        Date endTime = new Date();
        String insertSql = "insert into sys_multipart_file_info (id, identifier, file_name, bucket_name, bucket_host, object_name, total_chunk_num, total_file_size, chunk_size, current_chunk_size, current_chunk_index, begin_time, end_time, spend_second, status, create_by, create_time, update_by, update_time) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSql, IdUtil.getSnowflakeNextIdStr(), args.getIdentifier(), response.getFileName(), response.getBucket(), response.getEndpoint(),
                response.getObjectName(), args.getTotalChunks(), 0, args.getChunkSize(), args.getFile().getSize(),
                args.getChunkNumber(), beginTime, endTime, DateUtil.between(beginTime, endTime, DateUnit.SECOND), 1,
                "BREEZE", beginTime, "BREEZE", endTime);
        return response;
    }

    @Override
    public BreezeMinioResponse mergeChunk(String identifier, String minioConfigName, String bucketName, String objectName) {
        List<BreezeMultipartFileEntity> entityList = beforeUpload(identifier);
        // 当数据库保存已上传分片个数与上传分片总个相等时，说明上传完成。
        if (CollUtil.isEmpty(entityList) || entityList.get(0).getTotalChunkNum() != entityList.get(0).getCurrentChunkIndex()) {
            throw new RuntimeException(StrUtil.format("文件不满足合并要求，总分片{}个，已完成{}个。", entityList.get(0).getTotalChunkNum(), entityList.get(0).getCurrentChunkIndex()));
        }
        return mergeChunk(entityList, minioConfigName, bucketName, objectName);
    }

    @Override
    public BreezeMinioResponse mergeChunk(List<BreezeMultipartFileEntity> fileList, String minioConfigName, String bucketName, String objectName) {
        // 文件按照index顺序
        fileList.sort(Comparator.comparing(BreezeMultipartFileEntity::getCurrentChunkIndex));
        BreezeMinioServiceImpl minioService = BreezeMinioConfiguration.instance(minioConfigName).bucket(bucketName);
        List<ComposeSource> sources = new ArrayList<>();
        for (BreezeMultipartFileEntity file : fileList) {
            sources.add(ComposeSource.builder()
                    .bucket(file.getBucketName()).object(file.getObjectName())
                    .build());
        }
        BreezeMinioResponse merge = minioService.merge(ComposeObjectArgs.builder()
                .object(objectName)
                .sources(sources)
                .build());
        merge.setFileName(fileList.get(0).getFileName());
        merge.setFileMbSize(fileList.stream().mapToDouble(item -> {
            BigDecimal decimal = new BigDecimal(item.getCurrentChunkSize()).divide(new BigDecimal(1048576));
            return decimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
        }).sum());
        return merge;
    }
}
