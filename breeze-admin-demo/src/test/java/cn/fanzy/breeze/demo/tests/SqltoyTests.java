package cn.fanzy.breeze.demo.tests;

import cn.fanzy.breeze.admin.module.entity.SysAccount;
import cn.fanzy.breeze.sqltoy.model.IBaseEntity;
import cn.fanzy.breeze.sqltoy.plus.conditions.Wrappers;
import cn.fanzy.breeze.sqltoy.plus.dao.SqlToyHelperDao;
import cn.fanzy.breeze.web.password.PasswordEncoder;
import cn.fanzy.breeze.web.password.bcrypt.BCryptPasswordEncoder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SqltoyTests {
    @Autowired
    private SqlToyHelperDao sqlToyHelperDao;

    @Test
    void hello() {
        sqlToyHelperDao.findList(Wrappers.lambdaWrapper(SysAccount.class)
                .isNull(IBaseEntity::getTenantId));
    }

    @Test
    void update() {
        PasswordEncoder encoder=new BCryptPasswordEncoder();
        sqlToyHelperDao.update(Wrappers.lambdaUpdateWrapper(SysAccount.class)
                .set(SysAccount::getPassword,encoder.encode("123456")));
    }
}
