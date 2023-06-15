---

title: 持久层
order: 10

---

## sqltoy

> Sqltoy是ORM框架，除具有JPA功能外，具有最佳的sql编写模式、独创的缓存翻译、最优化的分页、并提供分组汇总、同比环比、行列转换、树形排序汇总、多数据库适配

可以配合`mybatis-plus`一起食用，如何使用请查看[源码](https://gitee.com/sagacity/sagacity-sqltoy)。

[在线文档](https://www.kancloud.cn/hugoxue/sql_toy/2390352)

```xml
<dependency>
    <groupId>cn.fanzy.breeze</groupId>
    <artifactId>breeze-sqltoy-spring-boot-starter</artifactId>
</dependency>
```

### 逻辑删除

```yaml
breeze:
  sqltoy:
    logic-delete-field: del_flag #数据库字段
    logic-delete-value: 1 #已删除后值，删除策略是value时有效。
    logic-not-delete-value: 0 # 删除后的值
    delete-value-strategy: value #可选值：value-固定值,uuid-uuid，snowflake-雪花,nanoTime-nano时间

```

### 高级用法

```java
@Resource
private SqlToyHelperDao sqlToyHelperDao;
@Resource
private BreezeSqlToyProperties properties;

@Test
void findList() {
    // 跳过逻辑删除
    sqlToyHelperDao.findList(Wrappers.lambdaWrapper(AgentCategoryInfo.class).skipDeletion(true));
}

```


