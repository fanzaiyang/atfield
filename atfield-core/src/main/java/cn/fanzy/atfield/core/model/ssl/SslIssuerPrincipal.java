package cn.fanzy.atfield.core.model.ssl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * SSL 颁发者信息
 *
 * @author fanzaiyang
 * @date 2025/01/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SslIssuerPrincipal implements Serializable {
    @Serial
    private static final long serialVersionUID = 5528504947667957254L;

    /**
     * 供应名;CN=
     */
    private String issuerCN;
    /**
     * 组织名;O=
     */
    private String issuerO;
    /**
     * 组织单元;OU=
     */
    private String issuerOU;

    /**
     * 发行商
     */
    private String issuerC;

}
