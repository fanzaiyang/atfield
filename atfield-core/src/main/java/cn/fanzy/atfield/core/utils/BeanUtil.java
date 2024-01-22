package cn.fanzy.atfield.core.utils;

/**
 * 实体类工具类
 *
 * @author fanzaiyang
 * @date 2024-01-21
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    public static Object[] toArray(Object obj) {
        return beanToMap(obj).values().toArray();
    }
}
