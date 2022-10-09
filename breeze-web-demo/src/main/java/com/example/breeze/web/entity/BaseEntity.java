package com.example.breeze.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private String tenantId;

    /**
     * 删除标志：0-未删除，1-已删除
     */
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
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 版本号
     */
    private Integer revision;
}
