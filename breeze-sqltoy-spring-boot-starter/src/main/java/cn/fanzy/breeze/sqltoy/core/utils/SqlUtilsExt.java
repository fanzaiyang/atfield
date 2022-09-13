/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import cn.fanzy.breeze.sqltoy.core.SqlToyConstants;
import cn.fanzy.breeze.sqltoy.core.config.model.EntityMeta;
import cn.fanzy.breeze.sqltoy.core.config.model.SqlToyConfig;
import cn.fanzy.breeze.sqltoy.core.plugins.TypeHandler;
import cn.fanzy.breeze.sqltoy.core.utils.DataSourceUtils.DBType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @project sqltoy-orm
 * @description 提供针对SqlUtil类的扩展,提供更有针对性的操作,提升性能
 * @author zhongxuchen
 * @version v1.0,Date:2015年4月22日
 */
public class SqlUtilsExt {
	/**
	 * 定义日志
	 */
	private final static Logger logger = LoggerFactory.getLogger(SqlUtilsExt.class);

	private SqlUtilsExt() {
	}

	/**
	 * @todo 仅提供对象形式的批量保存、修改、删除相关的最终sql执行
	 * @param typeHandler
	 * @param updateSql
	 * @param rowDatas
	 * @param fieldsType
	 * @param fieldsDefaultValue
	 * @param batchSize
	 * @param autoCommit
	 * @param conn
	 * @param dbType
	 * @return
	 * @throws Exception
	 */
	public static Long batchUpdateForPOJO(TypeHandler typeHandler, final String updateSql,
			final List<Object[]> rowDatas, final Integer[] fieldsType, final String[] fieldsDefaultValue,
			final int batchSize, final Boolean autoCommit, final Connection conn, final Integer dbType)
			throws Exception {
		if (rowDatas == null || rowDatas.isEmpty()) {
			logger.warn("batchUpdateForPOJO批量插入或修改数据操作数据为空!");
			return 0L;
		}
		long updateCount = 0;
		PreparedStatement pst = null;
		// 判断是否通过default转换方式插入
		boolean hasDefaultValue = (fieldsDefaultValue != null && fieldsType != null) ? true : false;
		try {
			boolean hasSetAutoCommit = false;
			// 是否自动提交
			if (autoCommit != null && autoCommit.booleanValue() != conn.getAutoCommit()) {
				conn.setAutoCommit(autoCommit.booleanValue());
				hasSetAutoCommit = true;
			}
			pst = conn.prepareStatement(updateSql);
			int totalRows = rowDatas.size();
			// 只有一条记录不采用批量
			boolean useBatch = (totalRows > 1) ? true : false;
			Object[] rowData;
			// 批处理计数器
			int meter = 0;
			Object cellValue;
			int fieldType;
			boolean hasFieldType = (fieldsType != null);
			boolean notSqlServer = (dbType == null || dbType.intValue() != DBType.SQLSERVER);
			int[] updateRows;
			int index = 0;
			for (int i = 0; i < totalRows; i++) {
				rowData = rowDatas.get(i);
				if (rowData != null) {
					// 使用对象properties方式传值
					index = 0;
					for (int j = 0, n = rowData.length; j < n; j++) {
						fieldType = hasFieldType ? fieldsType[j] : -1;
						// sqlserver timestamp 类型不支持赋值和更新
						if (notSqlServer || fieldType != java.sql.Types.TIMESTAMP) {
							if (hasDefaultValue) {
								cellValue = getDefaultValue(rowData[j], fieldsDefaultValue[j], fieldType);
							} else {
								cellValue = rowData[j];
							}
							SqlUtil.setParamValue(typeHandler, conn, dbType, pst, cellValue, fieldType, index + 1);
							index++;
						}
					}
					meter++;
					// 批量
					if (useBatch) {
						pst.addBatch();
						// 判断是否是最后一条记录或到达批次量,执行批处理
						if ((meter % batchSize) == 0 || i + 1 == totalRows) {
							updateRows = pst.executeBatch();
							for (int t : updateRows) {
								updateCount = updateCount + ((t > 0) ? t : 0);
							}
							pst.clearBatch();
						}
					} else {
						updateCount = pst.executeUpdate();
					}
				}
			}
			// 恢复conn原始autoCommit默认值
			if (hasSetAutoCommit) {
				conn.setAutoCommit(!autoCommit);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		} finally {
			try {
				if (pst != null) {
					pst.close();
					pst = null;
				}
			} catch (SQLException se) {
				logger.error(se.getMessage(), se);
			}
		}
		return updateCount;
	}

	/**
	 * @TODO 获得全部字段的默认值
	 * @param entityMeta
	 * @return
	 */
	public static Object[] getDefaultValues(EntityMeta entityMeta) {
		if (null == entityMeta || null == entityMeta.getFieldsDefaultValue()) {
			return null;
		}
		int size = entityMeta.getFieldsDefaultValue().length;
		Object[] result = new Object[size];
		String defaultValue;
		int fieldType;
		for (int i = 0; i < size; i++) {
			defaultValue = entityMeta.getFieldsDefaultValue()[i];
			if (null != defaultValue) {
				fieldType = entityMeta.getFieldsTypeArray()[i];
				result[i] = getDefaultValue(null, defaultValue, fieldType);
			}
		}
		return result;
	}

	/**
	 * @TODO 针对默认值进行处理
	 * @param paramValue
	 * @param defaultValue
	 * @param jdbcType
	 * @return
	 */
	public static Object getDefaultValue(Object paramValue, String defaultValue, int jdbcType) {
		Object realValue = paramValue;
		// 当前值为null且默认值不为null、且字段不允许为null
		if (realValue == null && defaultValue != null) {
			if (jdbcType == java.sql.Types.DATE) {
				if (isCurrentTime(defaultValue)) {
					realValue = new Date();
				} else {
					realValue = DateUtil.convertDateObject(defaultValue);
				}
			} else if (jdbcType == java.sql.Types.TIMESTAMP) {
				if (isCurrentTime(defaultValue)) {
					realValue = DateUtil.getTimestamp(null);
				} else {
					realValue = DateUtil.getTimestamp(defaultValue);
				}
			} else if (jdbcType == java.sql.Types.TIME) {
				if (isCurrentTime(defaultValue)) {
					realValue = LocalTime.now();
				} else {
					realValue = DateUtil.asLocalTime(DateUtil.convertDateObject(defaultValue));
				}
			} else if (jdbcType == java.sql.Types.BIGINT) {
				realValue = new BigInteger(defaultValue);
			} else if (jdbcType == java.sql.Types.INTEGER || jdbcType == java.sql.Types.TINYINT
					|| jdbcType == java.sql.Types.SMALLINT) {
				realValue = Integer.valueOf(defaultValue);
			} else if (jdbcType == java.sql.Types.DECIMAL || jdbcType == java.sql.Types.NUMERIC) {
				realValue = new BigDecimal(defaultValue);
			} else if (jdbcType == java.sql.Types.DOUBLE) {
				realValue = Double.valueOf(defaultValue);
			} else if (jdbcType == java.sql.Types.BOOLEAN) {
				realValue = Boolean.parseBoolean(defaultValue);
			} else if (jdbcType == java.sql.Types.FLOAT || jdbcType == java.sql.Types.REAL) {
				realValue = Float.valueOf(defaultValue);
			} else if (jdbcType == java.sql.Types.BIT) {
				if ("true".equalsIgnoreCase(defaultValue) || "false".equalsIgnoreCase(defaultValue)) {
					realValue = Boolean.parseBoolean(defaultValue.toLowerCase());
				} else {
					realValue = Integer.parseInt(defaultValue);
				}
			} else {
				realValue = defaultValue;
			}
		}
		return realValue;
	}

	// 判断默认值是否系统时间或日期
	private static boolean isCurrentTime(String defaultValue) {
		String defaultLow = defaultValue.toLowerCase();
		if (defaultLow.contains("sysdate") || defaultLow.contains("now") || defaultLow.contains("current")
				|| defaultLow.contains("sysdatetime") || defaultLow.contains("systime")
				|| defaultLow.contains("timestamp")) {
			return true;
		}
		return false;
	}

	/**
	 * @TODO 对sql增加签名,便于通过db来追溯sql(目前通过将sql id以注释形式放入sql)
	 * @param sql
	 * @param dbType       传递过来具体数据库类型,便于对不支持的数据库做区别处理
	 * @param sqlToyConfig
	 * @return
	 */
	public static String signSql(String sql, Integer dbType, SqlToyConfig sqlToyConfig) {
		// 判断是否打开sql签名,提供开发者通过SqlToyContext
		// dialectProperties设置:sqltoy.open.sqlsign=false 来关闭
		// elasticsearch类型 不支持
		if (!SqlToyConstants.openSqlSign() || dbType.equals(DBType.ES)) {
			return sql;
		}
		// 目前几乎所有数据库都支持/* xxx */ 形式的注释
		if (sqlToyConfig != null && StringUtil.isNotBlank(sqlToyConfig.getId())) {
			return "/* id=".concat(sqlToyConfig.getId()).concat(" */ ").concat(sql);
		}
		return sql;
	}
}