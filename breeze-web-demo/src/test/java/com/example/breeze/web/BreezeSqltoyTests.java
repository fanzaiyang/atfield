package com.example.breeze.web;

import com.example.breeze.web.entity.User;
import org.junit.jupiter.api.Test;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
class BreezeSqltoyTests {

    @Autowired
    private SqlToyLazyDao sqlToyLazyDao;

    /**
     * 测试查询findBySql
     */
    @Test
    void testQuery() {
        List<User> list = sqlToyLazyDao.findBySql("test", new User());
        list.forEach(System.out::println);
    }

    /**
     * 测试保存
     */
    @Test
    void testSave() {
        User user = new User();
        user.setName("用户AAA");
        user.setAge(30);
        user.setCreateTime(new Date());
        user.setCreateBy("SYS");
        Object save = sqlToyLazyDao.save(user);
        // 打印查看结果，返回主键ID
        System.out.println(save);
    }

    /**
     * 测试得到
     */
    @Test
    void testGet() {
        List<User> userList = sqlToyLazyDao.loadByIds(User.class, "1661308277350000001313");
        userList.forEach(System.out::println);
    }
    @Test
    void testDelete(){
        Long ids = sqlToyLazyDao.deleteByIds(User.class, "1661308277350000001313","1661308669488000001313");
        // 打印结果,受影响的行数。
        System.out.println(ids);
        String id="22365235959";
    }
}
