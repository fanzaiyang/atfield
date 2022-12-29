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
 * @date 2022-11-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminAccountQueryArgs extends BaseArgs {
    @Schema(description = "姓名或手机号")
    private String searchWord;
    private String orgCode;
    private String orgType;
    private Integer status;
}
