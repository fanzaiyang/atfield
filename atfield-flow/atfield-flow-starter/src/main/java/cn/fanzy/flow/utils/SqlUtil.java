package cn.fanzy.flow.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import jakarta.annotation.PostConstruct;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * SQL 常量
 *
 * @author fanzaiyang
 * @date 2024/03/11
 */
public class SqlUtil {

    public static Db db;


    public static boolean isTableExists(String tableName) {
        String sql = "SELECT * FROM information_schema.tables WHERE table_name = ?";
        try {
            List<Entity> query = db.query(sql, tableName);
            return CollUtil.isNotEmpty(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Db getDb() {
        if (db == null) {
            DataSource dataSource = SpringUtils.getBean(DataSource.class);
            db = Db.use(dataSource);
            return db;
        }
        return db;
    }

    @PostConstruct
    public void init() {
        DataSource dataSource = SpringUtils.getBean(DataSource.class);
        db = Db.use(dataSource);
    }
}
