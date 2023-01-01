---  

title: 工具组件  
order: 6 

---

工具组件`breeze-core`是其他模块的基础依赖，包含常用工具方法等。

## Cache缓存

基于内存和Redis的缓存组件，开发者通过简单配置即可使用。

* 开启redis缓存
  
  在pom.xml文件中加入`spring-boot-starter-data-redis`或者`breeze-auth-redis`**「推荐」**即开启了redis缓存模式。
  
  ```xml
  <!-- pom.xml -->
  <dependency>
      <groupId>cn.fanzy.breeze</groupId>
      <artifactId>breeze-auth-redis</artifactId>
  </dependency>
  ```
  
  * 使用
    
    在需要的地方注入`BreezeCacheService`即可，该类提供了保存、获取、删除方法。
    
    ```java
    @Autowired
    private BreezeCacheService breezeCacheService;
    ```

## 节假日日历

组件集成了获取节假日的方法。节假日数据来自网络[接口坞](http://www.apihubs.cn/#/)。

> ⚠️ **频次限制**：
> 
> 接口目前频次限制为 100次/1分钟。

* 如何使用
  
  在任何位置使用`CalendarUtil`即可。

* 数据结构说明
  
  | 属性                | 数据类型   | 说明                                               |
  | ----------------- | ------ | ------------------------------------------------ |
  | year              | int    | 年，格式：yyyy                                        |
  | month             | int    | 月，格式：yyyyMM                                      |
  | date              | int    | 日，格式：yyyyMMdd                                    |
  | yearweek          | int    | 公历一年中的第几周                                        |
  | yearday           | int    | 公历一年中的第几天                                        |
  | lunarYear         | int    | 农历年                                              |
  | lunarMonth        | int    | 农历月                                              |
  | lunarDate         | int    | 农历日                                              |
  | lunarYearday      | int    | 农历一年中的第几天                                        |
  | week              | int    | 今天星期几                                            |
  | weekend           | int    | 是否为周末,1-周末,2-非周末                                 |
  | workday           | int    | 是否为工作日（包含调休在内需要上班的日子）                            |
  | holiday           | int    | 节假日，这里使用两位数字枚举表示节假日，其中特殊数字10表示非节假日，特殊数字99表示全部节假日 |
  | holidayOr         | int    | 其他节假日，枚举与节假日相同，表示同一天中的另一个节日，如 2020-10-01         |
  | holidayOvertime   | int    | 节假日调休，枚举与节假日相同                                   |
  | holidayToday      | int    | 是否为节日当天,1-节日当天,2-非节日当天                           |
  | holidayLegal      | int    | 是否为法定节假日（三倍工资）,1-法定节假日,2-非法定节假日                  |
  | holidayRecess     | int    | 是否为假期节假日（节日是否放假）,1-假期节假日,2-非假期节假日                |
  | yearCn            | String |                                                  |
  | monthCn           | String |                                                  |
  | dateCn            | String |                                                  |
  | yearweekCn        | String |                                                  |
  | yeardayCn         | String |                                                  |
  | lunarYearCn       | String |                                                  |
  | lunarMonthCn      | String |                                                  |
  | lunarDateCn       | String |                                                  |
  | lunarYeardayCn    | String |                                                  |
  | weekCn            | String |                                                  |
  | weekendCn         | String |                                                  |
  | workdayCn         | String |                                                  |
  | holidayCn         | String |                                                  |
  | holidayOrCn       | String |                                                  |
  | holidayOvertimeCn | String |                                                  |
  | holidayTodayCn    | String |                                                  |
  | holidayLegalCn    | String |                                                  |
  | holidayRecessCn   | String |                                                  |
  
  ## Html转图片
  
  日常开发中，可能需要把业务数据发送到某社交平台如企业微信等。此时，可以使用HTML转图片，方便直观的发送出去。

* 使用
  
  实例化`HtmlImageGenerator`即可使用。

* 关于字体
  
  框架默认读取`resource`目录下`font.setting`文件。该文件定义字体路径，格式：`font.path`字体路径可以是classpath地址，也可以是来自网络。
  
  ```properties
  font.path = http://网络字体tt
  font.path = classpath:font/test.ttf
  ```

## 本地存储

提供线程安全的本地存储工具类

* LocalScheduledStorage 
  
  带有过期时间的本地存储工具类

* LocalStorage
  
  全局存储工具该工具主要是一个基于内存的KV键值对存储工具。

* SessionStorage
  
  本地线程存储工具类。工具主要是一个基于内存和ThreadLocal的KV键值对存储工具，该键值对内容与当前线程关联，在使用时需要注意内存溢出问题。

## 其他工具类

见包`cn.fanzy.breeze.core.utils`下类。

| 工具类         | 说明                                           |
| ----------- | -------------------------------------------- |
| BetweenUtil | 比较工具工具类，用于比较一个给定的值是否在一个指定的范围内。               |
| CertNoUtil  | 身份证号校验工具。                                    |
| GpsUtil     | 根据两个坐标值计算出两个坐标点之间的距离。                        |
| HumpUtil    | 下划线与驼峰互转工具，该工具是一个线程安全类的工具。                   |
| NumberUtil  | 该工具主要是将给定的数字与0进行比较和将数字转换成boolean以及将字符串解析成数字。 |
| ObjUtil     | 集合对象判断工具，该工具主要用于判断集合里元素的数量。                  |
| RegexUtil   | 该工具主要是利用正则对字符串进行判断。                          |
| TreeUtils   | 继承Hutool的`TreeUtil`,增加默认转化方法。                |


