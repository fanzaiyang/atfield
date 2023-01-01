package cn.fanzy.breeze.auth.properties;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * MinIO属性配置
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "breeze.auth")
public class BreezeAuthProperties implements Serializable {

    private static final long serialVersionUID = 111129130225136300L;
    /**
     * 路由鉴权
     */
    private Route route = new Route();

    /**
     * 注解鉴权
     */
    private Annotations annotation = new Annotations();

    private Safe safe = new Safe();


    @Data
    public static class Route {
        /**
         * 是否启用，默认：true
         */
        private Boolean enable;
        /**
         * 拦截路径，默认/**
         */
        private List<String> pathPatterns = CollUtil.newArrayList("/**");
        /**
         * 忽略路径
         */
        private List<String> excludePathPatterns = new ArrayList<>();
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
        private List<String> pathPatterns = CollUtil.newArrayList("/**");
        /**
         * 忽略路径
         */
        private List<String> excludePathPatterns = new ArrayList<>();
    }

    /**
     * 安全相关
     */
    @Data
    public static class Safe {
        /**
         * 保存登录次数的key前缀
         */
        private String loginFailedPrefix = "breeze_auth_safe:";

        /**
         * 登录失败有效期，默认24小时。
         */
        private int loginTimeoutSecond = 24 * 60 * 60;
        /**
         * 是否需要验证码，默认：false
         */
        private boolean needCode = false;

        /**
         * 登录失败x次，后需启用验证码
         */
        private int loginFailedShowCodeMaxNum = 3;


    }
}
