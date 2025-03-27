package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

/**
 * dict utils
 *
 * @author fanzaiyang
 * @date 2025/02/11
 */
@Component
public class EnvUtils {


    /**
     * 是否生产环境
     *
     * @return {@link String }
     */
    public static boolean isProd() {
        return isEnv("prod");
    }

    /**
     * 是否开发环境
     *
     * @return boolean
     */
    public static boolean isDev() {
        return isEnv("dev");
    }

    /**
     * 是否测试环境
     *
     * @return boolean
     */
    public static boolean isTest() {
        return isEnv("test");
    }

    public static boolean isEnv(String env) {
        return StrUtil.equalsIgnoreCase(SpringUtils.getActiveProfile(), env);
    }
}
