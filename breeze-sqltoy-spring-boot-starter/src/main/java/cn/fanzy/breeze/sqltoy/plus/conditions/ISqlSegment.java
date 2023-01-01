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
package cn.fanzy.breeze.sqltoy.plus.conditions;


import java.io.Serializable;
import java.util.Map;

/**
 * SQL 片段接口
 *
 * @author fanzaiyang
 */
public interface ISqlSegment extends Serializable {

    /**
     * SQL 片段
     * @return string
     */
    String getSqlSegment();

    /**
     * SQL片段参数map
     * @return Map
     */
    default Map<String, Object> getSqlSegmentParamMap() {
        return null;
    }
}
