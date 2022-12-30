package cn.fanzy.breeze.admin.module.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientEnvVo {
    @Schema(description = "客户端IP")
    private String clientIp;
    @Schema(description = "客户端ID")
    private String clientId;
    @Schema(description = "应用名称")
    private String appName;
}
