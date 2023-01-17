package cn.fanzy.breeze.minio.test;

import cn.fanzy.breeze.minio.utils.BreezeMinioMultipartClient;
import cn.hutool.json.JSONUtil;
import io.minio.ListPartsResponse;
import io.minio.MinioAsyncClient;
import org.junit.jupiter.api.Test;

public class BreezeMinioMultipartTemplateTest {

    @Test
    void test(){
        MinioAsyncClient minioClient = MinioAsyncClient.builder()
                .endpoint("http://minio.zaiyang.top")
                .credentials("minioadmin", "minioadmin")
                .build();
        BreezeMinioMultipartClient template=new BreezeMinioMultipartClient(minioClient);
        try {
            ListPartsResponse test = template.listMultipart("test", "", "2023/01/16/63c4bf2bdf9447a45b35e754.log", "43cb1ee5-41ae-422d-9bf2-db3c905421df");
            System.out.println(JSONUtil.toJsonStr(test));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2(){
        MinioAsyncClient minioClient = MinioAsyncClient.builder()
                .endpoint("http://minio.zaiyang.top")
                .credentials("minioadmin", "minioadmin")
                .build();
        BreezeMinioMultipartClient template=new BreezeMinioMultipartClient(minioClient);
        try {
            String uploadId = template.getUploadId("test", "", "test.log");
            System.out.println(uploadId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    void test3(){
        MinioAsyncClient minioClient = MinioAsyncClient.builder()
                .endpoint("http://minio.zaiyang.top")
                .credentials("minioadmin", "minioadmin")
                .build();
        BreezeMinioMultipartClient template=new BreezeMinioMultipartClient(minioClient);
        try {
            ListPartsResponse test = template.listMultipart("test", "", "test.log", "d30a9d44-c769-411c-a7c3-ac6d55e1ca1d");
            System.out.println(JSONUtil.toJsonStr(test));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
