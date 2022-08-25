/**
 * 
 */
package org.sagacity.sqltoy.link;

import org.sagacity.sqltoy.SqlToyContext;

import javax.sql.DataSource;

/**
 * @project sagacity-sqltoy
 * @description 提供基于elasticSearch的查询服务(利用sqltoy组织查询的语句机制的优势提供查询相关功能,增删改暂时不提供)
 * @author zhongxuchen
 * @version v1.0,Date:2018年1月1日
 */
public class Elastic extends BaseLink {
	public Elastic(SqlToyContext sqlToyContext, DataSource dataSource) {
		super(sqlToyContext, dataSource);
	}
}
