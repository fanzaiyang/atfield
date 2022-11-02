package cn.fanzy.breeze.admin.module.system.attachments.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微风管理附件查询参数
 *
 * @author fanzaiyang
 * @date 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BreezeAdminAttachmentQueryArgs extends BaseArgs {
    @ApiModelProperty(value = "文件名称，模糊查询",position = 1)
    private String fileName;
    @ApiModelProperty(value = "存储桶名称",position = 2)
    private String bucketName;
    @ApiModelProperty(value = "上传开始时间，yyyy-MM-dd HH:mm:ss",position = 3)
    private String startTime;
    @ApiModelProperty(value = "上传结束时间，yyyy-MM-dd HH:mm:ss",position = 4)
    private String endTime;
}
