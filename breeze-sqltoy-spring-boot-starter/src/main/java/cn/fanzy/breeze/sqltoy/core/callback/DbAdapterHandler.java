package cn.fanzy.breeze.sqltoy.core.callback;

import javax.sql.DataSource;

import cn.fanzy.breeze.sqltoy.core.config.model.SqlToyConfig;

/**
 * @project sagacity-sqltoy
 * @description 提供sql查询语句跨库适配反调函数
 * @author zhongxuchen
 * @version v1.0,Date:2022-8-13
 */
@FunctionalInterface
public interface DbAdapterHandler {
	public void query(SqlToyConfig sqlToyConfig, DataSource dataSource);
}
