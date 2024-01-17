package cn.fanzy.atfield.sqltoy.flex.query;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.sqltoy.flex.utils.LambdaGetter;
import cn.fanzy.atfield.sqltoy.flex.utils.LambdaUtil;
import cn.fanzy.atfield.sqltoy.flex.utils.SqlConstants;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.model.EntityQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Getter
public class LambdaEntityQuery {

    private final EntityQuery entityQuery = EntityQuery.create();

    /**
     * 哪里
     */
    private List<String> where = new ArrayList<>();
    /**
     * 参数名字
     */
    private List<String> paramNames = new ArrayList<>();
    /**
     * 参数值
     */
    private List<Object> paramValues = new ArrayList<>();

    private final List<String> groupBy = new ArrayList<>();

    private final List<String> orderBy = new ArrayList<>();

    public static LambdaEntityQuery create() {
        return new LambdaEntityQuery();
    }

    public LambdaEntityQuery(List<String> where, List<String> paramNames, List<Object> paramValues) {
        this.where = where;
        this.paramNames = paramNames;
        this.paramValues = paramValues;
    }

    @SafeVarargs
    public final <T> LambdaEntityQuery select(LambdaGetter<T>... fields) {
        String[] extraFields = extraLambdaFields(fields);
        entityQuery.select(extraFields);
        return this;
    }

    @SafeVarargs
    public final <T> LambdaEntityQuery unselect(LambdaGetter<T>... fields) {
        String[] extraFields = extraLambdaFields(fields);
        entityQuery.unselect(extraFields);
        return this;
    }

    /**
     * =
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery eq(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} = {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * NE !=
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery ne(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} != {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * >
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery gt(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} > {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * >=
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery ge(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} >= {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * 小于<
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery lt(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} < {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * <=
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery le(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} <= {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * like
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery like(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} like {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * not like
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery notLike(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} not like {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * 为 null
     * is null
     *
     * @param field     田
     * @param condition 条件
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery isNull(boolean condition, LambdaGetter<T> field) {
        if (!condition) {
            return this;
        }
        String fieldName = extraLambdaField(field);
        where.add(StrUtil.format("{} is null", fieldName));
        return this;
    }

    /**
     * is not null
     *
     * @param condition 条件
     * @param field     田
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery isNotNull(boolean condition, LambdaGetter<T> field) {
        if (!condition) {
            return this;
        }
        String fieldName = extraLambdaField(field);
        where.add(StrUtil.format("{} is not null", fieldName));
        return this;
    }

    /**
     * in
     *
     * @param field  田
     * @param values 值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery in(LambdaGetter<T> field, Object... values) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} in ({})]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        List<Object> tmpParamValues = new ArrayList<>();
        for (Object value : values) {
            if (value instanceof Collection) {
                tmpParamValues.addAll((Collection<T>) value);
                continue;
            }
            tmpParamValues.add(value);
        }
        paramValues.add(tmpParamValues);
        return this;
    }

    public final <T> LambdaEntityQuery in(LambdaGetter<T> field, List<Object> values) {
        return in(field, values.toArray());
    }

    /**
     * not in
     *
     * @param field  田
     * @param values 值
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery notIn(LambdaGetter<T> field, Object... values) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        where.add(StrUtil.format("#[{} not in ({})]", fieldName, paramName));
        List<Object> tmpParamValues = new ArrayList<>();
        for (Object value : values) {
            if (value instanceof Collection) {
                tmpParamValues.addAll((Collection<T>) value);
                continue;
            }
            tmpParamValues.add(value);
        }
        paramNames.add(paramName);
        paramValues.add(tmpParamValues);
        return this;
    }

    public final <T> LambdaEntityQuery notIn(LambdaGetter<T> field, List<Object> values) {
        return notIn(field, values.toArray());
    }

    /**
     * 之间
     * between
     *
     * @param field 田
     * @param start 开始
     * @param end   结束
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery between(LambdaGetter<T> field, Object start, Object end) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String paramNameStart = StrUtil.format("{}{}", paramName, "Start");
        String paramNameEnd = StrUtil.format("{}{}", paramName, "end");
        where.add(StrUtil.format("#[{} between {} and {}]", fieldName, paramNameStart, paramNameEnd));
        paramNames.add(paramNameStart);
        paramNames.add(paramNameEnd);
        paramValues.add(start);
        paramValues.add(end);
        return this;
    }

    /**
     * not between
     *
     * @param field 田
     * @param start 开始
     * @param end   结束
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery notBetween(LambdaGetter<T> field, Object start, Object end) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String paramNameStart = StrUtil.format("{}{}", paramName, "Start");
        String paramNameEnd = StrUtil.format("{}{}", paramName, "end");
        where.add(StrUtil.format("#[{} not between {} and {}]", fieldName, paramNameStart, paramNameEnd));
        paramNames.add(paramNameStart);
        paramNames.add(paramNameEnd);
        paramValues.add(start);
        paramValues.add(end);
        return this;
    }

    public final <T> LambdaEntityQuery groupBy(LambdaGetter<T> field) {
        String fieldName = extraLambdaField(field);
        groupBy.add(StrUtil.format("{}", fieldName));
        return this;
    }

    public final <T> LambdaEntityQuery orderBy(LambdaGetter<T> field, boolean desc) {
        String fieldName = extraLambdaField(field);
        orderBy.add(StrUtil.format("{} {}", fieldName, desc ? "desc" : ""));
        return this;
    }

    /**
     * 按 ASC 分组
     *
     * @param field 田
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery orderByAsc(LambdaGetter<T> field) {
        String fieldName = extraLambdaField(field);
        orderBy.add(StrUtil.format("{}", fieldName));
        return this;
    }

    /**
     * 按 DESC 分组
     *
     * @param field 田
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery orderByDesc(LambdaGetter<T> field) {
        String fieldName = extraLambdaField(field);
        orderBy.add(StrUtil.format("{} desc", fieldName));
        return this;
    }

    public final <T> LambdaEntityQuery and() {
        where.add("AND");
        return this;
    }

    public final <T> LambdaEntityQuery or() {
        where.add("OR");
        return this;
    }

    /**
     * and
     *
     * @param query 查询
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery and(Consumer<LambdaEntityQuery> query) {
        LambdaEntityQuery entityQuery = LambdaEntityQuery.create();
        query.accept(entityQuery);
        and();
        where.add("(" + StrUtil.join(" ", entityQuery.getWhere()) + ")");
        return this;
    }

    /**
     * or
     *
     * @param query 查询
     * @return {@link LambdaEntityQuery}
     */
    public final <T> LambdaEntityQuery or(Consumer<LambdaEntityQuery> query) {
        LambdaEntityQuery entityQuery = LambdaEntityQuery.create();
        query.accept(entityQuery);
        or();
        where.add("(" + StrUtil.join(" ", entityQuery.getWhere()) + ")");
        return this;
    }

    public EntityQuery build() {
        String select = StrUtil.join(",", paramNames);
        StringBuilder whereStr = new StringBuilder();
        for (int i = 0; i < where.size(); i++) {
            int nextIndex = i + 1;
            String thisValue = where.get(i);
            String nextValue = "";
            if (nextIndex < where.size()) {
                nextValue = where.get(nextIndex);
            }
            if (SqlConstants.KEYWORDS.contains(thisValue.toUpperCase())) {
                continue;
            }
            whereStr.append(thisValue);
            if (!SqlConstants.KEYWORDS.contains(nextValue.toUpperCase())) {
                whereStr.append("AND");
            }
            whereStr.append(nextValue);
        }
        return entityQuery.select(select)
                .where(whereStr.toString())
                .names(ArrayUtil.toArray(paramNames, String.class))
                .values(ArrayUtil.toArray(paramValues, Object.class))
                .groupBy();
    }

    public EntityQuery getEntityQuery() {
        return build();
    }

    @SafeVarargs
    private final <T> String[] extraLambdaFields(LambdaGetter<T>... fields) {
        if (fields == null || fields.length == 0) {
            return new String[0];
        }
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = extraLambdaField(fields[i]);
        }
        return fieldNames;
    }

    private <T> String extraLambdaField(LambdaGetter<T> field) {
        String fieldName = LambdaUtil.getFieldName(field);
        Class<?> implClass = LambdaUtil.getImplClass(field);
        EntityMeta entityMeta = SpringUtils.getBean(SqlToyContext.class)
                .getEntityMeta(implClass);
        String camelCase = StrUtil.toUnderlineCase(fieldName);
        if (entityMeta == null) {
            return camelCase;
        }
        String columnName = entityMeta.getColumnName(fieldName);
        return StrUtil.blankToDefault(columnName, camelCase);
    }

    private String getParamName(String fieldName) {
        return StrUtil.format(":param.{}", fieldName);
    }
}
