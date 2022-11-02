package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;

/**
 * 系统账户表(SysAccount)表实体类
 *
 * @author fasnzaiyang
 * @since 2021-09-27 18:09:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SysFile", description = "文件表")
@Entity(tableName = "sys_file")
public class SysFile extends IBaseEntity {
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id", type = Types.VARCHAR, length = 36)
    @ApiModelProperty(value = "主键", position = 0)
    private String id;

    /**
     * 文件的名称
     */
    @Column(name = "file_name", type = Types.VARCHAR, length = 255)
    @ApiModelProperty(value = "文件的名称", position = 1)
    private String fileName;
    /**
     * 保存到Minio的名字（唯一）
     */
    @Column(name = "object_name", type = Types.VARCHAR, length = 2048)
    @ApiModelProperty(value = "保存到Minio的名字（唯一），数据库保存", position = 2)
    private String objectName;

    /**
     * 文件大小
     */
    @Column(name = "file_mb_size", type = Types.DECIMAL, length = 5, precision = 2)
    @ApiModelProperty(value = "文件大小,单位：Mb", position = 5)
    private Double fileMbSize;
    /**
     * 存储桶名称
     */
    @Column(name = "bucket_name", type = Types.VARCHAR, length = 200)
    @ApiModelProperty(value = "存储桶名称", position = 6)
    private String bucketName;
    /**
     * 存储桶host地址
     */
    @Column(name = "bucket_host", type = Types.VARCHAR, length = 255)
    @ApiModelProperty(value = "存储桶host地址", position = 7)
    private String bucketHost;

    /**
     * 预览地址
     */
    @Column(name = "preview_url", type = Types.VARCHAR)
    @ApiModelProperty(value = "预览地址", position = 8)
    private String previewUrl;

}

