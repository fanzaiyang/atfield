package cn.fanzy.atfield.upload.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分片上传前端请求参数
 *
 * @author fanzaiyang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePartUploadResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -5341659138736055449L;
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
    private List<FilePartUploadVo> partList;

    /**
     * 已上传完成的文件信息
     * 用于前端拿到文件信息后向后端传参数。
     */
    private FileUploadResponse finishedFile;
}
