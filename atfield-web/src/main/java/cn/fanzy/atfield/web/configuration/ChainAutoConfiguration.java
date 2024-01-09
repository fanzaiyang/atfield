package cn.fanzy.atfield.web.configuration;

import cn.fanzy.atfield.web.chain.ChainHandler;
import cn.fanzy.atfield.web.chain.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 链下自动配置
 *
 * @author fanzaiyang
 * @date 2023/12/13
 */
@RequiredArgsConstructor
@Configuration
public class ChainAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ChainHandler chainHandler(List<Handler> handlerList) {
        return new ChainHandler(handlerList);
    }

}
