package cn.fanzy.breeze.admin.module.system.account.args;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "主键")
    private String id;
    @Schema(description = "编码")
    private String code;
    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;
    /**
     * wx用户id
     */
    @Schema(description = "微信ID")
    private String wxUserId;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    private String passowrd;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "性别")
    private Integer sex;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String telnum;

    @Schema(description = "工作手机号码")
    private String workTelnum;

    @Schema(description = "身份证号")
    private String idnum;

    @Schema(description = "iccid")
    private String iccid;
    /**
     * 电子邮件
     */
    @Schema(description = "用户邮箱")
    private String email;
    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    private String corpCode;
    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    private String corpName;
    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String deptCode;
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
    /**
     * 职务编码
     */
    @Schema(description = "职务编码")
    private String jobCode;
    /**
     * 职务名称
     */
    @Schema(description = "职务名称")
    private String jobName;
    /**
     * 状态;状态，0-禁用，1-启用
     */
    @Schema(description = "状态;状态，0-禁用，1-启用")
    private Integer status;

    /**
     * 用户类型
     */
    @Schema(description = "1-内部账户，2-外部账号，3-临时账号")
    private Integer userType;
    @Schema(description = "角色集合")
    private Set<String> roleIdList;
}
