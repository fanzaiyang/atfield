package cn.fanzy.breeze.web.exception.config;

import cn.fanzy.breeze.web.exception.controller.BreezeDefaultBasicErrorController;
import cn.fanzy.breeze.web.exception.properties.BreezeWebExceptionProperties;
import cn.fanzy.breeze.web.exception.view.BreezeStaticView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

/**
 * @author fanzaiyang
 */
@Slf4j
@Configuration
@AutoConfigureAfter(ErrorMvcAutoConfiguration.class)
@EnableConfigurationProperties({ServerProperties.class, BreezeWebExceptionProperties.class})
@ConditionalOnProperty(prefix = "breeze.web.exception", name = {"replace-basic-error"}, havingValue = "true", matchIfMissing = true)
public class BreezeBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static String SPECIAL_OVERRIDE_BEAN = "basicErrorController";


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (registry.containsBeanDefinition(SPECIAL_OVERRIDE_BEAN)) {
            registry.removeBeanDefinition(SPECIAL_OVERRIDE_BEAN);
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Bean
    @ConditionalOnMissingBean(BreezeDefaultBasicErrorController.class)
    public BreezeDefaultBasicErrorController breezeDefaultBasicErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties,
                                                                               ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        return new BreezeDefaultBasicErrorController(errorAttributes, serverProperties.getError(),
                errorViewResolvers.orderedStream().collect(Collectors.toList()));
    }

    @Bean(name = "breezeView")
    public View defaultErrorView() {
        return new BreezeStaticView();
    }


    @PostConstruct
    public void init() {
        log.info("「微风组件」开启 <重写BasicError> 相关的配置。");
    }
}
