/**
 *
 */
package cn.fanzy.atfield.web.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;


/**
 * 跨域支持属性配置
 *
 * @author fanzaiyang
 * @since 2021/09/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "atfield.web.attach")
public class CustomWebProperties implements Serializable {
    @Serial
    private static final long serialVersionUID = -2465252437257164820L;
    /**
     * 附件上传路径，默认：/attach
     */
    private String contextPath = "/attach";

    /**
     * 资源位置;外部文件夹实例：file:/Users/fanzy/Documents/attach
     */
    private String[] resourceLocations;

    /**
     * 公共读取;默认：false
     */
    private Boolean publicRead = false;
}
