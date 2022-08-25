package org.sagacity.sqltoy.utils;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @project sagacity-sqltoy
 * @description 用来批量设置集合中对象的属性值(jdk8 流式操作之后该类意义已经不大)
 * @author zhongxuchen
 * @version v1.0,Date:2012-8-17
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BeanWrapper {

	/**
	 * 集合中对象属性名称
	 */
	private String[] names;

	/**
	 * 集合中对象属性对应的值
	 */
	private Object[] values;

	public static BeanWrapper create() {
		return new BeanWrapper();
	}

	/**
	 * @todo 构造函数传递需要设置的参数名称
	 * @param names
	 */
	public BeanWrapper names(String... names) {
		this.names = names;
		return this;
	}

	/**
	 * @todo 用于将集合中单个属性值提取出来以数组返回，一般用于sql in 查询提取条件
	 * @param dataSet
	 * @return
	 * @throws RuntimeException
	 */
	public Object[] mappingAry(List dataSet) throws RuntimeException {
		if (null == names || names.length != 1 || null == dataSet || dataSet.isEmpty()) {
			return null;
		}
		List<List> reflectValue = BeanUtil.reflectBeansToList(dataSet, names);
		List result = new ArrayList();
		for (List rowList : reflectValue) {
			result.add(rowList.get(0));
		}
		return result.toArray();
	}

	/**
	 * @todo 传递参数名称对应的值
	 * @param paramsValue
	 * @return
	 */
	public BeanWrapper values(Object... paramsValue) {
		this.values = paramsValue;
		return this;
	}

	/**
	 * @todo 批量修改集合中对象的属性值
	 * @param dataSet
	 * @return
	 * @throws RuntimeException
	 */
	public Collection mappingSet(Collection dataSet) throws RuntimeException {
		if (dataSet != null && !dataSet.isEmpty()) {
			BeanUtil.batchSetProperties(dataSet, names, values, false);
		}
		return dataSet;
	}

	/**
	 * @todo 根据类型按照values长度构造全新的对象集合
	 * @param type
	 * @return
	 * @throws RuntimeException
	 */
	public Collection wrap(Type type) throws RuntimeException {
		if (null == names || names.length != 1 || null == values || values.length < 1 || null == type) {
			return null;
		}
		List valuesSet = new ArrayList();
		for (int i = 0; i < values.length; i++) {
			List rowList = new ArrayList();
			rowList.add(values[i]);
			valuesSet.add(rowList);
		}
		return BeanUtil.reflectListToBean(null, valuesSet, new int[] { 0 }, names, (Class) type);
	}

	/**
	 * @todo 设置单个对象属性值
	 * @param serializable
	 * @return
	 * @throws RuntimeException
	 */
	public Serializable mapping(Serializable serializable) throws RuntimeException {
		try {
			Serializable bean = (serializable instanceof Type)
					? (Serializable) ((Class) serializable).getDeclaredConstructor().newInstance()
					: serializable;
			List voList = new ArrayList();
			voList.add(bean);
			BeanUtil.batchSetProperties(voList, names, values, false);
			return bean;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
