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
package cn.fanzy.breeze.sqltoy.plus.conditions.interfaces;

import java.io.Serializable;

/**
 * 查询条件封装
 * <p>拼接</p>
 */
public interface Join<Children> extends Serializable {

    /**
     * ignore
     */
    default Children or() {
        return or(true);
    }

    /**
     * 拼接 OR
     *
     * @param condition 执行条件
     * @return children
     */
    Children or(boolean condition);


    default Children and() {
        return and(true);
    }

    /**
     * 拼接 and
     *
     * @param condition 执行条件
     * @return children
     */
    Children and(boolean condition);


    default Children last(String lastSql) {
        return last(true, lastSql);
    }

    /**
     * 无视优化规则直接拼接到 sql 的最后(有sql注入的风险,请谨慎使用)
     * <p>例: last("limit 1")</p>
     * <p>注意只能调用一次,多次调用以最后一次为准</p>
     *
     * @param condition 执行条件
     * @param lastSql   sql语句
     * @return children
     */
    Children last(boolean condition, String lastSql);
}
