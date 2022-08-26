/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.dialect.executor;

import java.util.concurrent.Callable;

import javax.sql.DataSource;

import cn.fanzy.breeze.sqltoy.core.SqlToyContext;
import cn.fanzy.breeze.sqltoy.core.config.model.SqlToyConfig;
import cn.fanzy.breeze.sqltoy.core.dialect.DialectFactory;
import cn.fanzy.breeze.sqltoy.core.model.ParallQuery;
import cn.fanzy.breeze.sqltoy.core.model.ParallQueryResult;
import cn.fanzy.breeze.sqltoy.core.model.QueryExecutor;
import cn.fanzy.breeze.sqltoy.core.model.inner.ParallQueryExtend;
import cn.fanzy.breeze.sqltoy.core.plugins.CrossDbAdapter;

/**
 * @project sagacity-sqltoy
 * @description 并行查询执行器
 * @author zhongxuchen
 * @version v1.0, Date:2020-8-25
 * @modify 2020-8-25,修改说明
 */
public class ParallQueryExecutor implements Callable<ParallQueryResult> {

	/**
	 * sqltoy上下文
	 */
	private SqlToyContext sqlToyContext;

	private DialectFactory dialectFactory;

	private DataSource dataSource;
	private ParallQuery parallQuery;
	private SqlToyConfig sqlToyConfig;
	private String[] paramNames;

	private Object[] paramValues;

	public ParallQueryExecutor(SqlToyContext sqlToyContext, DialectFactory dialectFactory, SqlToyConfig sqlToyConfig,
			ParallQuery parallQuery, String[] paramNames, Object[] paramValues, DataSource dataSource) {
		this.sqlToyContext = sqlToyContext;
		this.dialectFactory = dialectFactory;
		this.sqlToyConfig = sqlToyConfig;
		this.parallQuery = parallQuery;
		this.dataSource = dataSource;
		this.paramNames = paramNames;
		this.paramValues = paramValues;
	}

	@Override
	public ParallQueryResult call() {
		ParallQueryResult result = new ParallQueryResult();
		try {
			ParallQueryExtend extend = parallQuery.getExtend();
			QueryExecutor queryExecutor = new QueryExecutor(extend.sql).resultType(extend.resultType).names(paramNames)
					.values(paramValues);
			// 分页
			if (extend.page != null) {
				// 不取总记录数分页模式
				if (extend.page.getSkipQueryCount() != null && extend.page.getSkipQueryCount()) {
					result.setResult(dialectFactory.findSkipTotalCountPage(sqlToyContext, queryExecutor, sqlToyConfig,
							extend.page.getPageNo(), extend.page.getPageSize(), dataSource));
				} else {
					result.setResult(
							dialectFactory.findPage(sqlToyContext, queryExecutor, sqlToyConfig, extend.page.getPageNo(),
									extend.page.getPageSize(), extend.page.isOverPageToFirst(), dataSource));
				}
				// 产品化场景，适配其他数据库验证查询(仅仅在设置了redoDataSources时生效)
				CrossDbAdapter.redoPageQuery(sqlToyContext, dialectFactory, queryExecutor, extend.page);
			} else {
				result.setResult(
						dialectFactory.findByQuery(sqlToyContext, queryExecutor, sqlToyConfig, null, dataSource));
				// 产品化场景，适配其他数据库验证查询(仅仅在设置了redoDataSources时生效)
				CrossDbAdapter.redoQuery(sqlToyContext, dialectFactory, queryExecutor);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
