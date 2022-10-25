package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sagacity.sqltoy.config.annotation.Column;
import org.sagacity.sqltoy.config.annotation.Id;

import java.sql.Types;
import java.util.Date;
@Data
@ApiModel(value="sys_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog extends IBaseEntity {
    @Id(generator = "default")
    @Column(name = "id",type = Types.VARCHAR)
    @ApiModelProperty(value="")
    private String id;
    /**
     * 线程ID
     */
    private String traceId;
    /**
     * 业务名称
     */
    @ApiModelProperty(value="业务名称")
    private String name;

    /**
     * IP地址
     */
    @ApiModelProperty(value="IP地址")
    private String clientIp;

    /**
     * 当前登录用户主键
     */
    @ApiModelProperty(value="当前登录用户主键")
    private String userId;

    /**
     * 当前用户详情
     */
    @ApiModelProperty(value="当前用户详情")
    private String userInfo;

    /**
     * 开始时间
     */
    @ApiModelProperty(value="开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value="结束时间")
    private Date endTime;

    /**
     * 请求耗时（秒）
     */
    @ApiModelProperty(value="请求耗时（秒）")
    private Integer spendSecond;
    @ApiModelProperty(value="请求地址")
    private String requestUri;
    @ApiModelProperty(value="请求方法GET/POST")
    private String requestMethod;
    /**
     * 请求参数JSON
     */
    @ApiModelProperty(value="请求参数JSON")
    private String requestData;

    /**
     * 响应结果JSON
     */
    @ApiModelProperty(value="响应结果JSON")
    private String responseData;

    /**
     * 是否成功，1-是，0-否
     */
    @ApiModelProperty(value="是否成功，1-是，0-否")
    private Integer success;
}