package cn.fanzy.breeze.minio.model;

import lombok.Data;

/**
 * 分片上传前端请求参数
 *
 * @author fanzaiyang
 */
@Data
public class BreezePutMultipartFileArgs {
    /**
     * 配置文件中使用那个minio实例
     */
    private String minioConfigName;
    /**
     * 合并后的文件存储桶名称
     */
    private String bucketName;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 合并后的文件名称唯一
     */
    private String objectName;

    /**
     * 文件总大小
     */
    private Long fileSize;
    /**
     * 每个分片大小
     */
    private long chunkSize;
    /**
     * 分片总个数
     */
    private Integer totalChunks;
    /**
     * 文件唯一值MD5
     */
    private String identifier;
    public int getTotalChunks() {
        if (totalChunks == null || totalChunks == 0) {
            return (int) Math.ceil(fileSize * 1.0 / chunkSize);
        }
        return totalChunks;
    }
}
