package cn.fanzy.breeze.web.exception.config;

import cn.fanzy.breeze.web.exception.controller.BreezeDefaultBasicErrorController;
import cn.fanzy.breeze.web.exception.properties.BreezeWebExceptionProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Slf4j
@Configuration
@EnableConfigurationProperties({ServerProperties.class, BreezeWebExceptionProperties.class})
public class BreezeBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static String SPECIAL_OVERRIDE_BEAN = "basicErrorController";


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if(registry.containsBeanDefinition(SPECIAL_OVERRIDE_BEAN)){
            log.info("移除bean：{}",SPECIAL_OVERRIDE_BEAN);
            registry.removeBeanDefinition(SPECIAL_OVERRIDE_BEAN);
        }

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
    @Bean
    public BreezeDefaultBasicErrorController breezeDefaultBasicErrorController(ErrorAttributes errorAttributes,ServerProperties serverProperties,
                                                                               ObjectProvider<ErrorViewResolver> errorViewResolvers) {
        return new BreezeDefaultBasicErrorController(errorAttributes, serverProperties.getError(),
                errorViewResolvers.orderedStream().collect(Collectors.toList()));
    }
}
