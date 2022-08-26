/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.plugins.function.impl;

import java.util.regex.Pattern;

import cn.fanzy.breeze.sqltoy.core.plugins.function.IFunction;
import cn.fanzy.breeze.sqltoy.core.utils.DataSourceUtils.DBType;

/**
 * @project sqltoy-orm
 * @description 字符串去除两边的空白
 * @author zhongxuchen
 * @version v1.0,Date:2013-3-21
 */
public class Trim extends IFunction {
	private static Pattern regex = Pattern.compile("(?i)\\Wtrim\\(");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sagacity.sqltoy.config.function.IFunction#dialects()
	 */
	@Override
	public String dialects() {
		return ALL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sagacity.sqltoy.config.function.IFunction#regex()
	 */
	@Override
	public Pattern regex() {
		return regex;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sagacity.sqltoy.config.function.IFunction#wrap(int,
	 * java.lang.String[])
	 */
	@Override
	public String wrap(int dialect, String functionName, boolean hasArgs, String... args) {
		if (dialect == DBType.SQLSERVER) {
			return "rtrim(ltrim(" + args[0] + "))";
		}
		return super.IGNORE;
	}
}
