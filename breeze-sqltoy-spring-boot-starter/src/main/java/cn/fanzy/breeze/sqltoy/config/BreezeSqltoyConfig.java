package cn.fanzy.breeze.sqltoy.config;

import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BreezeSqltoyConfig {
    @Bean
    public SqlToyHelperDao sqlToyHelperDao(){
        return new SqlToyHelperDaoImpl();
    }
}
