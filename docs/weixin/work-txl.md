---
title: 通讯录
order: 6
group:
  title: 企业微信
---
# 通讯录

## 成员管理

### 创建成员

> [创建成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90195)

```java
@Test
void createUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    // 参数请搭配官方文档使用
    WxCpUser user=new WxCpUser();
    user.setUserId("18363061520");
    wxCpUserService.create(user);
}
```

### 读取成员

> [读取成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90196)

```java
@Test
void getUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    WxCpUser user = wxCpUserService.getById("19953150727");
}
```

### 更新成员

> [更新成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90197)

```java
@Test
void updateUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    WxCpUser user=new WxCpUser();
    user.setUserId("18363061520");
    user.setName("新名字");
    wxCpUserService.update(user);
}
```

### 删除成员

> [删除成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90198)
>
> 仅通讯录同步助手或第三方通讯录应用可调用。
> 若是绑定了腾讯企业邮，则会同时删除邮箱帐号。

```java
@Test
void delUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    wxCpUserService.delete("19953150727");
}
```

### 批量删除

> [批量删除成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90199)

```java
@Test
void batchDelUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    wxCpUserService.delete("11","333","333");
}
```

### 获取部门成员

> [获取部门成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90200)
>
> 应用须拥有指定部门的查看权限。

```java
@Test
void listOrgSimpleUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    List<WxCpUser> userList = wxCpUserService.listSimpleByDepartment(1L, true, 0);
}
```

### 获取部门成员详情

> [获取部门成员详情 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90201)
>
> 应用须拥有指定部门的查看权限。

```java
@Test
void listOrgUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    List<WxCpUser> userList = wxCpUserService.listByDepartment(1L, true, 0);
}
```

### userid与openid互换

> [userid与openid互换 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90202)
>
> 该接口使用场景为企业支付，在使用企业红包和向员工付款时，需要自行将企业微信的userid转成openid。
>
> 注：需要成员使用微信登录企业微信或者关注微信插件（原企业号）才能转成openid;
> 如果是外部联系人，请使用[外部联系人openid转换](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90202#18820)转换openid

```java
@Test
void convertToOpenid() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    // userid转openid
    Map<String, String> openidMap = wxCpUserService.userId2Openid("199", 10068);
    // openid转userid
    wxCpUserService.openid2UserId("eeeeee");
```

### 二次验证

> [二次验证 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90203)
>
> 此接口可以满足安全性要求高的企业进行成员验证。开启二次验证后，当且仅当成员登录时，需跳转至企业自定义的页面进行验证。验证频率可在设置页面选择。

```java
@Test
void authsucc() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    // 用在二次验证的时候. 企业在员工验证成功后，调用本方法告诉企业号平台该员工关注成功。
    wxCpUserService.authenticate("userId");
}
```

### 邀请成员

> [邀请成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90975)
>
> 企业可通过接口批量邀请成员使用企业微信，邀请后将通过短信或邮件下发通知。

```java
@Test
void batchInvite() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    List<String> userIds=new ArrayList<>();
    List<String> partyIds=new ArrayList<>();
    List<String> tagIds=new ArrayList<>();
    WxCpInviteResult invite = wxCpUserService.invite(userIds, partyIds, tagIds);
}
```

### 获取加入企业二维码

> [获取加入企业二维码 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/91714)

```java
@Test
void get_join_qrcode() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    String qrCode = wxCpUserService.getJoinQrCode(1);
}
```

### 获取企业活跃成员数

> [获取企业活跃成员数 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/92714)

未实现

### 手机号获取userid

> [手机号获取userid - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/95402)
>
> 通过手机号获取其所对应的userid。

```java
@Test
void getuserid() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    // 获取用户服务类对象
    WxCpUserService wxCpUserService = wxCpService.getUserService();
    wxCpUserService.getUserId("手机号");
}
```

## 标签管理

### 创建标签

> [创建标签 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90210)

```java
@Test
void contextLoads() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    String tagId = tagService.create("标签名", 1);
}
```

### 更新标签名字

> [更新标签名字 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90211)

```java
@Test
void update() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    tagService.update("1","新名字");
}
```

### 删除标签

> [删除标签 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90212)

```java
@Test
void delete() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    tagService.delete("1");
}
```

### 获取标签成员

> [获取标签成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90213)

```
@Test
void getTagUser() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    List<WxCpUser> userList = tagService.listUsersByTagId("标签ID");
}
```

### 增加标签成员

> [增加标签成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90214)

```java
@Test
void addUserToTag() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    List<String> partyIds=new ArrayList<>();
    List<String> userIds=new ArrayList<>();
    WxCpTagAddOrRemoveUsersResult result = tagService.addUsers2Tag("标签ID",userIds,partyIds);
}
```

### 删除标签成员

> [删除标签成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90215)

```java
@Test
void delUserToTag() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    List<String> partyIds=new ArrayList<>();
    List<String> userIds=new ArrayList<>();
    WxCpTagAddOrRemoveUsersResult result = tagService.removeUsersFromTag("标签ID", userIds, partyIds);
}
```

### 获取标签列表

> [获取标签列表 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90216)

```java
@Test
void listTag() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    assert wxCpService != null;
    WxCpTagService tagService = wxCpService.getTagService();
    List<WxCpTag> tagList = tagService.listAll();
}
```

## 部门管理

### 创建部门

> [创建成员 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90195)

```java
@Test
void contextDepartment() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    // 获取部门相关接口的服务类对象
    WxCpDepartmentService departmentService = wxCpService.getDepartmentService();
    // 创建部门，具体参数，见官方文档
    WxCpDepart depart=new WxCpDepart();
    depart.setId(1L);
    depart.setName("测试");
    depart.setOrder(1L);
    // 最多支持创建500个部门
    departmentService.create(depart);
}
```

### 更新部门

> [更新部门 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90206)

```java
@Test
void updateDepartment() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    // 获取部门相关接口的服务类对象
    WxCpDepartmentService departmentService = wxCpService.getDepartmentService();
    // 创建部门，具体参数，见官方文档
    WxCpDepart depart=new WxCpDepart();
    depart.setId(1L);
    depart.setName("测试2");
    depart.setOrder(1L);
    departmentService.update(depart);
}
```

### 删除部门

> [删除部门 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90207)

```java
@Test
void delDepartment() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    // 获取部门相关接口的服务类对象
    WxCpDepartmentService departmentService = wxCpService.getDepartmentService();
   
    departmentService.delete(1L);
}
```

### 获取部门列表

> [获取部门列表 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90208)
>
> 注：该接口性能较低，建议换用[获取子部门ID列表](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90208#36259)与[获取单个部门详情](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/90208#36260)

```java
@Test
void queryDepartment() throws WxErrorException {
    // 获取服务类，默认获取第一个配置的应用
    WxCpService wxCpService = WxCpConfiguration.getCpService();
    // 获取部门相关接口的服务类对象
    WxCpDepartmentService departmentService = wxCpService.getDepartmentService();
    List<WxCpDepart> departList = departmentService.list(1L);
}
```

### 获取子部门ID列表

> [获取子部门ID列表 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/95350)

- 同“获取部门列表”

### 获取单个部门详情

> [获取单个部门详情 - 接口文档 - 企业微信开发者中心 (qq.com)](https://gitee.com/link?target=https://developer.work.weixin.qq.com/document/path/95351)
