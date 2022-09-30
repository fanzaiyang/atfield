package com.example.breeze.web;

import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.BreezeBucketPolicy;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.service.impl.BreezeMinioServiceImpl;
import cn.fanzy.breeze.minio.utils.BreezeBucketEffectEnum;
import cn.fanzy.breeze.sqltoy.core.utils.FileUtil;
import cn.hutool.json.JSONUtil;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

@Slf4j
@SpringBootTest
class BreezeMinioTests {

    /**
     * 获取存储桶策略
     */
    @Test
    void getBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        String bucketPolicy = minioService.getBucketPolicy();
        System.out.println(bucketPolicy);
    }
    /**
     * 设置存储桶策略
     */
    @Test
    void setBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService.setBucketPolicy("/test/2021-09-29/*", BreezeBucketEffectEnum.Allow);
    }
    /**
     * 移除存储桶策略
     */
    @Test
    void removeBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService.removeBucketPolicy("/test/2021-09-29/*");
    }
    /**
     * 上传文件到默认存储桶
     */
    @Test
    void uploadFile() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        BreezeMinioResponse response = minioService.upload(FileUtil.getFile(""));
        log.info("存储结果：{}", JSONUtil.toJsonStr(response));
    }
    @Test
    void getPreviewUrl() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        String previewUrl = minioService.getPreviewUrl("objectName");
        log.info("结果：{}", previewUrl);
    }
    @Test
    void getFile() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        InputStream objectName = minioService.getObject("objectName");
    }

    /**
     * 当提供的方法不满足需要是，你可以直接获取MinIO的SDK中的MinioClient使用
     */
    @Test
    void getClient() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        MinioClient minioClient = minioService.client();
        //todo 你的自定义代码
    }

    /**
     * 指定存储桶
     */
    @Test
    void customBucket() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService = minioService.bucket("custom");
        //todo 你的自定义代码
    }
}
