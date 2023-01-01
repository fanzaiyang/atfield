package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.utils.IdStrategy;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Entity;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;
import java.util.Date;

/**
 * 系统账户表(SysAccount)表实体类
 *
 * @author fanzaiyang
 * @since 2021-09-27 18:09:29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统账户表")
@Entity(tableName = "sys_account")
public class SysAccount extends IBaseEntity {
    private static final long serialVersionUID = 5729730651403333369L;
    /**
     * 主键
     */
    @Id(strategy = IdStrategy.GENERATOR, generator = IdStrategy.Generator.DEFAULT)
    @Column(name = "id",type = Types.VARCHAR,length = 36, comment = "主键")
    @Schema(description = "主键")
    private String id;
    @Column(name = "code",type = Types.VARCHAR,length = 36, comment = "编码")
    @Schema(description = "编码")
    private String code;
    /**
     * 头像
     */
    @Column(name = "avatar",type = Types.LONGVARCHAR)
    @Schema(description = "头像")
    private String avatar;
    /**
     * wx用户id
     */
    @Column(name = "wx_user_id",type = Types.VARCHAR,length = 64)
    @Schema(description = "微信ID")
    private String wxUserId;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @Column(name = "username",type = Types.VARCHAR,length = 20)
    private String username;
    /**
     * 登录密码
     */
    @Schema(description = "登录密码")
    @Column(name = "password",type = Types.VARCHAR,length = 90)
    private String password;
    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @Column(name = "nick_name",type = Types.VARCHAR,length = 90)
    private String nickName;

    @Schema(description = "性别")
    @Column(name = "sex",type = Types.SMALLINT,length = 1)
    private Integer sex;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @Column(name = "telnum",type = Types.VARCHAR,length = 90)
    private String telnum;

    @Schema(description = "工作手机号码")
    @Column(name = "work_telnum",type = Types.VARCHAR,length = 90)
    private String workTelnum;

    @Schema(description = "身份证号")
    @Column(name = "idnum",type = Types.VARCHAR,length = 20)
    private String idnum;
    /**
     * 电子邮件
     */
    @Schema(description = "用户邮箱")
    @Column(name = "email",type = Types.VARCHAR,length = 200)
    private String email;
    /**
     * 公司编码
     */
    @Schema(description = "公司编码")
    @Column(name = "corp_code",type = Types.VARCHAR,length = 36)
    private String corpCode;
    /**
     * 公司名称
     */
    @Schema(description = "公司名称")
    @Column(name = "corp_name",type = Types.VARCHAR,length = 90)
    private String corpName;
    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    @Column(name = "dept_code",type = Types.VARCHAR,length = 36)
        private String deptCode;
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @Column(name = "dept_name",type = Types.VARCHAR,length = 90)
    private String deptName;
    /**
     * 职务编码
     */
    @Schema(description = "职务编码")
    @Column(name = "job_code",type = Types.VARCHAR,length = 36)
    private String jobCode;
    /**
     * 职务名称
     */
    @Schema(description = "职务名称")
    @Column(name = "job_name",type = Types.VARCHAR,length = 90)
    private String jobName;
    /**
     * 状态;状态，0-禁用，1-启用
     */
    @Schema(description = "状态;状态，0-禁用，1-启用")
    @Column(name = "status",type = Types.SMALLINT,length = 1)
    private Integer status;
    @Schema(description = "最后一次登录的IP")
    @Column(name = "last_login_ip",type = Types.VARCHAR,length = 20)
    private String lastLoginIp;
    @Schema(description = "最后一次登录的时间")
    @Column(name = "last_login_date",type = Types.DATE)
    private Date lastLoginDate;
}

