package cn.fanzy.breeze.minio.test;

import cn.fanzy.breeze.minio.utils.BreezeMinioMultipartClient;
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
            String test = template.getUploadId("test", "", "test.zip", null, null);
            System.out.println(test);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
