package cn.fanzy.atfield.upload.configuration;

import cn.fanzy.atfield.core.exception.NonePropertyException;
import cn.fanzy.atfield.upload.controller.AtFieldUploadController;
import cn.fanzy.atfield.upload.property.UploadProperty;
import cn.fanzy.atfield.upload.service.UploadService;
import cn.fanzy.atfield.upload.service.impl.UploadServiceImpl;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
@AllArgsConstructor
@Configuration
@ImportAutoConfiguration({AtFieldUploadController.class, UploadPartConfiguration.class})
@EnableConfigurationProperties({UploadProperty.class})
public class UploadConfiguration {
    private final UploadProperty properties;

    private static Set<String> keySet;

    public static Map<String, UploadService> serviceMap = new ConcurrentHashMap<>();

    /**
     * 实例
     *
     * @return {@link UploadService}
     */
    public static UploadService instance() {
        if (CollUtil.isEmpty(keySet)) {
            throw new NonePropertyException("501", "请在配置文件中添加上传配置。【atfield.upload】");
        }
        return instance(CollUtil.get(keySet, 0));
    }

    /**
     * 实例
     *
     * @param name 名字
     * @return {@link UploadService}
     */
    public static UploadService instance(String name) {
        if (StrUtil.isBlank(name)) {
            return instance();
        }
        return serviceMap.get(name);
    }


    /**
     * 配置检查
     */
    @PostConstruct
    public void init() {
        if (properties.getServers() == null || CollUtil.isEmpty(properties.getServers())) {
            log.error("请在配置文件中添加atfield.upload开头的参数！");
        }
        keySet = properties.getServers().keySet();
        for (String key : keySet) {
            UploadService service = new UploadServiceImpl();
            service.setConfig(properties.getServers().get(key));
            serviceMap.put(key, service);
        }
        log.info("开启 <文件上传> 相关的配置。");
    }
}
