package cn.fanzy.atfield.sqltoy.utils;

import cn.fanzy.atfield.core.utils.IdUtils;
import org.sagacity.sqltoy.SqlToyConstants;
import org.sagacity.sqltoy.utils.IdUtil;

import java.math.BigDecimal;

/**
 * id 工作程序
 *
 * @author fanzaiyang
 * @date 2024/09/11
 */
public class IdWorker {

    /**
     * 生成22位不重复的ID
     * <p>
     * 格式:13位当前毫秒+6位纳秒+3位主机ID 构成的22位不重复的ID
     * </p>
     *
     * @param tableName 表名称
     * @return {@link String }
     */
    public static String getShortNanoTimeId(String tableName) {
        BigDecimal nanoTimeId = IdUtil.getShortNanoTimeId(tableName, SqlToyConstants.SERVER_ID);
        return nanoTimeId.toPlainString();
    }

    /**
     * 生成26位不重复的ID
     * <p>
     * 产生26位顺序id;15位:yyMMddHHmmssSSS+6位纳秒+2位(线程Id+随机数)+3位主机ID
     * </p>
     *
     * @param tableName 表名称
     * @return {@link String }
     */
    public static String getNanoTimeId(String tableName) {
        BigDecimal nanoTimeId = IdUtil.getNanoTimeId(tableName, SqlToyConstants.SERVER_ID);
        return nanoTimeId.toPlainString();
    }


    /**
     * Snowflake自增ID
     * <p>
     * 基于twitter的分布式自增ID生成策略
     * </p>
     *
     * @return {@link String }
     */
    public static String getSnowflakeId() {
        return IdUtils.getSnowflakeNextIdStr();
    }

    /**
     * 获取UUID
     *
     * @return {@link String }
     */
    public static String getUuidId() {
        return IdUtils.fastSimpleUUID();
    }
}
