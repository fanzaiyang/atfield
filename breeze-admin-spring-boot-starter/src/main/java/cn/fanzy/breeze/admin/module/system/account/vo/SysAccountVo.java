package cn.fanzy.breeze.admin.module.system.account.vo;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import io.vavr.collection.Tree;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysAccountVo extends SysAccount {
    @Schema(description = "角色集合")
    private List<SysRole> roleList;
    @Schema(description = "权限集合")
    private List<Tree<String>> menuTreeList;
}
