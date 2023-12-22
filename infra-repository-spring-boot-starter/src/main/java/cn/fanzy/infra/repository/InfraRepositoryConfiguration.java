package cn.fanzy.infra.repository;

import cn.fanzy.infra.repository.dao.BaseRepository;
import cn.fanzy.infra.repository.dao.BaseRepositoryImpl;
import cn.fanzy.infra.repository.sqltoy.configuration.SqltoyAutoConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Redis扩展支持自动配置
 *
 * @author fanzaiyang
 * @since 2021/09/07
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@ImportAutoConfiguration({SqltoyAutoConfiguration.class})
public class InfraRepositoryConfiguration {

    @Bean
    public BaseRepository baseRepository() {
        return new BaseRepositoryImpl();
    }
}
