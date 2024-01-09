/**
 * 
 */
package cn.fanzy.atfield.sqltoy.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 提供es基于http连接的配置，2021年开始有大量基于jdbc的模式，可不再使用
 * @author zhongxuchen
 * @version v1.0,Date:2020年2月20日
 */
@Getter
@Setter
public class ElasticConfig implements Serializable {

	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5753295867761297803L;

	/**
	 * 连接赋予的id
	 */
	private String id;

	/**
	 * 连接url地址
	 */
	private String url;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 相对路径
	 */
	private String sqlPath;
	
	/**
	 * 是否禁止抢占式身份认证
	 */
	private boolean authCaching=true;

	/**
	 * 证书类型
	 */
	private String keyStoreType;

	/**
	 * 证书文件
	 */
	private String keyStore;

	/**
	 * 证书秘钥
	 */
	private String keyStorePass;

	/**
	 * 证书是否自签名
	 */
	private boolean keyStoreSelfSign = true;

	private Integer requestTimeout;

	private Integer connectTimeout;

	private Integer socketTimeout;

	/**
	 * 字符集,默认UTF-8
	 */
	private String charset;

}
