package cn.fanzy.breeze.admin.module.system.corp.args;

import cn.fanzy.breeze.core.utils.BreezeConstants;
import cn.hutool.core.util.StrUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BreezeAdminCorpSaveArgs {
    @Schema(description = "")
    private String id;
    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空！")
    @Schema(description = "编码")
    private String code;
    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空！")
    @Schema(description = "名称")
    private String name;
    /**
     * 简称
     */
    @Schema(description = "简称")
    private String shortName;
    /**
     * 上级ID
     */
    @Schema(description = "上级ID")
    private String parentId;
    /**
     * 父节点编码
     */
    @Schema(description = "父节点编码")
    private String parentCode;
    /**
     * 类别:corp=公司，dept=部门，job=职务
     */
    @Schema(description = "类别:corp=公司，dept=部门，job=职务")
    private String orgType;
    @Schema(description = "排序序号")
    private Integer orderNumber;
    @Schema(description = "备注")
    private String remarks;

    public String getParentId() {
        return StrUtil.blankToDefault(parentId, BreezeConstants.TREE_ROOT_ID);
    }
}
