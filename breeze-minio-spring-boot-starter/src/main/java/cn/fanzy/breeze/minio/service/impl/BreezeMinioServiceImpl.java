package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
public class BreezeMinioServiceImpl implements BreezeMinioService {
    private final BreezeMinIOProperties.MinioServerConfig config;
    private final MinioClient client;

    private final MinioClient innerClient;

    public BreezeMinioServiceImpl(BreezeMinIOProperties.MinioServerConfig config) {
        this.config = config;
        Assert.notNull(config, "未找到的配置！");
        client = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
        innerClient = MinioClient.builder()
                .endpoint(config.getEndpoint())
                .credentials(config.getAccessKey(), config.getSecretKey())
                .build();
    }

    @Override
    public MinioClient client() {
        return client;
    }

    @Override
    public MinioClient innerClient() {
        return innerClient;
    }

    @Override
    public void bucketExistsAndCreate(String bucket) {
        if (!StringUtils.hasLength(bucket)) {
            bucket = config.getBucket();
        }
        if (!StringUtils.hasLength(bucket)) {
            throw new RuntimeException("未配置存储桶!");
        }
        try {
            boolean exists = innerClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());
            if (!exists) {
                innerClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public BreezeMinioResponse upload(String fileName, String objectName, InputStream inputStream, String contentType, String bucket) {
        Assert.notBlank(fileName, "文件名不为空！");
        Assert.notBlank(bucket, "存储桶不能为空！");
        Assert.notBlank(objectName, "objectName不能为空！");
        Assert.notBlank(contentType, "contentType不能为空！");
        Assert.notNull(inputStream, "文件流不能为空！");
        bucketExistsAndCreate(config.getBucket());
        try {
            int available = inputStream.available();
            BigDecimal decimal =new BigDecimal(available).divide(new BigDecimal(1048576));
            ObjectWriteResponse response = innerClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .contentType(contentType)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
            return BreezeMinioResponse.builder()
                    .etag(response.etag())
                    .bucket(bucket)
                    .endpoint(config.getEndpoint())
                    .objectName(objectName)
                    .fileName(fileName)
                    .fileMbSize(decimal.setScale(2, RoundingMode.HALF_UP).doubleValue())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
