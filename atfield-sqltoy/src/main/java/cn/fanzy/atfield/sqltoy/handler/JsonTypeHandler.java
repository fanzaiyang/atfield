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
        if (jdbcType == Types.ARRAY ||
            jdbcType == Types.JAVA_OBJECT) {
            pst.setString(paramIndex, JSONUtil.toJsonStr(value));
            return true;
        }
        return false;
    }

    @Override
    public Object toJavaType(String javaTypeName, Class genericType, Object jdbcValue) throws Exception {
        log.info("转为Java类型：javaTypeName:{}", javaTypeName);

        Class<?> clazz = Class.forName(javaTypeName);
        if (Collection.class.isAssignableFrom(clazz) || clazz.isArray()) {
            String value = StrUtil.blankToDefault(jdbcValue.toString(), "[]");
            return JSONUtil.toList(value, genericType == null ? Object.class : genericType);
        }
        if (ClassUtil.isNormalClass(clazz) && !StrUtil.startWith(javaTypeName, "java.")) {
            return JSONUtil.toBean(jdbcValue.toString(), clazz);
        }
        if (jdbcValue instanceof String) {
            if (jdbcValue.toString().startsWith("{") && jdbcValue.toString().endsWith("}")) {
                return JSONUtil.toBean(jdbcValue.toString(), clazz);
            }
            if (jdbcValue.toString().startsWith("[") && jdbcValue.toString().endsWith("]")) {
                return JSONUtil.toList(jdbcValue.toString(), genericType == null ? Object.class : genericType);
            }
        }

        return super.toJavaType(javaTypeName, genericType, jdbcValue);
    }
}
