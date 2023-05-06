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
package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.eumn.SqlKeyword;

import java.util.function.Predicate;

/**
 * 匹配段
 * 匹配片段
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public enum MatchSegment {
    /**
     * GROUP_BY
     */
    GROUP_BY(i -> i == SqlKeyword.GROUP_BY),
    /**
     * ORDER_BY
     */
    ORDER_BY(i -> i == SqlKeyword.ORDER_BY),
    /**
     * NOT
     */
    NOT(i -> i == SqlKeyword.NOT),
    /**
     * AND
     */
    AND(i -> i == SqlKeyword.AND),
    /**
     * OR
     */
    OR(i -> i == SqlKeyword.OR),
    /**
     * AND_OR
     */
    AND_OR(i -> i == SqlKeyword.AND || i == SqlKeyword.OR),
    /**
     * EXISTS
     */
    EXISTS(i -> i == SqlKeyword.EXISTS),
    /**
     * HAVING
     */
    HAVING(i -> i == SqlKeyword.HAVING);

    private final Predicate<ISqlSegment> predicate;

    MatchSegment(Predicate<ISqlSegment> predicate) {
        this.predicate = predicate;
    }

    public boolean match(ISqlSegment segment) {
        return getPredicate().test(segment);
    }

    public Predicate<ISqlSegment> getPredicate() {
        return predicate;
    }
}
