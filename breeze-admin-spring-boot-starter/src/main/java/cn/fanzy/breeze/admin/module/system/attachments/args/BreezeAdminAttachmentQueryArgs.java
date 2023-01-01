package cn.fanzy.breeze.admin.module.system.attachments.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 微风管理附件查询参数
 *
 * @author fanzaiyang
 * @version 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BreezeAdminAttachmentQueryArgs extends BaseArgs {
    @Schema(description = "文件名称，模糊查询")
    private String fileName;
    @Schema(description = "存储桶名称")
    private String bucketName;
    @Schema(description = "上传开始时间，yyyy-MM-dd HH:mm:ss", hidden = true)
    private String startTime;
    @Schema(description = "上传结束时间，yyyy-MM-dd HH:mm:ss", hidden = true)
    private String endTime;
    @Schema(description = "开始和结束时间，yyyy-MM-dd HH:mm:ss")
    private List<String> rangeTime;

    public String getStartTime() {
        if (CollUtil.isEmpty(rangeTime)) {
            return null;
        }
        if (StrUtil.length(rangeTime.get(0)) == 10) {
            return rangeTime.get(0) + " 00:00:00";
        }
        return rangeTime.get(0);
    }

    public String getEndTime() {
        if (CollUtil.isEmpty(rangeTime) || rangeTime.size() < 2) {
            return null;
        }
        if (StrUtil.length(rangeTime.get(1)) == 10) {
            return rangeTime.get(1) + " 23:59:59";
        }
        return rangeTime.get(1);
    }
}
