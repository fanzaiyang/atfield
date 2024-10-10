package cn.fanzy.atfield.web.task;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Spring Task Scheduled 实现多线程并发定时任务
 *
 * @author fanzaiyang
 * @date 2024/10/10
 */
@Data
@ConfigurationProperties(prefix = "atfield.task.multi-thread")
public class TaskMultiProperty {

    /**
     * 是否启用，默认：true
     */
    private Boolean enable;

    /**
     * 核心池大小,默认：CPU个数*2+1
     */
    private Integer corePoolSize;

    /**
     * 最大池大小,默认：CPU个数*5
     */
    private Integer maxPoolSize;

    /**
     * 队列最大长度
     * <pre>
     *     队列最大长度，一般需要设置值: 大于等于notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
     * </pre>
     */
    private Integer queueCapacity=50;

    /**
     * 保持活动秒数,默认：60s
     */
    private Integer keepAliveSeconds=60;

    /**
     * 线程名称前缀,默认：task-thread-
     */
    private String threadNamePrefix="task-thread-";
}
