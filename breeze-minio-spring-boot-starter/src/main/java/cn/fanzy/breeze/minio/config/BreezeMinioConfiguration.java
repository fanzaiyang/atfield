package cn.fanzy.breeze.minio.config;

import cn.fanzy.breeze.minio.properties.BreezeMinIOProperties;
import cn.fanzy.breeze.minio.service.BreezeMinioService;
import cn.fanzy.breeze.minio.service.BreezeMultipartFileService;
import cn.fanzy.breeze.minio.service.impl.BreezeMinioServiceImpl;
import cn.fanzy.breeze.minio.service.impl.BreezeMultipartFileServiceImpl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 微风minio配置
 *
 * @author fanzaiyang
 * @since 2022-08-22
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeMinIOProperties.class})
public class BreezeMinioConfiguration {
    private final BreezeMinIOProperties properties;

    private static Set<String> keySet;

    public static Map<String, BreezeMinioService> serviceMap = new ConcurrentHashMap<>();

    /**
     * 实例
     *
     * @return {@link BreezeMinioService}
     */
    public static BreezeMinioService instance() {
        return instance(CollUtil.get(keySet, 0));
    }

    /**
     * 实例
     *
     * @param name 名字
     * @return {@link BreezeMinioService}
     */
    public static BreezeMinioService instance(String name) {
        if(StrUtil.isBlank(name)){
            return instance();
        }
        return serviceMap.get(name);
    }

    @Bean
    public BreezeMultipartFileService breezeMultipartFileService(JdbcTemplate jdbcTemplate){
        return new BreezeMultipartFileServiceImpl(jdbcTemplate,properties);
    }

    /**
     * 配置检查
     */
    @PostConstruct
    public void init() {
        if (properties.getServers() == null || CollUtil.isEmpty(properties.getServers())) {
            log.error("「微风组件」请在配置文件中添加breeze.minio开头的参数！");
        }
        keySet = properties.getServers().keySet();
        for (String key : keySet) {
            BreezeMinioService service = new BreezeMinioServiceImpl();
            service.setConfig(properties.getServers().get(key));
            serviceMap.put(key, service);
        }
        log.info("「微风组件」开启 <MinIO> 相关的配置。");
    }
}
