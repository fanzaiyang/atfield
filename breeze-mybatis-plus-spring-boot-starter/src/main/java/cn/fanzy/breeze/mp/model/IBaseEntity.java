package cn.fanzy.breeze.mp.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 基础的实体
 * 支持AR模式
 *
 * @author fanzaiyang
 * @date 2021/05/31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IBaseEntity<T extends Model<?>> extends Model<T> {

    /**
     * 租户号
     */
    private String tenantId;

    /**
     * 删除标志：0-未删除，1-已删除
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer delFlag;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT, value = "create_by")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "create_time")
    private Date createTime;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_by")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    private Date updateTime;
    /**
     * 版本号
     */
    @Version
    private Integer revision;

}
