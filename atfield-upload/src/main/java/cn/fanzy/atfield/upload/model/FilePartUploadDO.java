package cn.fanzy.atfield.upload.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 分片存储到数据中的实体类
 *
 * @author fanzaiyang
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilePartUploadDO implements Serializable {
    @Serial
    private static final long serialVersionUID = -8655737027760215359L;
    /**
     * 编号
     */
    private String id;

    /**
     * 上传 ID
     */
    private String uploadId;
    /**
     * 文件的唯一标识identifier（md5摘要）
     */
    private String identifier;

    /**
     *
     */
    private String fileName;

    /**
     * 存储桶host地址
     */
    private String bucketHost;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     *
     */
    private String objectName;

    /**
     * 分片个数
     */
    private Integer totalChunkNum;

    /**
     *
     */
    private Long totalFileSize;

    /**
     * 每个分块大小
     */
    private Long chunkSize;

    /**
     * 上传开始时间
     */
    private Date beginTime;

    /**
     * 上传结束时间
     */
    private Date endTime;

    /**
     * 后台上传耗时
     */
    private Integer spendSecond;

    /**
     * 文件状态：0-上传中，1-上传完成
     */
    private Integer status;

    /**
     * 删除标志,0:未删除 1:已删除
     */
    private Integer delFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     *
     */
    private String updateBy;

    /**
     *
     */
    private Date updateTime;

    /**
     *
     */
    private Integer revision;

    /**
     *
     */
    private Integer tenantId;
}
