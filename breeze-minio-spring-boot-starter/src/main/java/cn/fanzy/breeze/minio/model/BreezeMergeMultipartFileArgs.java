package cn.fanzy.breeze.minio.model;

import lombok.Data;

/**
 * 分片上传前端请求参数
 * @author fanzaiyang
 */
@Data
public class BreezeMergeMultipartFileArgs {
    /**
     * minio配置文件名
     */
    private String uploadId;
    /**
     * 合并后的文件存储桶名 称
     */
    private String bucketName;
    /**
     * 合并后的文件名称唯一
     */
    private String objectName;

}
