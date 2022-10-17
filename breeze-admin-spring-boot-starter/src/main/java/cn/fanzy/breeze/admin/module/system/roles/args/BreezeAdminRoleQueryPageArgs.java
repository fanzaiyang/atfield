package cn.fanzy.breeze.admin.module.system.roles.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeAdminRoleQueryPageArgs extends BaseArgs {

    private String code;

    private String name;
}
