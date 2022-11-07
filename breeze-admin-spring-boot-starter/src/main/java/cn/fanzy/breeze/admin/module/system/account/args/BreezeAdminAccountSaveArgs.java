package cn.fanzy.breeze.admin.module.system.account.args;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Set;

/**
 * 微风管理员帐户保存参数
 *
 * @author fanzaiyang
 * @date 2022-11-07
 */
@Data
public class BreezeAdminAccountSaveArgs {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 0)
    private String id;
    @ApiModelProperty(value = "编码", position = 2)
    private String code;
    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", position = 13)
    private String avatar;
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
     * 用户类型
     */
    @ApiModelProperty(value = "1-内部账户，2-外部账号，3-临时账号", position = 24)
    private Integer userType;
    @ApiModelProperty(value = "角色集合", position = 25)
    private Set<String> roleIdList;
}
