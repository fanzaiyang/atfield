package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.eumn.CompareEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * sql段元
 * 代码片段最小单元
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public class SqlSegmentMeta implements ISqlSegment {

    private static final long serialVersionUID = 6268438527351504845L;
    /**
     * 库字段名称
     */
    private String entityFiledName;

    /**
     * 数据库字段名称
     */
    private String columnName;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 比较方式
     */
    private CompareEnum compareEnum;

    /**
     * key值
     */
    private String key;

    /**
     * value值
     */
    public Object value;


    public void putPair(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getSqlSegment() {
        if (compareEnum != null && paramName != null && columnName != null) {
            return compareEnum.getMetaSql(columnName, paramName);
        }
        return null;
    }

    @Override
    public Map<String, Object> getSqlSegmentParamMap() {
        Map<String, Object> map = new HashMap<>(1);
        map.put(key, value);
        return map;
    }

    public String getEntityFiledName() {
        return entityFiledName;
    }

    public void setEntityFiledName(String entityFiledName) {
        this.entityFiledName = entityFiledName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public CompareEnum getCompareEnum() {
        return compareEnum;
    }

    public void setCompareEnum(CompareEnum compareEnum) {
        this.compareEnum = compareEnum;
    }
}
