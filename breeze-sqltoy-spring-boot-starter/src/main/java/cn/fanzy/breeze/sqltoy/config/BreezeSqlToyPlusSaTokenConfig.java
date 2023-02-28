package cn.fanzy.breeze.sqltoy.config;

import cn.dev33.satoken.stp.StpUtil;
import cn.fanzy.breeze.sqltoy.plus.session.BreezeCurrentSessionHandler;
import cn.fanzy.breeze.sqltoy.plus.session.BreezeSaTokenCurrentSessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@AutoConfigureBefore({BreezeSqlToyPlusConfig.class})
@ConditionalOnClass(StpUtil.class)
public class BreezeSqlToyPlusSaTokenConfig {
    @Bean
    @ConditionalOnMissingBean
    public BreezeCurrentSessionHandler breezeCurrentSessionHandler() {
        return new BreezeSaTokenCurrentSessionHandler();
    }
}
