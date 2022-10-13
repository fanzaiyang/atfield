package cn.fanzy.breeze.admin.module.entity;

import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value="sys_log")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog extends IBaseEntity {
    @ApiModelProperty(value="")
    private String id;

    /**
     * 业务名称
     */
    @ApiModelProperty(value="业务名称")
    private String name;

    /**
     * IP地址
     */
    @ApiModelProperty(value="IP地址")
    private String ip;

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

    /**
     * 请求参数JSON
     */
    @ApiModelProperty(value="请求参数JSON")
    private String request;

    /**
     * 响应结果JSON
     */
    @ApiModelProperty(value="响应结果JSON")
    private String response;

    /**
     * 是否成功，1-是，0-否
     */
    @ApiModelProperty(value="是否成功，1-是，0-否")
    private Boolean success;
}