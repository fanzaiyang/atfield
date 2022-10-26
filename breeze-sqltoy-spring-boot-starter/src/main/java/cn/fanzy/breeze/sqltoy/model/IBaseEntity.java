package cn.fanzy.breeze.sqltoy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.DataVersion;

import java.io.Serializable;
import java.sql.Types;
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
    @Column(name = "tenant_id",type = Types.VARCHAR,length = 36)
    private String tenantId;

    /**
     * 删除标志：0-未删除，1-已删除
     */
    @Column(name = "del_flag",type = Types.SMALLINT,length = 1)
    private Integer delFlag;
    /**
     * 创建者
     */
    @Column(name = "create_by",type = Types.VARCHAR,length = 36)
    private String createBy;
    /**
     * 创建时间
     */
    @Column(name = "create_time",type = Types.DATE)
    private Date createTime;
    /**
     * 更新者
     */
    @Column(name = "update_by",type = Types.VARCHAR,length = 36)
    private String updateBy;
    /**
     * 更新时间
     */
    @Column(name = "update_time",type = Types.DATE)
    private Date updateTime;
    /**
     * 版本号
     */
    @DataVersion(field = "revision", startDate = true)
    @Column(name = "revision",type = Types.INTEGER,length = 11)
    private Integer revision;

}
