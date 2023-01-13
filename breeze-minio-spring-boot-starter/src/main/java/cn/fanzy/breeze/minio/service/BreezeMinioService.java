package cn.fanzy.breeze.minio.service;

import cn.fanzy.breeze.minio.model.BreezeBucketPolicy;
import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.impl.BreezeMinioServiceImpl;
import cn.fanzy.breeze.minio.utils.BreezeBucketEffectEnum;
import com.google.common.collect.Multimap;
import io.minio.ComposeObjectArgs;
import io.minio.ListPartsResponse;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.messages.Part;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

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
     * 变更存储桶
     *
     * @param bucket 桶
     * @return BreezeMinioServiceImpl
     */
    BreezeMinioServiceImpl bucket(String bucket);

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
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file);


    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(MultipartFile file, String objectName);


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
     * 上传到默认目录
     *
     * @param file 文件
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file);

    /**
     * 上传到指定对象名
     *
     * @param file       文件
     * @param objectName 对象名称
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(File file, String objectName);
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
     * 上传到默认存储桶
     *
     * @param inputStream 输入流
     * @param objectName  对象名称
     * @param fileName    文件名称
     * @param contentType 内容类型
     * @return {@link BreezeMinioResponse}
     */
    BreezeMinioResponse upload(InputStream inputStream, String objectName, String fileName, String contentType);

    BreezeMinioResponse merge(ComposeObjectArgs args);
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
    void setBucketPolicy(String objectPrefix, BreezeBucketEffectEnum effect);

    /**
     * 制定桶政策
     *
     * @param args   arg游戏
     */
    void setBucketPolicy(BreezeBucketPolicy args);

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
     * @param bucketName 存储桶
     * @param region region
     * @param objectName 对象名唯一
     * @param headers 头
     * @param extraQueryParams 额外参数
     * @return String
     */
    String getUploadId(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams);


    /**
     * 删除上传的分片任务
     * @param bucket 存储桶
     * @param region region
     * @param object 对象名唯一
     * @param uploadId 上传ID
     * @param headers 头
     * @param extraQueryParams 额外参数
     * @return String
     */
    String removeMultipartUpload(String bucket, String region, String object,String uploadId, Multimap<String, String> headers, Multimap<String, String> extraQueryParams);

    /**
     * 合并分片
     * @param bucketName 存储桶
     * @param region region
     * @param objectName 对象名唯一
     * @param uploadId 上传ID
     * @param parts 分片数组
     * @param extraHeaders 头
     * @param extraQueryParams 参数
     * @return
     */
    ObjectWriteResponse mergeMultipart(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams);

    /**
     * 查询分片
     * @param bucketName 存储桶
     * @param region region
     * @param objectName 对象名唯一
     * @param maxParts 最大分片个数
     * @param partNumberMaker partNumberMaker
     * @param uploadId 上传ID
     * @param extraHeaders 头
     * @param extraQueryParams 参数
     * @return
     */
    ListPartsResponse listMultipart(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams);

    /**
     *  获取带签名的临时上传元数据对象，前端可获取后，直接上传到Minio
     * @param objectName objectName
     * @return Map
     */
    Map<String, String> getPresignedPostFormData(String objectName);
}
