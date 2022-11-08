package cn.fanzy.breeze.sqltoy.plus.conditions.toolkit;

import cn.fanzy.breeze.sqltoy.plus.conditions.interfaces.SFunction;
import org.sagacity.sqltoy.exception.DataAccessException;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class ColumnUtils {

    @SafeVarargs
    public static <T> List<SFunction<T, ?>> of(SFunction<T, ?>... functions) {
        if (functions == null || functions.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(functions).collect(Collectors.toList());
    }

    public static List<String> of(String... fields) {
        if (fields == null || fields.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(fields).collect(Collectors.toList());
    }

    /**
     * 转换对象为map
     *
     * @param object
     * @param ignore
     * @return HashMap<String, Object>
     */
    public static HashMap<String, Object> objectToObjectMap(Object object, String... ignore) {
        if (object == null) {
            return null;
        }
        HashMap<String, Object> tempMap = new LinkedHashMap<String, Object>();
        for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field[] fields = clazz.getDeclaredFields();
                if (fields != null && fields.length > 0) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        // 排除类变量
                        if (Modifier.isStatic(field.getModifiers())) {
                            continue;
                        }
                        boolean ig = false;
                        if (ignore != null && ignore.length > 0) {
                            for (String i : ignore) {
                                if (i.equals(field.getName())) {
                                    ig = true;
                                    break;
                                }
                            }
                        }
                        if (ig) {
                            continue;
                        }
                        Object o = null;
                        try {
                            o = field.get(object);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if(o != null) {
                            tempMap.put(field.getName(), o);
                        }
                    }
                }
            } catch (Exception e) {
                throw new DataAccessException("sqlToy plus entity to map occur exception");
            }
        }
        return tempMap;
    }
}
