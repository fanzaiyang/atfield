package cn.fanzy.atfield.sqltoy.flex.query;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.fanzy.atfield.sqltoy.flex.utils.LambdaGetter;
import cn.fanzy.atfield.sqltoy.flex.utils.LambdaUtil;
import cn.fanzy.atfield.sqltoy.flex.utils.SqlConstants;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.model.ParamsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Getter
public class LambdaEntityQuery<T> extends LambdaQuery<T> {

    public LambdaEntityQuery(Class<T> clazz) {
        super(clazz);
    }

    @SafeVarargs
    public final LambdaEntityQuery<T> select(LambdaGetter<T>... fields) {
        String[] extraFields = extraLambdaFields(fields);
        entityQuery.select(extraFields);
        return this;
    }

    @SafeVarargs
    public final LambdaEntityQuery<T> unselect(LambdaGetter<T>... fields) {
        String[] extraFields = extraLambdaFields(fields);
        entityQuery.unselect(extraFields);
        return this;
    }

    public final LambdaEntityQuery<T> groupBy(LambdaGetter<T> field) {
        String fieldName = extraLambdaField(field);
        groupBy.add(StrUtil.format("{}", fieldName));
        return this;
    }

    public final LambdaEntityQuery<T> orderBy(LambdaGetter<T> field, boolean desc) {
        String fieldName = extraLambdaField(field);
        orderBy.add(StrUtil.format("{} {}", fieldName, desc ? "desc" : ""));
        return this;
    }

    /**
     * 按 ASC 分组
     *
     * @param field 田
     * @return {@link LambdaQuery}
     */
    public final LambdaEntityQuery<T> orderByAsc(LambdaGetter<T> field) {
        String fieldName = extraLambdaField(field);
        orderBy.add(StrUtil.format("{}", fieldName));
        return this;
    }

    /**
     * 按 DESC 分组
     *
     * @param field 田
     * @return {@link LambdaQuery}
     */
    public final LambdaEntityQuery<T> orderByDesc(LambdaGetter<T> field) {
        String fieldName = extraLambdaField(field);
        orderBy.add(StrUtil.format("{} desc", fieldName));
        return this;
    }
}
