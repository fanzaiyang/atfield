package cn.fanzy.breeze.minio.service;

import cn.fanzy.breeze.minio.model.BreezeMinioResponse;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileArgs;
import cn.fanzy.breeze.minio.model.BreezePutMultipartFileResponse;
import com.amazonaws.services.s3.model.PartSummary;

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
     * @param args {@link BreezePutMultipartFileArgs}
     * @return {@link BreezePutMultipartFileResponse}
     */
    BreezePutMultipartFileResponse beforeUpload(BreezePutMultipartFileArgs args);

    List<PartSummary> queryListPart(String uploadId);

    /**
     * 根据文件的MD5合并文件
     * @param identifier 文件MD5
     * @param minioConfigName minio配置文件名
     * @return 合并结果
     */
    BreezeMinioResponse mergeChunk(String identifier,String minioConfigName);
    
}
