package cn.fanzy.breeze.minio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezePutMultiPartFile {
    /**
     * 当前分片序号
     */
    private int currentPartNumber;

    /**
     * 当前分片未上传完成是，此字段不为空，作为前端上传地址，请求方式PUT
     * 上传的URL uploadMethod: 'PUT', octet
     */
    private String uploadUrl;

    /**
     * 是否已上传完成
     */
    private boolean finished;

    /**
     * 当前分片唯一值
     */
    private String etag;

    /**
     * 当前分片大小
     */
    private Long size;
}
