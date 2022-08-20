package cn.fanzy.breeze.minio.service.impl;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.InputStream;

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
    public BreezeMinioResponse upload(String bucket, String objectName, InputStream inputStream,String contentType) {
        bucketExistsAndCreate(config.getBucket());
        try {
            innerClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .contentType(contentType)
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
