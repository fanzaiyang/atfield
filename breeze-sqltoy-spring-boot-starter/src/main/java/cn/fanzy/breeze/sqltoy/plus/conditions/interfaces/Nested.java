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
import java.util.function.Function;

/**
 * 查询条件封装
 * <p>嵌套</p>
 * <li>泛型 Param 是具体需要运行函数的类(也是 wrapper 的子类)</li>
 * @author fanzaiyang
 */
public interface Nested<Param, Children> extends Serializable {

    /**
     * ignore
     */
    default Children and(Function<Param, Param> function) {
        return and(true, function);
    }

    /**
     * AND 嵌套
     * <p>
     * 例: and(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param condition 执行条件
     * @param function  消费函数
     * @return children
     */
    Children and(boolean condition, Function<Param, Param> function);

    /**
     * ignore
     */
    default Children or(Function<Param, Param> function) {
        return or(true, function);
    }

    /**
     * OR 嵌套
     * <p>
     * 例: or(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param condition 执行条件
     * @param function  消费函数
     * @return children
     */
    Children or(boolean condition, Function<Param, Param> function);

    /**
     * ignore
     */
    default Children nested(Function<Param, Param> function) {
        return nested(true, function);
    }

    /**
     * 正常嵌套 不带 AND 或者 OR
     * <p>
     * 例: nested(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param condition 执行条件
     * @param function  消费函数
     * @return children
     */
    Children nested(boolean condition, Function<Param, Param> function);

    /**
     * ignore
     */
    default Children not(Function<Param, Param> function) {
        return not(true, function);
    }

    /**
     * not嵌套
     * <p>
     * 例: not(i -&gt; i.eq("name", "李白").ne("status", "活着"))
     * </p>
     *
     * @param condition 执行条件
     * @param function  消费函数
     * @return children
     */
    Children not(boolean condition, Function<Param, Param> function);
}
