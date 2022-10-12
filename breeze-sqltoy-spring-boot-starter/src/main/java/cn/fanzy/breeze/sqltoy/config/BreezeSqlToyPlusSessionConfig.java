package cn.fanzy.breeze.sqltoy.config;

import cn.fanzy.breeze.sqltoy.plus.session.BreezeCurrentSessionHandler;
import cn.fanzy.breeze.sqltoy.plus.session.BreezeDefaultCurrentSessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AutoConfigureBefore({BreezeSqlToyPlusConfig.class})
@AutoConfigureAfter({BreezeSqlToyPlusSaTokenConfig.class})
@ImportAutoConfiguration(BreezeSqlToyPlusSaTokenConfig.class)
public class BreezeSqlToyPlusSessionConfig {
    @Bean
    @ConditionalOnMissingBean
    public BreezeCurrentSessionHandler breezeCurrentSessionHandler() {
        return new BreezeDefaultCurrentSessionHandler();
    }
}
