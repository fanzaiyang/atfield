package cn.fanzy.breeze.auth.properties;

import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * MinIO属性配置
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
@Slf4j
@Data
@ConfigurationProperties(prefix = "breeze.auth")
public class BreezeAuthProperties {

    /**
     * 路由鉴权
     */
    private Route route=new Route();


    @Data
    public static class Route{
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
        private List<String> excludePathPatterns;
    }

}
