package cn.fanzy.atfield.sqltoy.flex.query;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.sqltoy.flex.utils.LambdaGetter;
import cn.fanzy.atfield.sqltoy.flex.utils.LambdaUtil;
import cn.fanzy.atfield.sqltoy.flex.utils.SqlConstants;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.ParamsFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Getter
public class LambdaQuery<T> {

    protected final EntityQuery entityQuery = EntityQuery.create();

    /**
     * 哪里
     */
    private final List<String> where = new ArrayList<>();
    /**
     * 参数名字
     */
    private final List<String> paramNames = new ArrayList<>();
    /**
     * 参数值
     */
    private final List<Object> paramValues = new ArrayList<>();

    /**
     * 过滤 器
     */
    private final List<ParamsFilter> filters = new ArrayList<>();

    protected final List<String> groupBy = new ArrayList<>();

    protected final List<String> orderBy = new ArrayList<>();

    private final Class<T> clazz;

    public LambdaQuery(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * =
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> eq(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> ne(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> gt(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> ge(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> lt(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> le(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> like(LambdaGetter<T> field, Object value) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> notLike(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        String condition = StrUtil.format("#[{} not like {}]", fieldName, paramName);
        where.add(condition);
        paramNames.add(paramName);
        paramValues.add(value);
        return this;
    }

    /**
     * like value%
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaQuery}<{@link T}>
     */
    public final LambdaQuery<T> likeRight(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        ParamsFilter filter = new ParamsFilter(paramName.replace(":", ""));
        filter.rlike();
        filters.add(filter);
        entityQuery.filters(filters.toArray(new ParamsFilter[0]));
        return like(field, value);
    }

    /**
     * like %value
     *
     * @param field 田
     * @param value 价值
     * @return {@link LambdaQuery}<{@link T}>
     */
    public final LambdaQuery<T> likeLeft(LambdaGetter<T> field, Object value) {
        String fieldName = extraLambdaField(field);
        String paramName = getParamName(fieldName);
        ParamsFilter filter = new ParamsFilter(paramName.replace(":", ""));
        filter.llike();
        filters.add(filter);
        entityQuery.filters(filters.toArray(new ParamsFilter[0]));
        return like(field, value);
    }

    /**
     * 为 null
     * is null
     *
     * @param field     田
     * @param condition 条件
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> isNull(boolean condition, LambdaGetter<T> field) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> isNotNull(boolean condition, LambdaGetter<T> field) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> in(LambdaGetter<T> field, Object... values) {
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

    public final LambdaQuery<T> in(LambdaGetter<T> field, List<Object> values) {
        return in(field, values.toArray());
    }

    /**
     * not in
     *
     * @param field  田
     * @param values 值
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> notIn(LambdaGetter<T> field, Object... values) {
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

    public final LambdaQuery<T> notIn(LambdaGetter<T> field, List<Object> values) {
        return notIn(field, values.toArray());
    }

    /**
     * 之间
     * between
     *
     * @param field 田
     * @param start 开始
     * @param end   结束
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> between(LambdaGetter<T> field, Object start, Object end) {
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
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> notBetween(LambdaGetter<T> field, Object start, Object end) {
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

    public final LambdaQuery<T> and() {
        where.add("AND");
        return this;
    }

    public final LambdaQuery<T> or() {
        where.add("OR");
        return this;
    }

    /**
     * and
     *
     * @param query 查询
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> and(Consumer<LambdaQuery<T>> query) {
        LambdaQuery<T> entityQuery = new LambdaQuery<>(clazz);
        query.accept(entityQuery);
        and();
        where.add("(" + StrUtil.join(" ", entityQuery.getWhere()) + ")");
        return this;
    }

    /**
     * or
     *
     * @param query 查询
     * @return {@link LambdaQuery}
     */
    public final LambdaQuery<T> or(Consumer<LambdaQuery<T>> query) {
        LambdaQuery<T> entityQuery = new LambdaQuery<>(clazz);
        query.accept(entityQuery);
        or();
        where.add("(" + StrUtil.join(" ", entityQuery.getWhere()) + ")");
        return this;
    }

    public EntityQuery build() {
        StringBuilder whereStr = new StringBuilder();
        for (int i = 0; i < where.size(); i++) {
            int nextIndex = i + 1;
            String thisValue = where.get(i);
            String nextValue = " ";
            if (nextIndex < where.size()) {
                nextValue = where.get(nextIndex);
            }
            if (SqlConstants.KEYWORDS.contains(thisValue.toUpperCase())) {
                continue;
            }
            whereStr.append(thisValue);
            if (StrUtil.isNotBlank(nextValue) &&
                !SqlConstants.KEYWORDS.contains(nextValue.toUpperCase())) {
                whereStr.append(" AND ");
            }
            whereStr.append(nextValue);
        }
        return entityQuery
                .where(whereStr.toString())
                .names(paramNames.stream().map(name -> name.replace(":", ""))
                        .toArray(String[]::new))
                .values(paramValues.toArray())
                .groupBy();
    }

    public EntityQuery getEntityQuery() {
        return build();
    }

    @SafeVarargs
    protected final String[] extraLambdaFields(LambdaGetter<T>... fields) {
        if (fields == null || fields.length == 0) {
            return new String[0];
        }
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = extraLambdaField(fields[i]);
        }
        return fieldNames;
    }

    protected String extraLambdaField(LambdaGetter<T> field) {
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
        return StrUtil.format(":param_{}", fieldName);
    }
}
