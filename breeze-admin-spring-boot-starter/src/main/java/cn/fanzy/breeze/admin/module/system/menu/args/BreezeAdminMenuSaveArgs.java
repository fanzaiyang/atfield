package cn.fanzy.breeze.admin.module.system.menu.args;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminMenuSaveArgs {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", position = 1)
    private String id;
    /**
     * 菜单标识
     */
    @NotBlank(message = "菜单编码不能为空！")
    @ApiModelProperty(value = "菜单标识", position = 2)
    private String code;
    /**
     * 菜单标题
     */
    @NotBlank(message = "菜单标题不能为空！")
    @ApiModelProperty(value = "菜单标题", position = 3)
    private String title;
    /**
     * 菜单图标
     */
    @ApiModelProperty(value = "菜单图标", position = 4)
    private String icon;
    /**
     * 上级菜单ID
     */
    @ApiModelProperty(value = "上级菜单ID", position = 5)
    private String parentId;
    /**
     * 菜单类型;menu-菜单，button-按钮
     */
    @NotBlank(message = "菜单类型不能为空！")
    @ApiModelProperty(value = "菜单类型;M目录 C菜单 F按钮", position = 6)
    private String menuType;
    /**
     * 是否隐藏;0-否，1-是
     */
    @ApiModelProperty(value = "是否隐藏;0-否，1-是", position = 7)
    private Integer hidden;
    /**
     * 菜单路径
     */
    @ApiModelProperty(value = "菜单路径", position = 8)
    private String uri;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", position = 9)
    private String remarks;

    @ApiModelProperty(value = "序号", position = 11)
    private Integer orderNumber;

    /**
     * 权限标识
     */
    @ApiModelProperty(value = "权限标识", position = 12)
    private String permissionCode;
    /**
     * 权限标识
     */
    @ApiModelProperty(value = "回调地址", position = 12)
    private String callbackUrl;
    @ApiModelProperty(value = "临时token登录地址", position = 13)
    private String authLoginTokenUri;
    @ApiModelProperty(value = "子系统用户验证地址", position = 14)
    private String authUserCheckUri;

    private Integer status;

    public Integer getHidden() {
        return hidden == null ? 0 : hidden;
    }

    public Integer getStatus() {
        return status == null ? 1 : status;
    }

    public Integer getOrderNumber() {
        return orderNumber == null ? 0 : orderNumber;
    }
}
