package cn.fanzy.atfield.sqltoy.property;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * SQL Toy 上下文任务池属性
 *
 * @author fanzaiyang
 * @date 2024/01/09
 * @since : JDK 1.8
 */
@Getter
@Setter
public class SqlToyContextTaskPoolProperties implements Serializable {

	/**
	 * 指定线程池名称，该属性指定后则以指定的线程池作为默认线程池
	 */
	private String targetPoolName = "none";

	/**
	 * 线程前缀
	 */
	private String threadNamePrefix = "sqltoyThreadPool";

	/**
	 * 线程池维护线程的最少数量,核心线程数
	 */
	private Integer corePoolSize = Runtime.getRuntime().availableProcessors() / 2 + 1;

	/**
	 * 线程池维护线程的最大数量
	 */
	private Integer maxPoolSize = Runtime.getRuntime().availableProcessors() * 3;

	/**
	 * 线程池所使用的缓冲队列
	 */
	private Integer queueCapacity = 200;

	/**
	 * 线程池维护线程所允许的空闲时间
	 */
	private Integer keepAliveSeconds = 60;
	/**
	 * 决定使用ThreadPool的shutdown()还是shutdownNow()方法来关闭，默认为false
	 */
	private Boolean waitForTasksToCompleteOnShutdown = Boolean.TRUE;
	/**
	 * 超时中断时间
	 */
	private Integer awaitTerminationSeconds = -1;
	/**
	 * 拒绝策略
	 */
	private RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
}
