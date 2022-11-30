package cn.fanzy.breeze.admin.module.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEnvVo {
    private String clientIp;

    private String clientId;
    private String appName;
}
