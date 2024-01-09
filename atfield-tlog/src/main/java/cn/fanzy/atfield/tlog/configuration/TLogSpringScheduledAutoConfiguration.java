package cn.fanzy.atfield.tlog.configuration;

import cn.fanzy.atfield.tlog.task.spring.SpringScheduledTaskAop;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Scheduled AOP自动装配类
 * @author Bryan.Zhang
 * @since 1.3.4
 */
@Configuration
public class TLogSpringScheduledAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(SpringScheduledTaskAop.class)
    public SpringScheduledTaskAop springScheduledTaskAop(){
        return new SpringScheduledTaskAop();
    }
}
