package cn.fanzy.breeze.admin.module.system.dict.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 微风admin dict批处理参数
 *
 * @author fanzaiyang
 * @version 2022-11-03
 */
@Data
public class BreezeAdminDictBatchArgs {
    @NotEmpty(message = "请求参数不能为空！")
    @Schema(description = "字典ID集合")
    private List<String> idList;
}
