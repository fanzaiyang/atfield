package cn.fanzy.atfield.web.configuration;

import cn.fanzy.atfield.web.json.property.JsonProperty;
import cn.fanzy.atfield.web.task.TaskMultiProperty;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 任务多线程配置
 *
 * @author fanzaiyang
 * @date 2024/10/10
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(TaskMultiProperty.class)
@ConditionalOnProperty(prefix = "atfield.task.multi-thread", name = {"enable"}, havingValue = "true")
public class TaskMultiThreadConfig {

    private final TaskMultiProperty property;

    @Primary
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        // 获取当前系统的CPU
        int cpuNum = Runtime.getRuntime().availableProcessors();
        // 核心池大小
        int corePoolSize = cpuNum * 2 + 1;
        // 线程池的最大线程数
        int maximumPoolSize = cpuNum * 5;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数，默认为1
        executor.setCorePoolSize(property.getCorePoolSize() == null ? corePoolSize : property.getCorePoolSize());
        // 最大线程数，默认为Integer.MAX_VALUE
        executor.setMaxPoolSize(property.getMaxPoolSize() == null ? maximumPoolSize : property.getMaxPoolSize());
        // 队列最大长度，一般需要设置值: 大于等于notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
        executor.setQueueCapacity(property.getQueueCapacity() == null ? 50 : property.getQueueCapacity());
        // 线程池维护线程所允许的空闲时间，默认为60s
        executor.setKeepAliveSeconds(property.getKeepAliveSeconds() == null ? 60 : property.getKeepAliveSeconds());
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        // AbortPolicy:直接抛出java.util.concurrent.RejectedExecutionException异常
        // CallerRunsPolicy:主线程直接执行该任务，执行完之后尝试添加下一个任务到线程池中，可以有效降低向线程池内添加任务的速度
        // DiscardOldestPolicy:抛弃旧的任务、暂不支持；会导致被丢弃的任务无法再次被执行
        // DiscardPolicy:抛弃当前任务、暂不支持；会导致被丢弃的任务无法再次被执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setThreadNamePrefix(StrUtil.blankToDefault(property.getThreadNamePrefix(),"task-thread-"));
        //执行初始化会自动执行afterPropertiesSet()初始化
        executor.initialize();
        return executor;
    }
}
