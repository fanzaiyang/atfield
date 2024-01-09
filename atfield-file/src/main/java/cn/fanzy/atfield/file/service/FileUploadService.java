package cn.fanzy.atfield.file.service;

import cn.fanzy.atfield.file.model.FileUploadResponse;
import cn.fanzy.atfield.file.model.MinioBucketPolicy;
import cn.fanzy.atfield.file.property.FileUploadProperty;
import cn.fanzy.atfield.file.utils.MinioBucketEffectEnum;
import com.amazonaws.services.s3.model.CompleteMultipartUploadResult;
import com.amazonaws.services.s3.model.PartSummary;
import io.minio.ComposeObjectArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface FileUploadService {

    void setConfig(FileUploadProperty.MinioServerConfig config);
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
     * 变更存储桶
     *
     * @param bucket 桶
     * @return BreezeMinioServiceImpl
     */
    FileUploadService bucket(String bucket);


    /**
     * 获取存储桶
     *
     * @return {@link String}
     */
    String getBucket();

    /**
     * 获取存储桶主机
     *
     * @return {@link String}
     */
    String getBucketHost();
    /**
     * 桶存在和创造
     *
     * @param bucket 桶
     */
    void bucketExistsAndCreate(String bucket);



    /**
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(MultipartFile file);


    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(MultipartFile file, String objectName);


    /**
     * 上传
     *
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(MultipartFile file, String objectName, String contentType);


    /**
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(File file);

    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(File file, String objectName);
    /**
     * 上传
     *
     * @param file        文件
     * @param objectName  对象名称
     * @param contentType 内容类型
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(File file, String objectName, String contentType);

    /**
     * 上传到默认存储桶
     *
     * @param inputStream 输入流
     * @param objectName  对象名称
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @return {@link FileUploadResponse}
     */
    FileUploadResponse upload(InputStream inputStream, String objectName, String fileName, String contentType);

    FileUploadResponse merge(ComposeObjectArgs args);
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
     * @param effect       政策
     */
    void setBucketPolicy(String objectPrefix, MinioBucketEffectEnum effect);

    /**
     * 制定桶政策
     *
     * @param args   arg游戏
     */
    void setBucketPolicy(MinioBucketPolicy args);

    /**
     * 删除桶政策
     *
     * @param objectPrefix 对象前缀
     */
    void removeBucketPolicy(String objectPrefix);

    /**
     * 得到预览url,失效：7天
     *
     * @param objectName 对象名称
     * @return {@link String}
     */
    String getPreviewUrl(String objectName);

    /**
     * 得到公共预览url
     * <pre>
     *     ⚠️ 注意：这里会把该文件设置为公共可读，请谨慎使用。
     * </pre>
     *
     * @param objectName 对象名称
     * @return {@link String}
     */
    String getPublicPreviewUrl(String objectName);


    /**
     * 分片上传，获取上传ID
     * @param objectName 对象名唯一
     * @return String
     */
    String initiateMultipartUpload(String objectName);


    /**
     * 删除上传的分片任务
     * @param objectName object
     * @param uploadId uploadId
     */
    void abortMultipartUpload(String objectName, String uploadId);

    /**
     * 合并分片
     * @param objectName objectName
     * @param uploadId uploadId
     * @param parts List {@link PartSummary}
     * @return CompleteMultipartUploadResult
     */
    CompleteMultipartUploadResult completeMultipartUpload(String objectName, String uploadId, List<PartSummary> parts);

    /**
     * 查询分片
     * @param objectName objectName
     * @param uploadId uploadId
     * @return List {@link PartSummary}
     */
    List<PartSummary> listParts(String objectName, String uploadId);


    /**
     * 获取上传路径
     * @param method {@link Method}
     * @param objectName objectName
     * @param expireDuration expireDuration
     * @param timeUnit {@link TimeUnit}
     * @param extraQueryParams Map
     * @return String
     */
    String getPresignedObjectUrl(Method method, String objectName, Integer expireDuration, TimeUnit timeUnit, Map<String, String> extraQueryParams);

    /**
     * 删除
     *
     * @param objectName 对象名称
     */
    void delete(String objectName);
}
