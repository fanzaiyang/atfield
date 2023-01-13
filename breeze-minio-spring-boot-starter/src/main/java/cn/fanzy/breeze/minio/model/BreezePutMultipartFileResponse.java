package cn.fanzy.breeze.minio.model;

import lombok.Data;

import java.util.List;

/**
 * 分片上传前端请求参数
 * @author fanzaiyang
 */
@Data
public class BreezePutMultipartFileResponse {
    /**
     * 是否完成
     */
    private boolean finished;
    /**
     * 合并后的文件存储桶名称
     */
    private String bucketName;
    /**
     * 合并后的文件名称唯一
     */
    private String objectName;

    private List<BreezeMultipartFileEntity> finishedFiles;
}
