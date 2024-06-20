package cn.fanzy.atfield.core.utils;

import cn.hutool.core.bean.BeanUtil;

/**
 * 实体类工具类
 *
 * @author fanzaiyang
 * @date 2024-01-21
 */
public class BeanUtils extends BeanUtil {

    public static Object[] toArray(Object obj) {
        ByteRation.of(1000,ByteCalcUnit.Bytes).getGb();
        return beanToMap(obj).values().toArray();
    }
}
