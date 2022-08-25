/**
 * 
 */
package org.sagacity.sqltoy.callback;

import java.util.List;

import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.model.ShardingGroupModel;

/**
 * @project sagacity-sqltoy
 * @description 并行执行反调定义
 * @author zhongxuchen
 * @version v1.0,Date:2017年11月3日
 */
@FunctionalInterface
public interface ParallelCallbackHandler {
	/**
	 * @todo 并行执行反调计算
	 * @param sqlToyContext
	 * @param shardingGroupModel 集合数据根据分组策略拆分成的单一组数据和对应table或数据源配置
	 * @return
	 * @throws Exception
	 */
	public List<?> execute(SqlToyContext sqlToyContext, ShardingGroupModel shardingGroupModel) throws Exception;
}
