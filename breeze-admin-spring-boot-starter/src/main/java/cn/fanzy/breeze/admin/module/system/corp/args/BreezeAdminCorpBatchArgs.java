package cn.fanzy.breeze.admin.module.system.corp.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class BreezeAdminCorpBatchArgs {
    @NotEmpty(message = "组织ID集合不能为空！")
    @Schema(description = "组织ID集合")
    private List<String> idList;
}
