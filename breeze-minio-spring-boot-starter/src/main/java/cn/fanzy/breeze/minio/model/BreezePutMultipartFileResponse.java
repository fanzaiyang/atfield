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
    private String uploadId;

    private String identifier;
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

    private int totalChunks;

    private long chunkSize;

    /**
     * 已上传的分片
     */
    private List<PartFile> partList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartFile{
        /**
         * 当前分片序号
         */
        private int currentPartNumber;

        /**
         * 上传的URL uploadMethod: 'PUT', octet
         */
        private String uploadUrl;

        /**
         * 是否已上传完成
         */
        private boolean finished;

        private String objectName;

        private String etag;

        private Long size;
    }
}
