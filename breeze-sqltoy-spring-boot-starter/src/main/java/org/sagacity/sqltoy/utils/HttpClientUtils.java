package org.sagacity.sqltoy.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.sagacity.sqltoy.SqlToyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * @project sagacity-sqltoy
 * @description 提供基于http请求的工具类
 * @author zhongxuchen
 * @version v1.0,Date:2018年1月7日
 */
public class HttpClientUtils {
	/**
	 * 请求配置
	 */
	private final static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(30000)
			.setConnectTimeout(10000).setSocketTimeout(180000).build();

	/**
	 * 定义全局日志
	 */
	protected final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	private final static String CHARSET = "UTF-8";

	private final static String SEARCH = "_search";

	private final static String CONTENT_TYPE = "application/json";

	private final static String POST = "POST";

	private HttpClientUtils() {
	}

	public static String doPost(SqlToyContext sqltoyContext, final String url, String username, String password,
			String[] paramName, String[] paramValue) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		// 设置connection是否自动关闭
		httpPost.setHeader("Connection", "close");
		httpPost.setConfig(requestConfig);
		CloseableHttpClient client = null;
		try {
			if (StringUtil.isNotBlank(username) && StringUtil.isNotBlank(password)) {
				// 凭据提供器
				CredentialsProvider credsProvider = new BasicCredentialsProvider();
				credsProvider.setCredentials(AuthScope.ANY,
						// 认证用户名和密码
						new UsernamePasswordCredentials(username, password));
				client = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
			} else {
				client = HttpClients.createDefault();
			}
			if (paramValue != null && paramValue.length > 0) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (int i = 0; i < paramValue.length; i++) {
					if (paramValue[i] != null) {
						nvps.add(new BasicNameValuePair(paramName[i], paramValue[i]));
					}
				}
				HttpEntity httpEntity = new UrlEncodedFormEntity(nvps, CHARSET);
				((UrlEncodedFormEntity) httpEntity).setContentType(CONTENT_TYPE);
				httpPost.setEntity(httpEntity);
			}
			HttpResponse response = client.execute(httpPost);
			// 返回结果
			HttpEntity reponseEntity = response.getEntity();
			if (reponseEntity != null) {
				return EntityUtils.toString(reponseEntity, CHARSET);
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
}
