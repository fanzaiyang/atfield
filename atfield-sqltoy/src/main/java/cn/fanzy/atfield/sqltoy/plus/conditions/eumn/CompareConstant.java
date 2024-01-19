package cn.fanzy.atfield.sqltoy.plus.conditions.eumn;

/**
 * 比较恒定
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public class CompareConstant {
    public static final String EQ_SQL = "%s = :%s";

    public static final String NE_SQL = "%s <> :%s";

    public static final String GT_SQL = "%s > :%s";

    public static final String LT_SQL = "%s < :%s";

    public static final String GE_SQL = "%s >= :%s";

    public static final String LE_SQL = "%s <= :%s";

    /**
     * %需要转义处理,适配Oracle加双CONCAT
     */
    public static final String LIKE_SQL = "%s LIKE CONCAT('%%',CONCAT(:%s,'%%'))";

    public static final String LIKE_LEFT_SQL = "%s LIKE CONCAT('%%',:%s)";

    public static final String LIKE_RIGHT_SQL = "%s LIKE CONCAT(:%s,'%%')";

    public static final String NOT_LIKE_SQL = "%s NOT LIKE CONCAT('%%',CONCAT(:%s,'%%'))";

    public static final String IN_SQL = "%s IN (:%s)";

    public static final String NOT_IN_SQL = "%s NOT IN (:%s)";

    public static final String IN_BATCH_SQL = "(%s) IN (%s)";

    public static final String IS_NULL_SQL = "%s IS NULL";

    public static final String IS_NOT_NULL_SQL = "%s IS NOT NULL";

    public static final String BETWEEN_SQL = "%s BETWEEN :%s AND :%s";

    public static final String NOT_BETWEEN_SQL = "%s NOT BETWEEN :%s AND :%s";
    public static final String SKIP_SQL = "SKIP";
}
