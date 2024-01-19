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
package cn.fanzy.atfield.sqltoy.plus.conditions.eumn;


import cn.fanzy.atfield.sqltoy.plus.conditions.ISqlSegment;

/**
 * sql关键字
 * SQL 保留关键字枚举
 *
 * @author fanzaiyang
 * @date 2023-05-16
 */
public enum SqlKeyword implements ISqlSegment {
    /**
     * 和
     */
    AND("AND"),
    /**
     * 或
     */
    OR("OR"),
    /**
     * 不
     */
    NOT("NOT"),
    /**
     * 在
     */
    IN("IN"),
    /**
     * 不是在
     */
    NOT_IN("NOT IN"),
    /**
     * 就像
     */
    LIKE("LIKE"),
    /**
     * 不喜欢
     */
    NOT_LIKE("NOT LIKE"),
    /**
     * 情商
     */
    EQ("="),
    /**
     * 不
     */
    NE("<>"),
    /**
     * gt
     */
    GT(">"),
    /**
     * 通用电气
     */
    GE(">="),
    /**
     * lt
     */
    LT("<"),
    /**
     * 勒
     */
    LE("<="),
    /**
     * 为空
     */
    IS_NULL("IS NULL"),
    /**
     * 不是零
     */
    IS_NOT_NULL("IS NOT NULL"),
    /**
     * 集团根据
     */
    GROUP_BY("GROUP BY"),
    /**
     * 有
     */
    HAVING("HAVING"),
    /**
     * 订单根据
     */
    ORDER_BY("ORDER BY"),
    /**
     * 存在
     */
    EXISTS("EXISTS"),
    /**
     * 不存在
     */
    NOT_EXISTS("NOT EXISTS"),
    /**
     * 之间
     */
    BETWEEN("BETWEEN"),
    /**
     * 之间不
     */
    NOT_BETWEEN("NOT BETWEEN"),
    /**
     * asc
     */
    ASC("ASC"),
    /**
     * desc
     */
    DESC("DESC"),
    LAST("LAST"),
    SKIP("SKIP"),;

    private final String keyword;

    SqlKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getSqlSegment() {
        return keyword;
    }
}
