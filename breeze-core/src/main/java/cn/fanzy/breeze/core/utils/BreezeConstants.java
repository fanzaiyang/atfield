package cn.fanzy.breeze.core.utils;

import cn.hutool.core.collection.CollUtil;

import java.util.List;

public class BreezeConstants {
    public static final List<String> SWAGGER_LIST = CollUtil.toList("/doc.html","/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/favicon.ico", "/error","/v3/api-docs/**");

    /**
     * 默认tree的根结点
     */
    public static final String TREE_ROOT_ID = "-1";

    /**
     * 树的上级ID
     */
    public static final String TREE_PARENT_ID="parentId";

    /**
     * 默认密码
     */
    public static final String DEFAULT_PASSWORD="123456a?";
}
