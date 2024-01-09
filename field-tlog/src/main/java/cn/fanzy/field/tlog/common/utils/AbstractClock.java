package cn.fanzy.field.tlog.common.utils;

/**
 * 抽象时钟
 *
 * @author fanzaiyang
 * @date 2024/01/09
 */
public abstract class AbstractClock {

	/**
	 * 创建系统时钟.
	 *
	 * @return 系统时钟
	 */
	public static AbstractClock systemClock() {
		return new SystemClock();
	}

	/**
	 * 返回从纪元开始的毫秒数.
	 *
	 * @return 从纪元开始的毫秒数
	 */
	public abstract long millis();

	private static final class SystemClock extends AbstractClock {

		@Override
		public long millis() {
			return System.currentTimeMillis();
		}
	}
}
