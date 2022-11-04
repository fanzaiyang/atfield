package cn.fanzy.breeze.admin.module.system.attachments.args;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 微风管理附件批量参数
 *
 * @author fanzaiyang
 * @date 2022-11-04
 */
@Data
public class BreezeAdminAttachmentBatchArgs {
    @NotEmpty(message = "主键ID不能为空！")
    private List<String> idList;
}
