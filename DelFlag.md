## 逻辑删除

### 配置文件说明

```yaml
spring:
  sqltoy:
    extra:
      logic-delete-field: delFlag # 逻辑删除字段名
      logic-delete-value: 1  # 逻辑删除后的值，delete-value-strategy为static时生效。
      logic-not-delete-value: 0 # 逻辑未删除的值
      delete-value-strategy: custom # 逻辑删除值策略，static表示静态值，custom表示使用自定义的逻辑删除值。
      field-type: entity # logic-delete-field的字段类型，entity表示实体类字段，database表示数据库字段。
      hump-to-underline: true # 当field-type=entity时，通过Entity未匹配到对应数据库字段名时，是否将驼峰命名的字段名转换为下划线命名作为数据库字段。
```

* `logic-delete-field`：逻辑删除字段名，默认值为`delFlag`。
* `logic-delete-value`：逻辑删除后的值，默认值为`1`,当`delete-value-strategy`为`static`时生效。
* `logic-not-delete-value`：逻辑未删除的值，默认值为`0`。
* `delete-value-strategy`：逻辑删除值策略，默认值为`static`。
    * `STATIC`：静态值，使用`logic-delete-value`和`logic-not-delete-value`配置的值。
    * `UUID`"：UUID，使用UUID作为逻辑删除值。
    * `UUID-SIMPLE`: 简单UUID，使用UUID的简单形式作为逻辑删除值。
    * `SNOWFLAKE`: SNOWFLAKE，使用SNOWFLAKE算法生成逻辑删除值。
    * `SNOWFLAKE_SIMPLE`: 简单SNOWFLAKE，使用SNOWFLAKE的简单形式生成逻辑删除值。
    * `CUSTOM`：自定义，使用自定义的逻辑删除值，需要实现
      `cn.fanzy.atfield.sqltoy.delflag.service.DeleteValueGenerateService`接口，并注入到Spring容器中。
* `field-type`：逻辑删除字段类型，默认值为`entity`。
    * `entity`：实体类字段。
    * `database`：数据库字段。
* `hump-to-underline`：当`field-type=entity`时，通过Entity未匹配到对应数据库字段名时，是否将驼峰命名的字段名转换为下划线命名作为数据库字段。默认值为
  `true`。

### 逻辑删除上下文

* `LogicDeleteContext`：逻辑删除上下文，用于临时修改逻辑删除配置项。
    * putDeleteField方法：临时修改逻辑删除字段为该字段值。
    * putDeleteValue方法：临时修改逻辑删除值。
    * putNotDeleteValue方法：临时修改逻辑未删除值。
    * putIgnoreValue方法：临时忽略逻辑删除,此次查询将不包含逻辑删除字段。该值为true时。
