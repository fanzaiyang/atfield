package cn.fanzy.breeze.admin.module.system.dict.args;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BreezeAdminDictSaveArgs {
    @ApiModelProperty(value = "", position = 1)
    private String id;
    /**
     * 键名
     */
    @NotBlank(message = "字典名称不能为空！")
    @ApiModelProperty(value = "键名", position = 2)
    private String keyName;
    /**
     * 键值
     */
    @NotBlank(message = "字典值不能为空！")
    @ApiModelProperty(value = "键值", position = 3)
    private String keyValue;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 4)
    private String remarks;
    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID", position = 5)
    private String parentId;
    @ApiModelProperty(value = "序号", position = 11)
    private Integer orderNumber;
}
