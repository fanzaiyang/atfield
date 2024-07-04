package cn.fanzy.atfield.sqltoy.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
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
public class JsonTypeHandler extends TypeHandler {

    @Override
    public boolean setValue(Integer dbType, PreparedStatement pst, int paramIndex, int jdbcType, Object value) throws SQLException {
        if (jdbcType == Types.ARRAY) {
            pst.setString(paramIndex, JSONUtil.toJsonStr(value));
            return true;
        }
        return false;
    }

    @Override
    public Object toJavaType(String javaTypeName, Class genericType, Object jdbcValue) throws Exception {
        if (javaTypeName.equalsIgnoreCase("java.util.List") && genericType != null) {
            JSONConfig config = new JSONConfig();
            config.setDateFormat("yyyy-MM-dd HH:mm:ss");
            config.setIgnoreNullValue(true);
            config.setWriteLongAsString(true);
            return JSONUtil.parseArray(StrUtil.blankToDefault(jdbcValue.toString(), "[]"), config);
        }
        return super.toJavaType(javaTypeName, genericType, jdbcValue);
    }
}
