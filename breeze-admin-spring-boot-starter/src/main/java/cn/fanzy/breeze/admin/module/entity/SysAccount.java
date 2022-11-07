package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;
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
@Entity(tableName = "sys_account")
public class SysAccount extends IBaseEntity {
    private static final long serialVersionUID = 5729730651403333369L;
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id",type = Types.VARCHAR,length = 36, comment = "主键")
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    @Column(name = "code",type = Types.VARCHAR,length = 36, comment = "编码")
    @ApiModelProperty(value = "编码", position = 2)
    private String code;
    /**
     * 头像
     */
    @Column(name = "avatar",type = Types.LONGVARCHAR)
    @ApiModelProperty(value = "头像", position = 13)
    private String avatar;
    /**
     * wx用户id
     */
    @Column(name = "wx_user_id",type = Types.VARCHAR,length = 64)
    @ApiModelProperty(value = "微信ID", position = 1)
    private String wxUserId;
    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", position = 2)
    @Column(name = "username",type = Types.VARCHAR,length = 20)
    private String username;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码", position = 3)
    @Column(name = "password",type = Types.VARCHAR,length = 90)
    private String password;
    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称", position = 4)
    @Column(name = "nick_name",type = Types.VARCHAR,length = 90)
    private String nickName;

    @ApiModelProperty(value = "性别", position = 4)
    @Column(name = "sex",type = Types.SMALLINT,length = 1)
    private Integer sex;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", position = 5)
    @Column(name = "telnum",type = Types.VARCHAR,length = 90)
    private String telnum;

    @ApiModelProperty(value = "工作手机号码", position = 5)
    @Column(name = "work_telnum",type = Types.VARCHAR,length = 90)
    private String workTelnum;

    @ApiModelProperty(value = "身份证号", position = 5)
    @Column(name = "idnum",type = Types.VARCHAR,length = 20)
    private String idnum;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "用户邮箱", position = 5)
    @Column(name = "email",type = Types.VARCHAR,length = 200)
    private String email;
    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码", position = 6)
    @Column(name = "corp_code",type = Types.VARCHAR,length = 36)
    private String corpCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", position = 7)
    @Column(name = "corp_name",type = Types.VARCHAR,length = 90)
    private String corpName;
    /**
     * 部门编码
     */
    @ApiModelProperty(value = "部门编码", position = 8)
    @Column(name = "dept_code",type = Types.VARCHAR,length = 36)
    private String deptCode;
    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", position = 9)
    @Column(name = "dept_name",type = Types.VARCHAR,length = 90)
    private String deptName;
    /**
     * 职务编码
     */
    @ApiModelProperty(value = "职务编码", position = 10)
    @Column(name = "job_code",type = Types.VARCHAR,length = 36)
    private String jobCode;
    /**
     * 职务名称
     */
    @ApiModelProperty(value = "职务名称", position = 11)
    @Column(name = "job_name",type = Types.VARCHAR,length = 90)
    private String jobName;
    /**
     * 状态;状态，0-禁用，1-启用
     */
    @ApiModelProperty(value = "状态;状态，0-禁用，1-启用", position = 12)
    @Column(name = "status",type = Types.SMALLINT,length = 1)
    private Integer status;
    @ApiModelProperty(value = "最后一次登录的IP", position = 13)
    @Column(name = "last_login_ip",type = Types.VARCHAR,length = 20)
    private String lastLoginIp;
    @ApiModelProperty(value = "最后一次登录的时间", position = 13)
    @Column(name = "last_login_date",type = Types.DATE)
    private Date lastLoginDate;
}

