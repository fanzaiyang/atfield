package cn.fanzy.breeze.admin.module.system.account.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微风管理员账户查询参数
 *
 * @author fanzaiyang
 * @version 2022-11-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminAccountQueryArgs extends BaseArgs {
    @Schema(description = "姓名或手机号")
    private String searchWord;
    @Schema(description = "组织编码")
    private String orgCode;
    @Schema(description = "组织类型")
    private String orgType;
    @Schema(description = "状态。0-禁用，1-启用")
    private Integer status;
}
