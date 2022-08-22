package cn.fanzy.breeze.minio.service;

import cn.fanzy.breeze.minio.model.BreezeBucketPolicy;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.utils.BreezeBucketEffectEnum;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface BreezeMinioService {

    void setConfig(BreezeMinIOProperties.MinioServerConfig config);
    /**
     * 公网客户端
     * <pre>主要用于查询类</pre>
     *
     * @return {@link MinioClient}
     */
    MinioClient client();

    /**
     * 内网客户端
     * <pre>主要用于存储类</pre>
     *
     * @return {@link MinioClient}
     */
    MinioClient innerClient();

    /**
     * 桶存在和创造
     *
     * @param bucket 桶
     */
    void bucketExistsAndCreate(String bucket);


    /**
     * 上传
     *
     * @param bucket      桶
     * @param inputStream 输入流
     * @param objectName  对象名称
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, InputStream inputStream, String objectName, String fileName, String contentType);

    /**
     * 上传到默认存储桶
     *
     * @param inputStream 输入流
     * @param objectName  对象名称
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(InputStream inputStream, String objectName, String fileName, String contentType);

    /**
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file);

    /**
     * 上传到指定存储桶
     *
     * @param bucket     桶
     * @param file       文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, MultipartFile file);

    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file, String objectName);

    /**
     * 上传到指定存储桶且指定对象名
     *
     * @param bucket     桶
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, MultipartFile file, String objectName);

    /**
     * 上传
     *
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file, String objectName, String contentType);

    /**
     * 上传
     *
     * @param bucket      桶
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, MultipartFile file, String objectName, String contentType);

    /**
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file);

    /**
     * 上传到指定存储桶
     *
     * @param bucket     桶
     * @param file       文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, File file);

    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file, String objectName);

    /**
     * 上传到指定存储桶且指定对象名
     *
     * @param bucket     桶
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, File file, String objectName);

    /**
     * 上传
     *
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file, String objectName, String contentType);

    /**
     * 上传
     *
     * @param bucket      桶
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(String bucket, File file, String objectName, String contentType);

    /**
     * 得到对象
     *
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    InputStream getObject(String objectName);

    /**
     * 得到对象
     *
     * @param bucket     桶
     * @param objectName 对象名称
     * @return {@link InputStream}
     */
    InputStream getObject(String bucket, String objectName);

    /**
     * 删除对象
     *
     * @param objectName 对象名称
     */
    void removeObject(String objectName);

    /**
     * 删除对象
     *
     * @param bucket     桶
     * @param objectName 对象名称
     */
    void removeObject(String bucket, String objectName);

    /**
     * 桶政策
     *
     * @return {@link String}
     */
    String getBucketPolicy();

    /**
     * 桶政策
     *
     * @param bucket 桶
     * @return {@link String}
     */
    String getBucketPolicy(String bucket);

    /**
     * 制定桶政策
     *
     * @param objectPrefix 对象前缀
     * @param policy       政策
     */
    void setBucketPolicy(String objectPrefix, BreezeBucketEffectEnum policy);

    /**
     * 制定桶政策
     *
     * @param bucket       桶
     * @param objectPrefix 对象前缀
     * @param policy       政策
     */
    void setBucketPolicy(String bucket, String objectPrefix, BreezeBucketEffectEnum policy);

    /**
     * 制定桶政策
     *
     * @param args   arg游戏
     * @param bucket 桶
     */
    void setBucketPolicy(String bucket,BreezeBucketPolicy args)    ;

    /**
     * 删除桶政策
     *
     * @param bucket       桶
     * @param objectPrefix 对象前缀
     */
    void removeBucketPolicy(String bucket, String objectPrefix);

    /**
     * 删除桶政策
     *
     * @param objectPrefix 对象前缀
     */
    void removeBucketPolicy(String objectPrefix);
}
