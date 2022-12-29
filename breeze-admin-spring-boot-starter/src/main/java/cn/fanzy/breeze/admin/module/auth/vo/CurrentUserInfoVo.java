package cn.fanzy.breeze.admin.module.auth.vo;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.admin.module.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserInfoVo extends SysAccount {

    @Schema(title = "角色集合")
    private List<SysRole> roleList;
}
