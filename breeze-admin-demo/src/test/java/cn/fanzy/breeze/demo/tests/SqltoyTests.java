package cn.fanzy.breeze.demo.tests;

import cn.fanzy.breeze.admin.module.auth.entity.SysAccount;
import cn.fanzy.breeze.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.model.EntityQuery;
import org.sagacity.sqltoy.service.SqlToyCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SqltoyTests {
    @Autowired
    private SqlToyLazyDao sqlToyLazyDao;
    @Autowired
    private SqlToyCRUDService sqlToyCRUDService;
    @Test
    void hello() {
        List<User> userList = sqlToyLazyDao.findBySql("select * from user", new User());
        userList.forEach(System.out::println);
    }
    @Test
    void save() {
        List<SysAccount> accountList = sqlToyLazyDao.findEntity(SysAccount.class, EntityQuery.create()
                .where("username=:username or telnum=:telnum or workTelnum=:workTelnum")
                .names("username","telnum","workTelnum").values("Admin","admin","admin"));
        accountList.forEach(System.out::println);
    }
}
