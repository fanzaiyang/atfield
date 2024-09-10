package cn.fanzy.atfield.sqltoy.handler;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.plugins.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

/**
 * JSON 类型处理程序
 *
 * @author fanzaiyang
 * @date 2024/02/21
 */
@Slf4j
public class JsonTypeHandler extends TypeHandler {

    @Override
    public boolean setValue(Integer dbType, PreparedStatement pst, int paramIndex, int jdbcType, Object value) throws SQLException {
        if (jdbcType == Types.JAVA_OBJECT) {
            pst.setString(paramIndex, JSONUtil.toJsonStr(value == null ? "{}" : value));
            return true;
        }
        if (jdbcType == Types.ARRAY) {
            pst.setString(paramIndex, JSONUtil.toJsonStr(value == null ? "[]" : value));
            return true;
        }
        return false;
    }

    @Override
    public Object toJavaType(String javaTypeName, Class genericType, Object jdbcValue) throws Exception {
        // 没有泛型信息，直接使用默认的转换
        if (StrUtil.startWith(javaTypeName, "java.") && genericType == null) {
            return super.toJavaType(javaTypeName, null, jdbcValue);
        }
        // 泛型信息不为空，且jdbcValue为空，则使用默认的转换
        if (jdbcValue == null || StrUtil.isBlank(jdbcValue.toString())) {
            Class<?> clazz = Class.forName(javaTypeName);
            if (Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
                return JSONUtil.toList("[]", genericType == null ? Object.class : genericType);
            }
            if (ClassUtil.isNormalClass(clazz)) {
                return JSONUtil.toBean("{}", clazz);
            }
            return super.toJavaType(javaTypeName, genericType, null);
        }
        if (JSONUtil.isTypeJSONObject(jdbcValue.toString())) {
            return JSONUtil.toBean(jdbcValue.toString(), Class.forName(javaTypeName));
        }
        if (JSONUtil.isTypeJSONArray(jdbcValue.toString())) {
            return JSONUtil.toList(jdbcValue.toString(), genericType == null ? Object.class : genericType);
        }

        return super.toJavaType(javaTypeName, genericType, jdbcValue);
    }
}
