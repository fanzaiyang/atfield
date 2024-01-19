/*
 * Copyright (c) 2011-2022, baomidou (jobob@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.fanzy.atfield.sqltoy.plus.conditions.interfaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 查询条件封装
 *
 */
@SuppressWarnings("unchecked")
public interface Func<Children extends Func<Children, R>, R> extends Serializable {

    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy("id")</p>
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @return children
     */
    Children groupBy(boolean condition, R column);

    default Children groupBy(R column) {
        return groupBy(true, column);
    }

    /**
     * 分组：GROUP BY 字段, ...
     * <p>例: groupBy(Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段数组
     * @return children
     */
    Children groupBy(boolean condition, List<R> columns);

    default Children groupBy(List<R> columns) {
        return groupBy(true, columns);
    }

    default Children groupBy(R column, R... columns) {
        return groupBy(true, column, columns);
    }


    Children groupBy(boolean condition, R column, R... columns);

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc(true, "id")</p>
     *
     * @param condition 执行条件
     * @param column    单个字段
     * @return children
     */
    default Children orderByAsc(boolean condition, R column) {
        return orderBy(condition, true, column);
    }

    default Children orderByAsc(R column) {
        return orderByAsc(true, column);
    }

    /**
     * 排序：ORDER BY 字段, ... ASC
     * <p>例: orderByAsc(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段数组
     * @return children
     */
    default Children orderByAsc(boolean condition, List<R> columns) {
        return orderBy(condition, true, columns);
    }

    default Children orderByAsc(List<R> columns) {
        return orderByAsc(true, columns);
    }

    default Children orderByAsc(R column, R... columns) {
        return orderByAsc(true, column, columns);
    }


    default Children orderByAsc(boolean condition, R column, R... columns) {
        return orderBy(condition, true, column, columns);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     * <p>例: orderByDesc(true, "id")</p>
     *
     * @param condition 执行条件
     * @param column    字段
     * @return children
     */
    default Children orderByDesc(boolean condition, R column) {
        return orderBy(condition, false, column);
    }

    default Children orderByDesc(R column) {
        return orderByDesc(true, column);
    }

    /**
     * 排序：ORDER BY 字段, ... DESC
     * <p>例: orderByDesc(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param columns   字段列表
     * @return children
     */
    default Children orderByDesc(boolean condition, List<R> columns) {
        return orderBy(condition, false, columns);
    }

    default Children orderByDesc(List<R> columns) {
        return orderByDesc(true, columns);
    }

    default Children orderByDesc(R column, R... columns) {
        return orderByDesc(true, column, columns);
    }


    default Children orderByDesc(boolean condition, R column, R... columns) {
        return orderBy(condition, false, column, columns);
    }

    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, "id")</p>
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param column    单个字段
     * @return children
     */
    Children orderBy(boolean condition, boolean isAsc, R column);

    /**
     * 排序：ORDER BY 字段, ...
     * <p>例: orderBy(true, Arrays.asList("id", "name"))</p>
     *
     * @param condition 执行条件
     * @param isAsc     是否是 ASC 排序
     * @param columns   字段列表
     * @return children
     */
    Children orderBy(boolean condition, boolean isAsc, List<R> columns);


    Children orderBy(boolean condition, boolean isAsc, R column, R... columns);


    default Children having(String sqlHaving, Map<String, Object> paramMap) {
        return having(true, sqlHaving, paramMap);
    }

    /**
     * HAVING ( sql语句 )
     * <p>例1: having("sum(age) &gt; 10")</p>
     * <p>例2: having("sum(age) &gt; {0}", 10)</p>
     *
     * @param condition 执行条件
     * @param sqlHaving sql 语句
     * @param paramMap    参数数据
     * @return children
     */
    Children having(boolean condition, String sqlHaving, Map<String, Object> paramMap);


    default Children accept(Consumer<Children> consumer) {
        return accept(true, consumer);
    }

    /**
     * 消费函数
     *
     * @param consumer 消费函数
     * @param condition boolen
     * @return children
     * @since 3.3.1
     */
    Children accept(boolean condition, Consumer<Children> consumer);
}
