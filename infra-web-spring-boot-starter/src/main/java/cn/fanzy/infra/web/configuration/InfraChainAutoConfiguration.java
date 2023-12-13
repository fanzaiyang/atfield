package cn.fanzy.infra.web.configuration;

import cn.fanzy.infra.web.chain.ChainHandler;
import cn.fanzy.infra.web.chain.Handler;
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
public class InfraChainAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public ChainHandler chainHandler(List<Handler> handlerList) {
        return new ChainHandler(handlerList);
    }

}
