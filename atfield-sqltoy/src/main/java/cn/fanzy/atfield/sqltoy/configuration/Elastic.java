/**
 * 
 */
package cn.fanzy.atfield.sqltoy.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @description 提供es基于http连接的配置，2021年开始有大量基于jdbc的模式，可不再使用
 * @author zhongxuchen
 * @version v1.0,Date:2020年2月20日
 */
@Getter
@Setter
public class Elastic implements Serializable {


	@Serial
	private static final long serialVersionUID = -7130685535362259100L;

	/**
	 * 使用时默认使用的es集群点
	 */
	private String defaultId;

	/**
	 * 多个es集群配置
	 */
	private List<ElasticConfig> endpoints;


}
