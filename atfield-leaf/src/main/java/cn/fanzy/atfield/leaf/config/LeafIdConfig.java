package cn.fanzy.atfield.leaf.config;

import cn.fanzy.atfield.leaf.dao.IdGenDao;
import cn.fanzy.atfield.leaf.dao.IdGenDaoImpl;
import cn.fanzy.atfield.leaf.gen.RedisIdGenerator;
import cn.fanzy.atfield.leaf.gen.SegmentRedisIdGenerator;
import cn.fanzy.atfield.leaf.property.LeafIdProperty;
import cn.fanzy.atfield.leaf.service.SegmentService;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RedissonClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LeafIdProperty.class)
public class LeafIdConfig {

    private final SqlToyRepository sqlToyRepository;
    private final LeafIdProperty leafIdProperty;
    private final RedissonClient redissonClient;

    @Bean
    public IdGenDao idAllocDao() {
        return new IdGenDaoImpl(sqlToyRepository, leafIdProperty);
    }

    @Bean
    public RedisIdGenerator redisIdGenerator() {
        return new SegmentRedisIdGenerator(idAllocDao(), leafIdProperty, redissonClient);
    }

    @Bean
    public SegmentService segmentService() {
        return new SegmentService(redisIdGenerator());
    }

}
