package cn.fanzy.atfield.sqltoy.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.plugins.TypeHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

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
        if (jdbcType == Types.ARRAY || jdbcType == Types.JAVA_OBJECT) {
            pst.setString(paramIndex, JSONUtil.toJsonStr(value));
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
        if (jdbcValue == null) {
            return super.toJavaType(javaTypeName, genericType, null);
        }
        if (jdbcValue.toString().startsWith("{") && jdbcValue.toString().endsWith("}")) {
            return JSONUtil.toBean(jdbcValue.toString(), Class.forName(javaTypeName));
        }
        if (jdbcValue.toString().startsWith("[") && jdbcValue.toString().endsWith("]")) {
            return JSONUtil.toList(jdbcValue.toString(), genericType == null ? Object.class : genericType);
        }

        return super.toJavaType(javaTypeName, genericType, jdbcValue);
    }
}
