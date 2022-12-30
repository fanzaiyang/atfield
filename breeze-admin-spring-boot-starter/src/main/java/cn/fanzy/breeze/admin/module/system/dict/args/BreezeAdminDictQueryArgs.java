package cn.fanzy.breeze.admin.module.system.dict.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微风admin dict查询参数
 *
 * @author fanzaiyang
 * @date 2022-11-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BreezeAdminDictQueryArgs extends BaseArgs {
    @Schema(description = "显示禁用")
    private boolean showDisable;
    @Schema(description = "字典键名")
    private String keyName;
    @Schema(description = "备注")
    private String remarks;
}
