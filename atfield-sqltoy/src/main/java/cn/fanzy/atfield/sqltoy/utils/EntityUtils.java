package cn.fanzy.atfield.sqltoy.utils;

import cn.fanzy.atfield.core.spring.SpringUtils;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.EntityManager;

/**
 * 实体实用程序
 *
 * @author fanzaiyang
 * @date 2024/09/30
 */
public class EntityUtils {

    public static void test() {
        SqlToyContext bean = SpringUtils.getBean(SqlToyContext.class);
        EntityManager entityManager = bean.getEntityManager();
        entityManager.getAllEntities();
    }
}
