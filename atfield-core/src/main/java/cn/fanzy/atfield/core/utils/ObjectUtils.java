package cn.fanzy.atfield.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;

/**
 * Object 对象工具类
 *
 * @author fanzaiyang
 * @date 2023/11/30
 */
public class ObjectUtils extends cn.hutool.core.util.ObjectUtil {
    private ObjectUtils() {
    }

    /**
     * 铸造
     *
     * @param object 对象
     * @param clazz  clazz
     * @return {@link T}
     */
    public static <T> T cast(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        if (clazz.isAssignableFrom(object.getClass())) {
            return clazz.cast(object);
        }
        if (JSONUtil.isTypeJSONObject(object.toString())) {
            return JSONUtil.toBean(object.toString(), clazz);
        }
        // 最后尝试使用BeanUtil转对象
        return BeanUtil.toBean(object, clazz);
    }
}
