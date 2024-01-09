package cn.fanzy.field.core.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

/**
 * infra常数
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
public class Constants {
    public static final List<String> SWAGGER_LIST = CollUtil.toList("/doc.html","/doc.html#/**","/doc.html/**","/swagger-ui/**","/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/favicon.ico", "/error","/v3/api-docs/**");

}
