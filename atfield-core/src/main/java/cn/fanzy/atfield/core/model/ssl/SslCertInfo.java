package cn.fanzy.atfield.core.model.ssl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * SSL 证书信息
 *
 * @author fanzaiyang
 * @date 2025/01/16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SslCertInfo implements Serializable {
    @Serial
    private static final long serialVersionUID = -7403318798521080266L;

    /**
     * 证书签名算法名称
     */
    private String sigAlgName;

    /**
     * 证书签名算法标识符
     */
    private String sigAlgOID;

    /**
     * 证书的版本号;版本号反映了证书中包含的信息丰富程度及支持的扩展特性。
     */
    private Integer version;

    /**
     * 证书颁发日期
     */
    private Date notBefore;

    /**
     * 证书过期日期
     */
    private Date notAfter;

    /**
     * 证书持有者；CN=*.fanzy.cn
     */
    private String subjectX500DN;

    /**
     * 证书颁发者信息；CN=WR1,O=Google Trust Services,C=US
     */
    private String issuerX500DN;

    /**
     * 证书颁发者信息
     */
    private SslIssuerPrincipal issuerPrincipal = new SslIssuerPrincipal();

    /**
     * 证书持有者信息
     */
    private SslSubjectPrincipal subjectPrincipal = new SslSubjectPrincipal();
}
