package com.example.breeze.web;

import cn.fanzy.breeze.minio.config.BreezeMinioConfiguration;
import cn.fanzy.breeze.minio.model.BreezeBucketPolicy;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.utils.BreezeBucketEffectEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BreezeMinioTests {

    @Test
    void getBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        String bucketPolicy = minioService.getBucketPolicy();
        System.out.println(bucketPolicy);
    }

    @Test
    void setBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService.setBucketPolicy("/test/2021-09-29/*", BreezeBucketEffectEnum.Allow);
    }
    @Test
    void removeBucketPolicy() {
        BreezeMinioService minioService = BreezeMinioConfiguration.instance();
        minioService.removeBucketPolicy("/test/2021-09-29/*");
    }
}
