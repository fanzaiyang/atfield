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
     * CN=baidu.com
     */
    private String subjectCN;


    /**
     * C=CN
     */
    private String subjectC;


    /**
     * O=Beijing Baidu Netcom Science Technology Co.\\, Ltd
     */
    private String subjectO;

    /**
     * beijing
     */
    private String subjectST;

    /**
     * beijing
     */
    private String subjectL;
}
