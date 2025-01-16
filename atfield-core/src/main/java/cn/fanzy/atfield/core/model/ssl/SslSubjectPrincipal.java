package cn.fanzy.atfield.core.model.ssl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * SSL证书持有者信息
 *
 * @author fanzaiyang
 * @date 2025/01/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SslSubjectPrincipal implements Serializable {
    @Serial
    private static final long serialVersionUID = 597533553737874433L;

    /**
     * 主题 CN
     */
    private String subjectCN;


    private String subjectC;


    private String subjectO;


    private String subjectOU;
}
