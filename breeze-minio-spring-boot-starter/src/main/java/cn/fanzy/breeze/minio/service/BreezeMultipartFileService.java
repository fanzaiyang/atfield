package cn.fanzy.breeze.minio.service;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezeMultipartFileEntity;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;

import java.util.List;

/**
 * 分片上传接口
 */
public interface BreezeMultipartFileService {
    /**
     * <h2>上传之前文件校验</h2>
     * <ul>
     *     <li>若文件存在则返回已上传的分片集合，前端根据已上传部分进行忽略。</li>
     *     <li>若文件不存在则返回空集合</li>
     * </ul>
     * @param identifier 文件的MD5
     * @return 已上传部分的集合List of {@link BreezeMultipartFileEntity}
     */
    List<BreezeMultipartFileEntity> beforeUpload(String identifier);

    /**
     * 分片上传
     * @param args {@link BreezePutMultipartFileArgs} 上传请求参数
     * @return 上传结果
     */
    BreezeMinioResponse chunkUpload(BreezePutMultipartFileArgs args);

    /**
     * 根据文件的MD5合并文件
     * @param identifier 文件的MD5
     * @param minioConfigName minio配置文件名
     * @param bucketName 保存到存储桶
     * @param objectName 文件唯一值
     * @return 合并结果
     */
    BreezeMinioResponse mergeChunk(String identifier,String minioConfigName,String bucketName,String objectName);
    BreezeMinioResponse mergeChunk(List<BreezeMultipartFileEntity> fileList, String minioConfigName, String bucketName, String objectName);
}
