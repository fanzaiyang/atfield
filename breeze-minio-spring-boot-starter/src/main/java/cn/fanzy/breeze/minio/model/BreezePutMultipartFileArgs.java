package cn.fanzy.breeze.minio.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 分片上传前端请求参数
 * @author fanzaiyang
 */
@Data
public class BreezePutMultipartFileArgs {
    /**
     * minio配置文件名
     */
    private String minioConfigName;
    /**
     * 合并后的文件存储桶名称
     */
    private String finalBucketName;
    /**
     * 合并后的文件名称唯一
     */
    private String finalObjectName;
    /**
     * 上传的存储桶名称
     */
    private String chunkBucketName;
    /**
     * 上传后文件名称唯一
     */
    private String chunkObjectName;

    /**
     * 当前第几片
     */
    private int chunkNumber;
    /**
     * 当前分片大小
     */
    private long currentChunkSize;
    /**
     * 每个分片大小
     */
    private long chunkSize;
    /**
     * 分片总个数
     */
    private int totalChunks;
    /**
     * 文件唯一值MD5
     */
    private String identifier;

    /**
     * 分片文件，要求每片要大于5Mb
     */
    private MultipartFile file;
}
