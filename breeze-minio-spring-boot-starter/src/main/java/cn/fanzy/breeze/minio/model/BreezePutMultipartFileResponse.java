package cn.fanzy.breeze.minio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分片上传前端请求参数
 * @author fanzaiyang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezePutMultipartFileResponse {

    /**
     * 文件唯一值MD5
     */
    private String identifier;
    /**
     * 是否完成，true-上传完成，false-未完成
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

    /**
     * 总分片个数
     */
    private int totalChunks;

    /**
     * 每片大小byte
     */
    private long chunkSize;

    /**
     * 已上传的分片
     */
    private List<BreezePutMultiPartFile> partList;
}
