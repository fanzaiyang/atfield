package cn.fanzy.breeze.minio.model;

import lombok.Data;

@Data
public class BreezeMinioResponse {
    /**
     * MinIO地址
     */
    private String endpoint;
    /**
     * 存储桶
     */
    private String bucket;
    /**
     * 对象名
     */
    private String objectName;

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小，单位：Mb
     */
    private double fileMbSize;

    /**
     * 临时预览地址
     */
    private String previewUrl;



}
