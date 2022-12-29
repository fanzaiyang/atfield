package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "文件表")
@Entity(tableName = "sys_file")
public class SysFile extends IBaseEntity {
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id", type = Types.VARCHAR, length = 36)
    @Schema(description = "主键")
    private String id;

    /**
     * 文件的名称
     */
    @Column(name = "file_name", type = Types.VARCHAR, length = 255)
    @Schema(description = "文件的名称")
    private String fileName;
    /**
     * 保存到Minio的名字（唯一）
     */
    @Column(name = "object_name", type = Types.VARCHAR, length = 2048)
    @Schema(description = "保存到Minio的名字（唯一），数据库保存")
    private String objectName;

    /**
     * 文件大小
     */
    @Column(name = "file_mb_size", type = Types.DECIMAL, length = 5, precision = 2)
    @Schema(description = "文件大小,单位：Mb")
    private Double fileMbSize;
    /**
     * 存储桶名称
     */
    @Column(name = "bucket_name", type = Types.VARCHAR, length = 200)
    @Schema(description = "存储桶名称")
    private String bucketName;
    /**
     * 存储桶host地址
     */
    @Column(name = "bucket_host", type = Types.VARCHAR, length = 255)
    @Schema(description = "存储桶host地址")
    private String bucketHost;

    /**
     * 预览地址
     */
    @Column(name = "preview_url", type = Types.VARCHAR)
    @Schema(description = "预览地址")
    private String previewUrl;

}

