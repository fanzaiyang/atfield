/**
 *
 */
package cn.fanzy.breeze.web.code.generator;

import cn.fanzy.breeze.web.code.model.BreezeCode;
import cn.fanzy.breeze.web.code.properties.BreezeCodeProperties;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * <p>
 * 验证码生成器
 * </p>
 *
 * @author fanzaiyang
 * @date 2021/09/07
 */
public interface BreezeCodeGenerator<T extends BreezeCode> {

    /**
     * 生成验证码
     *
     * @param servletWebRequest 用户请求
     * @param properties    验证码属性配置
     * @return 验证码
     */
    T generate(ServletWebRequest servletWebRequest, BreezeCodeProperties properties);

    /**
     * 生成验证码的唯一标识符
     *
     * @param request        用户请求
     * @param properties 验证码属性配置
     * @return 验证码的唯一标识符
     */
    String generateKey(ServletWebRequest request, BreezeCodeProperties properties);

    /**
     * 获取请求中携带的验证码的内容
     *
     * @param request        用户请求
     * @param properties 验证码属性配置
     * @return 验证码的内容
     */
    String getCodeInRequest(ServletWebRequest request, BreezeCodeProperties properties);
}
