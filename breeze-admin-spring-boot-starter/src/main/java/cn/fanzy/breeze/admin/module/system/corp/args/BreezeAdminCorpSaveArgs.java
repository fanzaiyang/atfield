package cn.fanzy.breeze.admin.module.system.corp.args;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BreezeAdminCorpSaveArgs {
    @ApiModelProperty(value = "", position = 1)
    private String id;
    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空！")
    @ApiModelProperty(value = "编码", position = 2)
    private String code;
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空！")
    @ApiModelProperty(value = "名称", position = 3)
    private String name;
    /**
     * 简称
     */
    @ApiModelProperty(value = "简称", position = 3)
    private String shortName;
    /**
     * 上级ID
     */
    @ApiModelProperty(value = "上级ID", position = 5)
    private String parentId;
    /**
     * 父节点编码
     */
    @ApiModelProperty(value = "父节点编码", position = 6)
    private String parentCode;
    /**
     * 类别:corp=公司，dept=部门，job=职务
     */
    @ApiModelProperty(value = "类别:corp=公司，dept=部门，job=职务", position = 7)
    private String orgType;
    @ApiModelProperty(value = "排序序号", position = 10)
    private Integer orderNumber;
    @ApiModelProperty(value = "备注", position = 12)
    private String remarks;

    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}
