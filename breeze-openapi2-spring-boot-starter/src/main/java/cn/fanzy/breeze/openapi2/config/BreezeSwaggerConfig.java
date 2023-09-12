package cn.fanzy.breeze.openapi2.config;

import cn.fanzy.breeze.openapi2.properties.BreezeSwaggerProperties;
import com.github.xiaoymin.knife4j.core.enums.ApiRuleEnums;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jInfoProperties;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.github.xiaoymin.knife4j.spring.model.docket.Knife4jDocketInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BreezeSwaggerProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.swagger", name = {"packages-to-scan"})
public class BreezeSwaggerConfig implements WebMvcConfigurer {
    private final BreezeSwaggerProperties properties;


    @Bean
    public Knife4jProperties knife4jProperties(Knife4jProperties knife4jProperties) {
        knife4jProperties.setEnable(true);
        Knife4jInfoProperties openapi = knife4jProperties.getOpenapi();
        if (openapi == null) {
            openapi = new Knife4jInfoProperties();
        }
        Map<String, Knife4jDocketInfo> group = openapi.getGroup();
        if (group == null && !properties.getPackagesToScan().isEmpty()) {
            group = new HashMap<>(1);
            Knife4jDocketInfo docketInfo = new Knife4jDocketInfo();
            docketInfo.setGroupName("默认分组");
            docketInfo.setApiRule(ApiRuleEnums.PACKAGE);
            docketInfo.setApiRuleResources(properties.getPackagesToScan());
            group.put("default", docketInfo);
            openapi.setGroup(group);
        }
        knife4jProperties.setOpenapi(openapi);
        return knife4jProperties;
    }
    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Swagger2> 相关的配置。");
    }
}
