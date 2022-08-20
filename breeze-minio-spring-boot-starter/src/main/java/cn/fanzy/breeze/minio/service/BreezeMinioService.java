package cn.fanzy.breeze.minio.service;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import io.minio.MinioClient;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface BreezeMinioService {

    /**
     * 公网客户端
     * <pre>主要用于查询类</pre>
     * @return {@link MinioClient}
     */
    MinioClient client();
    /**
     * 内网客户端
     * <pre>主要用于存储类</pre>
     * @return {@link MinioClient}
     */
    MinioClient innerClient();

    /**
     * 桶存在和创造
     *
     * @param bucket 桶
     */
    void bucketExistsAndCreate(String bucket) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 上传
     *
     * @param bucket      桶
     * @param objectName  对象名称
     * @param inputStream 输入流
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, String objectName, InputStream inputStream, String contentType);
}
