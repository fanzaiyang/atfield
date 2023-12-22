package cn.fanzy.infra.auth.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serial;
import java.io.Serializable;

/**
 * auth 属性
 *
 * @author fanzaiyang
 * @date 2023/12/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "infra.auth")
public class AuthProperty implements Serializable {
    @Serial
    private static final long serialVersionUID = 3002186446023424455L;

    /**
     * 路线
     */
    private Route route = new Route();
    /**
     * 注解
     */
    private Annotations annotation = new Annotations();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Route implements Serializable {
        @Serial
        private static final long serialVersionUID = -2351210069926957462L;

        /**
         * 是否启用，默认：true
         */
        private Boolean enable;
        /**
         * 添加路径模式，默认：/**
         */
        private String[] addPathPatterns;

        /**
         * 排除路径模式
         */
        private String[] excludePathPatterns;

        public String[] getAddPathPatterns() {
            if (addPathPatterns == null || addPathPatterns.length == 0) {
                return new String[]{"/**"};
            }
            return addPathPatterns;
        }
    }

    /**
     * 注解鉴权
     *
     * @author fanzaiyang
     * @since 2022-08-23
     */
    @Data
    public static class Annotations {
        /**
         * 是否启用，默认：true
         */
        private Boolean enable;
        /**
         * 拦截路径，默认/**
         */
        private String[] addPathPatterns;
        /**
         * 忽略路径
         */
        private String[] excludePathPatterns;

        public String[] getAddPathPatterns() {
            if (addPathPatterns == null || addPathPatterns.length == 0) {
                return new String[]{"/**"};
            }
            return addPathPatterns;
        }
    }
}
