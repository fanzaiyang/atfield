package cn.fanzy.breeze.admin.module.system.roles.args;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 微风admin角色删除参数
 *
 * @author fanzaiyang
 * @date 2022-11-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminRoleDeleteArgs {
    /**
     * 主键
     */
    @Schema(description = "主键集合")
    private List<String> idList;
}
