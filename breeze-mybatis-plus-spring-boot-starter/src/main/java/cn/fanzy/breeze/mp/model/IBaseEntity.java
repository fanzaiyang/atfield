package cn.fanzy.breeze.mp.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "租户号-当前年份", position = 100)
    private String tenantId;

    /**
     * 删除标志：0-未删除，1-已删除
     */
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "删除标志：0-未删除，1-已删除", position = 100)
    private Integer delFlag;
    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT, value = "create_by")
    @ApiModelProperty(value = "创建者", position = 101)
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "create_time")
    @ApiModelProperty(value = "创建时间", position = 102)
    private Date createTime;
    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_by")
    @ApiModelProperty(value = "更新者", position = 103)
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "update_time")
    @ApiModelProperty(value = "更新时间", position = 104)
    private Date updateTime;
    /**
     * 版本号
     */
    @Version
    @ApiModelProperty(value = "版本号", position = 105)
    private Integer revision;

}
