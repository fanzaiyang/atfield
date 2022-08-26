/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.callback;

import cn.fanzy.breeze.sqltoy.core.config.model.EntityMeta;

/**
 * @project sagacity-sqltoy
 * @description 提供一个参数sql的反调函数，方便抽象统一的方言处理
 * @author zhongxuchen
 * @version v1.0,Date:2015年3月5日
 */
public abstract class GenerateSqlHandler {
	/**
	 * @todo 根据pojo的meta产生特定的诸如：insert、update等sql
	 * @param entityMeta
	 * @param forceUpdateFields 强制修改的字段
	 * @return
	 */
	public abstract String generateSql(EntityMeta entityMeta, String[] forceUpdateFields);
}
