package cn.fanzy.breeze.demo.tests;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.demo.entity.User;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.dao.SqlToyLazyDao;
import org.sagacity.sqltoy.service.SqlToyCRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SqltoyTests {
    @Autowired
    private SqlToyLazyDao sqlToyLazyDao;
    @Autowired
    private SqlToyCRUDService sqlToyCRUDService;
    @Autowired
    private SqlToyHelperDao sqlToyHelperDao;

    @Test
    void hello() {
        List<User> userList = sqlToyLazyDao.findBySql("select * from user", new User());
        userList.forEach(item -> {
            System.out.println(JSONUtil.toJsonStr(item, JSONConfig.create().setDateFormat("yyyy-MM-dd HH:mm:ss")));
        });
    }

    @Test
    void save1() {
        SqlToyContext context = new SqlToyContext();
        List<User> saveList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            saveList.add(User.builder()
                    .code(RandomUtil.randomNumbers(4))
                    .name(RandomUtil.randomString(6))
                    .age(RandomUtil.randomInt(1, 100))
                    .build());
        }
        sqlToyCRUDService.saveAll(saveList);
    }
    @Test
    void save2() {
        List<SysAccount> saveList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            saveList.add(SysAccount.builder()
                            .username(RandomUtil.randomString(5))
                            .nickName(RandomUtil.randomString(8))
                    .build());
        }
        sqlToyCRUDService.saveAll(saveList);
    }
    @Test
    void update() {
        User user = sqlToyCRUDService.load(User.builder().id("1665541650741000001283").build());
        user.setName("修改");
        sqlToyCRUDService.update(user);
    }
}
