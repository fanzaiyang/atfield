package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.eumn.CompareEnum;
import cn.hutool.core.lang.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * 代码片段最小单元
 */
public class SqlSegmentMeta implements ISqlSegment {

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
     * sql中变量键值对
     */
    private Pair<String, Object> paramPair;


    public void putPair(String key, Object value) {
        paramPair = new Pair<>(key, value);
    }

    @Override
    public String getSqlSegment() {
        if (compareEnum != null && paramName != null && columnName != null) {
            return compareEnum.getMetaSql(paramName, columnName);
        }
        return null;
    }

    @Override
    public Map<String, Object> getSqlSegmentParamMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(paramPair.getKey(), paramPair.getValue());
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

    public Pair<String, Object> getParamPair() {
        return paramPair;
    }

    public void setParamPair(Pair<String, Object> paramPair) {
        this.paramPair = paramPair;
    }
}
