/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.config.model;

/**
 * @project sqltoy-orm
 * @description 通过枚举方式定义数据库主键实现的四种机制
 * @author zhongxuchen
 * @version v1.0,Date:2013-6-10
 * @modify Date:2013-6-10 {填写修改说明}
 */
public enum PKStrategy {
	// 手工赋值
	assign,

	// 数据库sequence
	sequence,

	// 数据库identity自增模式,oracle,db2中对应always identity
	identity,

	// 自定义类产生一个不唯一的主键
	generator;

}
