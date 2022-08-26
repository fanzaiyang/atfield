/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.dialect.executor;

import java.util.concurrent.Callable;

import cn.fanzy.breeze.sqltoy.core.SqlExecuteStat;
import cn.fanzy.breeze.sqltoy.core.SqlToyContext;
import cn.fanzy.breeze.sqltoy.core.callback.ParallelCallbackHandler;
import cn.fanzy.breeze.sqltoy.core.config.model.ShardingGroupModel;
import cn.fanzy.breeze.sqltoy.core.model.ShardingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project sagacity-sqltoy
 * @description 数据库方言并行执行器
 * @author zhongxuchen
 * @version v1.0,Date:2017年11月3日
 */
public class DialectExecutor implements Callable<ShardingResult> {
	/**
	 * 定义日志
	 */
	protected final Logger logger = LoggerFactory.getLogger(DialectExecutor.class);

	/**
	 * sqltoy上下文
	 */
	private SqlToyContext sqltoyContext = null;

	/**
	 * sharding分组模型
	 */
	private ShardingGroupModel shardingGroupModel;

	/**
	 * 并行反调处理器
	 */
	private ParallelCallbackHandler handler;

	public DialectExecutor(SqlToyContext sqltoyContext, ShardingGroupModel shardingGroupModel,
			ParallelCallbackHandler handler) {
		this.sqltoyContext = sqltoyContext;
		this.shardingGroupModel = shardingGroupModel;
		this.handler = handler;
	}

	/**
	 * @todo 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
	 * @return
	 */
	@Override
	public ShardingResult call() {
		String dataSourceName = shardingGroupModel.getShardingModel().getDataSourceName();
		String tableName = shardingGroupModel.getShardingModel().getTableName();
		ShardingResult result = new ShardingResult();
		// 异常捕获掉,确保其他线程可以正常执行
		try {
			logger.debug("执行分库分表,DataSource节点:{},table={}", dataSourceName, tableName);
			result.setRows(handler.execute(sqltoyContext, shardingGroupModel));
		} catch (Exception e) {
			SqlExecuteStat.error(e);
			e.printStackTrace();
			result.setSuccess(false);
			result.setMessage(
					"执行分库分表,DataSource节点:" + dataSourceName + ",table=" + tableName + " 发生异常:" + e.getMessage());
			logger.error("执行分库分表,DataSource节点:{},table={} 发生异常:{}", dataSourceName, tableName, e.getMessage());
		} finally {
			SqlExecuteStat.destroy();
		}
		return result;
	}
}
