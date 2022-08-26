package com.example.breeze.web.entity;

import lombok.Data;
import cn.fanzy.breeze.sqltoy.core.config.annotation.Column;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    @Column(name = "tenant_id", type = Types.VARCHAR)
    private String tenantId;

    /**
     * 删除标志：0-未删除，1-已删除
     */
    @Column(name = "del_flag", type = Types.INTEGER, defaultValue = "0")
    private Integer delFlag;
    /**
     * 创建者
     */
//    @Column(name = "create_by", type = Types.VARCHAR)
    private String createBy;
    /**
     * 创建时间
     */
//    @Column(name = "create_time", type = Types.DATE)
    private Date createTime;
    /**
     * 更新者
     */
    @Column(name = "update_by", type = Types.VARCHAR)
    private String updateBy;
    /**
     * 更新时间
     */
    @Column(name = "update_time", type = Types.DATE)
    private Date updateTime;
    /**
     * 版本号
     */
    @Column(name = "revision", type = Types.INTEGER)
    private Integer revision;
}
