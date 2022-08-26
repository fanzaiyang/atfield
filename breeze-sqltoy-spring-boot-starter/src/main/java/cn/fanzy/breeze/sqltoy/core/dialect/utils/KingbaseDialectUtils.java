package cn.fanzy.breeze.sqltoy.core.dialect.utils;

import cn.fanzy.breeze.sqltoy.core.config.model.PKStrategy;

/**
 * @project sagacity-sqltoy
 * @description 北大金仓数据库方言支持
 * @author zhongxuchen
 * @version v1.0, Date:2020-11-6
 * @modify 2020-11-6,修改说明
 */
public class KingbaseDialectUtils {
	/**
	 * 判定为null的函数
	 */
	public static final String NVL_FUNCTION = "isnull";

	public static boolean isAssignPKValue(PKStrategy pkStrategy) {
		if (pkStrategy == null) {
			return true;
		}
		if (pkStrategy.equals(PKStrategy.sequence)) {
			return true;
		}
		// 目前不支持identity模式
		if (pkStrategy.equals(PKStrategy.identity)) {
			return false;
		}
		return true;
	}
}
