package cn.fanzy.breeze.admin.module.system.menu.args;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "主键")
    private String id;
    /**
     * 菜单标识
     */
    @NotBlank(message = "菜单编码不能为空！")
    @Schema(description = "菜单标识")
    private String code;
    /**
     * 菜单标题
     */
    @NotBlank(message = "菜单标题不能为空！")
    @Schema(description = "菜单标题")
    private String title;
    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;
    /**
     * 上级菜单ID
     */
    @Schema(description = "上级菜单ID")
    private String parentId;
    /**
     * 菜单类型;menu-菜单，button-按钮
     */
    @NotBlank(message = "菜单类型不能为空！")
    @Schema(description = "菜单类型;M目录 C菜单 F按钮")
    private String menuType;
    /**
     * 是否隐藏;0-否，1-是
     */
    @Schema(description = "是否隐藏;0-否，1-是")
    private Integer hidden;
    /**
     * 菜单路径
     */
    @Schema(description = "菜单路径")
    private String uri;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "序号")
    private Integer orderNumber;

    /**
     * 权限标识
     */
    @Schema(description = "权限标识")
    private String permissionCode;
    /**
     * 权限标识
     */
    @Schema(description = "回调地址")
    private String callbackUrl;
    @Schema(description = "临时token登录地址")
    private String authLoginTokenUri;
    @Schema(description = "子系统用户验证地址")
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
