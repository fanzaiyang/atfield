package cn.fanzy.breeze.admin.module.system.menu.args;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreezeAdminMenuEnableArgs {

    @NotEmpty(message = "菜单ID不能为空！")
    private List<String> idList;
}
