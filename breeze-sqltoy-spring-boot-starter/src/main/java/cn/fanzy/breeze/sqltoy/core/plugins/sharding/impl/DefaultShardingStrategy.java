/**
 * 
 */
package cn.fanzy.breeze.sqltoy.core.plugins.sharding.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import cn.fanzy.breeze.sqltoy.core.SqlToyContext;
import cn.fanzy.breeze.sqltoy.core.config.model.ShardingDBModel;
import cn.fanzy.breeze.sqltoy.core.integration.impl.SpringAppContext;
import cn.fanzy.breeze.sqltoy.core.integration.impl.SpringConnectionFactory;
import cn.fanzy.breeze.sqltoy.core.model.IgnoreCaseLinkedMap;
import cn.fanzy.breeze.sqltoy.core.plugins.sharding.IdleConnectionMonitor;
import cn.fanzy.breeze.sqltoy.core.plugins.sharding.ShardingStrategy;
import cn.fanzy.breeze.sqltoy.core.utils.DateUtil;
import cn.fanzy.breeze.sqltoy.core.utils.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @project sagacity-sqltoy
 * @description 提供默认的数据库sharding策略
 * @author zhongxuchen
 * @version v1.0,Date: 2017年1月3日
 */
public class DefaultShardingStrategy implements ShardingStrategy, ApplicationContextAware {
	private final static Logger logger = LoggerFactory.getLogger(DefaultShardingStrategy.class);

	private Map<String, String> tableNamesMap = new HashMap<String, String>();

	/**
	 * 默认为180天，180天前查询历史表
	 */
	private Integer[] days = { 180 };

	// 需要检查的日期条件参数名称,比较难以理解，已经不建议使用，统一单一传参
	@Deprecated
	private String[] dateParams = { "begindate", "begintime", "bizdate", "biztime", "businessdate", "businesstime" };

	/**
	 * 自动检测时间(默认3分钟)
	 */
	private int checkSeconds = 180;

	/**
	 * 不同dataSource对应的使用权重
	 */
	private Map<String, Integer> dataSourceWeight;

	// 格式 {dataSource,weight}
	private Object[][] dataSourceWeightConfig;

	private int[] weights;

	/**
	 * spring 上下文容器
	 */
	private ApplicationContext applicationContext;

	@Override
	@Autowired
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sagacity.sqltoy.plugins.sharding.ShardingStrategy#initialize()
	 */
	@Override
	public void initialize() {
		if (dataSourceWeight == null || dataSourceWeight.isEmpty()) {
			return;
		}
		dataSourceWeightConfig = new Object[dataSourceWeight.size()][2];
		weights = new int[dataSourceWeight.size()];
		Iterator<Entry<String, Integer>> entryIter = dataSourceWeight.entrySet().iterator();
		Entry<String, Integer> entry;
		int i = 0;
		while (entryIter.hasNext()) {
			entry = entryIter.next();
			dataSourceWeightConfig[i] = new Object[] { entry.getKey(), entry.getValue() };
			weights[i] = entry.getValue();
			i++;
		}
		// 检测时间小于等于零,则表示不做自动检测
		if (checkSeconds <= 0) {
			return;
		}
		if (checkSeconds < 60) {
			checkSeconds = 60;
		}
		SpringAppContext appContext = new SpringAppContext(applicationContext);
		IdleConnectionMonitor monitor = new IdleConnectionMonitor(appContext, new SpringConnectionFactory(),
				dataSourceWeightConfig, weights, 60, checkSeconds);
		monitor.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sagacity.sqltoy.plugins.sharding.ShardingStrategy#getTargetTableName(org.
	 * sagacity.sqltoy.SqlToyContext, java.lang.String, java.lang.String,
	 * java.util.HashMap)
	 */
	@Override
	public String getShardingTable(SqlToyContext sqlToyContext, Class entityClass, String baseTableName,
								   String decisionType, IgnoreCaseLinkedMap<String, Object> paramsMap) {
		if (paramsMap == null || baseTableName == null || tableNamesMap == null) {
			return null;
		}
		if (tableNamesMap.get(baseTableName.toUpperCase()) == null) {
			return null;
		}
		Object bizDate = null;
		String[] shardingTable = tableNamesMap.get(baseTableName.toUpperCase()).split("\\,");
		// 单一参数，表示直接传递参数值
		if (paramsMap.size() == 1) {
			bizDate = paramsMap.values().iterator().next();
		} else if (dateParams != null) {
			for (int i = 0; i < dateParams.length; i++) {
				// 业务时间条件值
				bizDate = paramsMap.get(dateParams[i]);
				if (bizDate != null) {
					break;
				}
			}
		}
		if (bizDate == null) {
			logger.error("分表操作对应的参数值为null,导致无法分表,请检查参数配置!");
			return null;
		}
		// 间隔多少天
		int intervalDays = Math.abs(DateUtil.getIntervalDays(DateUtil.getNowTime(), bizDate));
		int index = -1;
		for (int i = 0; i < days.length; i++) {
			if (intervalDays >= days[i]) {
				index = i;
				break;
			}
		}
		// 返回null,表示使用原表
		if (index == -1) {
			logger.debug("日期间隔:{} 天,小于最小分表区间则使用当前sql中的表!", intervalDays);
			return null;
		}
		String tableName;
		if (index > shardingTable.length - 1) {
			tableName = shardingTable[shardingTable.length - 1].trim();
		} else {
			tableName = shardingTable[index].trim();
		}
		logger.debug("分表实际取得表名:{}", tableName);
		return tableName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sagacity.sqltoy.plugins.sharding.ShardingStrategy#getShardingModel(org.
	 * sagacity. sqltoy.SqlToyContext, java.lang.String, java.lang.String,
	 * java.util.HashMap)
	 */
	@Override
	public ShardingDBModel getShardingDB(SqlToyContext sqlToyContext, Class entityClass, String tableOrSql,
			String decisionType, IgnoreCaseLinkedMap<String, Object> paramsMap) {
		// 为null则使用service或dao中默认注入的dataSource
		if (dataSourceWeight == null || dataSourceWeight.isEmpty()) {
			return null;
		}
		return getDataSource();
	}

	/**
	 * @TODO 根据权重配置分配数据库
	 * @return
	 */
	private ShardingDBModel getDataSource() {
		int index = 0;
		String chooseDataSource = null;
		// 根据权重进行随机取具体哪个dataSource
		if (dataSourceWeightConfig.length > 1) {
			index = NumberUtil.getProbabilityIndex(weights);
		}
		chooseDataSource = dataSourceWeightConfig[index][0].toString();
		logger.debug("分库取得的数据库为:{},index={}", chooseDataSource, index);
		ShardingDBModel shardingModel = new ShardingDBModel();
		shardingModel.setDataSourceName(chooseDataSource);
		shardingModel.setDataSource((DataSource) applicationContext.getBean(chooseDataSource));
		return shardingModel;
	}

	/**
	 * @param dataSourceWeight the dataSourceWeight to set
	 */
	public void setDataSourceWeight(Map<String, Integer> dataSourceWeight) {
		this.dataSourceWeight = dataSourceWeight;
	}

	/**
	 * @param checkSeconds the checkSeconds to set
	 */
	public void setCheckSeconds(int checkSeconds) {
		this.checkSeconds = checkSeconds;
	}

	/**
	 * @param tableMap the tableNamesMap to set
	 */
	public void setTableNamesMap(Map<String, String> tableMap) {
		Iterator<Entry<String, String>> iter = tableMap.entrySet().iterator();
		Entry<String, String> entry;
		while (iter.hasNext()) {
			entry = iter.next();
			// key大写转化,避免匹配错误
			this.tableNamesMap.put(entry.getKey().toUpperCase(), entry.getValue());
		}
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(String days) {
		String[] daysAry = days.split("\\,");
		this.days = new Integer[daysAry.length];
		for (int i = 0; i < daysAry.length; i++) {
			this.days[i] = Integer.parseInt(daysAry[i].trim());
		}
	}

	/**
	 * @param dateParams the dateParams to set
	 */
	public void setDateParams(String dateParams) {
		this.dateParams = dateParams.toLowerCase().split("\\,");
	}

}
