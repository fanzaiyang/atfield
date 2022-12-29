package cn.fanzy.breeze.admin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@Configuration
public class SwaggerConfig {
    protected final SpringDocConfigProperties springDocConfigProperties;
    @Bean
    public GroupedOpenApi breezeAdminApi(SwaggerUiConfigParameters swaggerUiConfigParameters){
//        swaggerUiConfigParameters.addGroup("微风组件");
        return GroupedOpenApi.builder()
                .group("微风组件")
                .pathsToMatch("/**")
                .packagesToScan("cn.fanzy.breeze.admin")
                .build();
    }
//    @Bean
//    @Lazy(false)
//    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters) {
//        List<GroupedOpenApi> groups = new ArrayList<>();
//        if (CollUtil.isNotEmpty(springDocConfigProperties.getGroupConfigs())) {
//            springDocConfigProperties.getGroupConfigs().forEach(groupConfig -> {
//                groups.add(GroupedOpenApi.builder()
//                        .group(groupConfig.getGroup()).displayName(groupConfig.getDisplayName())
//                        .pathsToMatch(CollUtil.isNotEmpty(groupConfig.getPathsToMatch()) ? groupConfig.getPathsToMatch().toArray(new String[0]) : new String[]{})
//                        .pathsToExclude(CollUtil.isNotEmpty(groupConfig.getPathsToExclude()) ? groupConfig.getPathsToExclude().toArray(new String[0]) : new String[]{})
//                        .packagesToScan(CollUtil.isNotEmpty(groupConfig.getPackagesToScan()) ? groupConfig.getPackagesToScan().toArray(new String[0]) : new String[]{})
//                        .packagesToExclude(CollUtil.isNotEmpty(groupConfig.getPackagesToExclude()) ? groupConfig.getPackagesToExclude().toArray(new String[0]) : new String[]{})
//                        .build());
//            });
//        }
//        swaggerUiConfigParameters.addGroup("公共组件");
//        groups.add(GroupedOpenApi.builder()
//                .group("公共组件")
//                .pathsToMatch("/**")
//                .packagesToScan("cn.fanzy.breeze.admin")
//                .build());
//        return groups;
//    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("XXX用户系统API")
                        .version("1.0")
                        .description("Knife4j集成springdoc-openapi示例")
                        .termsOfService("http://doc.xiaominfo.com")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }

    @PostConstruct
    public void checkConfig() {
        log.info("「微风组件」开启 <Swagger3> 相关的配置。");
    }
}
