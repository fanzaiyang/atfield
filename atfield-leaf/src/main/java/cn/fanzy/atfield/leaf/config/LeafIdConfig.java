package cn.fanzy.atfield.leaf.config;

import cn.fanzy.atfield.leaf.core.IDGenerator;
import cn.fanzy.atfield.leaf.core.segment.SegmentIDGeneratorImpl;
import cn.fanzy.atfield.leaf.core.segment.dao.IDAllocDao;
import cn.fanzy.atfield.leaf.core.segment.dao.impl.IDAllocDaoImpl;
import cn.fanzy.atfield.leaf.property.LeafIdProperty;
import cn.fanzy.atfield.leaf.service.SegmentService;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(LeafIdProperty.class)
public class LeafIdConfig {

    private final SqlToyRepository sqlToyRepository;
    private final LeafIdProperty leafIdProperty;

    @Bean
    public IDAllocDao idAllocDao() {
        return new IDAllocDaoImpl(sqlToyRepository, leafIdProperty);
    }

    @Bean
    @Primary
    public IDGenerator idGenerator() {
        return new SegmentIDGeneratorImpl(idAllocDao());
    }

    @Bean
    public SegmentService segmentService() {
        return new SegmentService(idGenerator());
    }
}
