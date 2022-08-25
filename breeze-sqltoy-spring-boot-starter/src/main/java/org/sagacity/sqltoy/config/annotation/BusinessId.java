/**
 * 
 */
package org.sagacity.sqltoy.config.annotation;

import org.sagacity.sqltoy.plugins.id.IdGenerator;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @project sagacity-sqltoy
 * @description 业务主键,可以充当主键(一般可能是多个字段联合充当)
 * @author zhongxuchen
 * @version v1.0,Date:2018年1月12日
 */
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface BusinessId {
	/**
	 * 识别区分符号
	 * 
	 * @return
	 */
	String signature() default "";

	/**
	 * 默认长度
	 * 
	 * @return
	 */
	int length() default -1;

	/**
	 * 流水长度
	 * 
	 * @return
	 */
	int sequenceSize() default -1;

	/**
	 * 关联字段
	 * 
	 * @return
	 */
	String[] relatedColumns() default {};

	/**
	 * 主键生成策略
	 * 
	 * @return
	 */
	Class<? extends IdGenerator> generator();

	/**
	 * 初始值
	 * 
	 * @return
	 */
	long start() default 1;
}
