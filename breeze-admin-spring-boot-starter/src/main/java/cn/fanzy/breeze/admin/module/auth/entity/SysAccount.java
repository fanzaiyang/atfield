package cn.fanzy.breeze.admin.module.auth.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

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
@ApiModel(value = "SysAccount", description = "系统账户表")
public class SysAccount extends IBaseEntity {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 0)
    private String id;

    /**
     * wx用户id
     */
    @ApiModelProperty(value = "微信ID", position = 1)
    private String wxUserId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", position = 2)
    private String username;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码", position = 3)
    private String passowrd;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", position = 4)
    private String nickName;

    @ApiModelProperty(value = "性别", position = 4)
    private Integer sex;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", position = 5)
    private String telnum;

    @ApiModelProperty(value = "工作手机号码", position = 5)
    private String workTelnum;

    @ApiModelProperty(value = "身份证号", position = 5)
    private String idnum;

    @ApiModelProperty(value = "iccid", position = 5)
    private String iccid;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "用户邮箱", position = 5)
    private String email;
    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码", position = 6)
    private String corpCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", position = 7)
    private String corpName;
    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码", position = 8)
    private String deptCode;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", position = 9)
    private String deptName;
    /**
     * 职务编码
     */
    @ApiModelProperty(value = "职务编码", position = 10)
    private String jobCode;
    /**
     * 职务名称
     */
    @ApiModelProperty(value = "职务名称", position = 11)
    private String jobName;
    /**
     * 状态;状态，0-禁用，1-启用
     */
    @ApiModelProperty(value = "状态;状态，0-禁用，1-启用", position = 12)
    private Integer status;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", position = 13)
    private String avatar;
    @ApiModelProperty(value = "密码是否安全，0-否，1-是", position = 16)
    private Integer passSafe;
    @ApiModelProperty(value = "密码修改时间", position = 20)
    private Date passTime;
    @ApiModelProperty(value = "锁定;0-未锁定，1-已锁定", position = 21)
    private Integer locked;
    @ApiModelProperty(value = "锁定时间，即解锁时间", position = 22)
    private Date lockedTime;
    @ApiModelProperty(value = "锁定时间，即解锁时间", position = 23)
    private String lockedRemarks;
    @ApiModelProperty(value = "允许删除;0-不可删除，1-可删除", position = 24)
    private Integer deleteEnable;

    @ApiModelProperty(value = "用户类型", position = 24)
    private Integer userType;
    @ApiModelProperty(value = "密码修改时间", position = 20)
    private Date delTime;
}

