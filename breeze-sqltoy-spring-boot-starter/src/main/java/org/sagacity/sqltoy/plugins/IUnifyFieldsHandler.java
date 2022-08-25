package org.sagacity.sqltoy.plugins;

import java.util.Map;

import org.sagacity.sqltoy.model.DataAuthFilterConfig;
import org.sagacity.sqltoy.model.IgnoreCaseSet;
import org.sagacity.sqltoy.model.IgnoreKeyCaseMap;

/**
 * @project sagacity-sqltoy
 * @description 统一字段赋值处理、统一数据权限传值和越权控制
 * @author zhongxuchen
 * @version v1.0,Date:2018年1月17日
 * @modify {Date:2019-09-15,增加了forceUpdateFields方法}
 * @modify {Date:2021-10-11,增加了dataAuthFilters做统一参数传递和统一数据权限防越权过滤}
 */
public interface IUnifyFieldsHandler {
	/**
	 * @TODO 设置创建记录时需要赋值的字段和对应的值(弹性模式:即优先以传递的值优先，为null再填充)
	 * @return
	 */
	public default Map<String, Object> createUnifyFields() {
		return null;
	}

	/**
	 * @TODO 设置修改记录时需要赋值的字段和对应的值(弹性)
	 * @return
	 */
	public default Map<String, Object> updateUnifyFields() {
		return null;
	}

	// 在非强制情况下，create和update赋值都是先判断字段是否已经赋值，如已经赋值则忽视
	// 强制赋值后，则忽视字段赋值，强制覆盖
	/**
	 * @TODO 強制修改的字段(一般针对update属性)
	 * @return
	 */
	public default IgnoreCaseSet forceUpdateFields() {
		return null;
	}

	/**
	 * <p>
	 * 可设置如授权租户id:
	 * authTenantId-->S0001,sql语句中tenant_id=:authTenantId,框架则会自动将此值传递给sql
	 * </p>
	 * 
	 * @TODO 数据权限过滤，你可以灵活结合AOP、ThreadLocal技术实现不同场景注入不同的数据权限值
	 * @return
	 */
	public default IgnoreKeyCaseMap<String, DataAuthFilterConfig> dataAuthFilters() {
		return null;
	}
}
