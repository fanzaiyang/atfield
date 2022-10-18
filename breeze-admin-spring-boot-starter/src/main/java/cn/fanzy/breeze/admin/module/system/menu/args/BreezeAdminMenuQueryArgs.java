package cn.fanzy.breeze.admin.module.system.menu.args;

import cn.fanzy.breeze.web.model.BaseArgs;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BreezeAdminMenuQueryArgs extends BaseArgs {
    private String code;
    private String title;
}
