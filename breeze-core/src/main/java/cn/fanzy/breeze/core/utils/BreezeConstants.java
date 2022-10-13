package cn.fanzy.breeze.core.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

public class BreezeConstants {
    public static final List<String> SWAGGER_LIST = CollUtil.toList("/doc.html", "/swagger-resources/**", "/webjars/**", "/favicon.ico", "/error");
}
