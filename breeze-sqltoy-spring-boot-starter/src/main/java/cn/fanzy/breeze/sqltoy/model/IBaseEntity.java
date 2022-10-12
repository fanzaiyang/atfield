package cn.fanzy.breeze.sqltoy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sagacity.sqltoy.config.annotation.DataVersion;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础的实体
 * 支持AR模式
 *
 * @author fanzaiyang
 * @date 2021/05/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IBaseEntity implements Serializable {


    /**
     * 租户号
     */
    private String tenantId;

    /**
     * 删除标志：0-未删除，1-已删除
     */
    private Integer delFlag;
    /**
     * 创建者
     */
    private String createBy;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新者
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 版本号
     */
    @DataVersion(field = "revision", startDate = true)
    private Integer revision;

}
