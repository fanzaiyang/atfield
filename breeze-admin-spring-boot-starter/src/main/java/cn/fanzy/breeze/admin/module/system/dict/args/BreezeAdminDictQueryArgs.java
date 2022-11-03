package cn.fanzy.breeze.admin.module.system.dict.args;

import cn.fanzy.breeze.web.model.BaseArgs;
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
    private boolean showDisable;
    private String keyName;
    private String remarks;
}
