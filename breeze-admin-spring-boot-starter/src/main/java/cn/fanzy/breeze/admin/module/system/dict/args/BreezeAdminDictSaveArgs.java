package cn.fanzy.breeze.admin.module.system.dict.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BreezeAdminDictSaveArgs {
    @Schema(description = "")
    private String id;
    /**
     * 键名
     */
    @NotBlank(message = "字典名称不能为空！")
    @Schema(description = "键名")
    private String keyName;
    /**
     * 键值
     */
    @NotBlank(message = "字典值不能为空！")
    @Schema(description = "键值")
    private String keyValue;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;
    /**
     * 上级ID
     */
    @Schema(description = "上级ID")
    private String parentId;
    @Schema(description = "序号")
    private Integer orderNumber;
}
