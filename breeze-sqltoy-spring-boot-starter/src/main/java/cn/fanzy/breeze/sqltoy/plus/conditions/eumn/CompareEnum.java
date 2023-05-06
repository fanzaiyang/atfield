package cn.fanzy.breeze.sqltoy.plus.conditions.eumn;

import org.sagacity.sqltoy.exception.DataAccessException;

import java.util.Collection;

import static java.util.stream.Collectors.joining;


/**
 * 比较枚举
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public enum CompareEnum {
    /**
     * ==
     */
    EQ(SqlKeyword.EQ, CompareConstant.EQ_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * !=
     */
    NE(SqlKeyword.NE, CompareConstant.NE_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * >
     */
    GT(SqlKeyword.GT, CompareConstant.GT_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * <
     */
    LT(SqlKeyword.LT, CompareConstant.LT_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * >=
     */
    GE(SqlKeyword.GE, CompareConstant.GE_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * <=
     */
    LE(SqlKeyword.LE, CompareConstant.LE_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * LIKE
     */
    LIKE(SqlKeyword.LIKE, CompareConstant.LIKE_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * 做模糊 *str
     */
    LIKE_LEFT(SqlKeyword.LIKE, CompareConstant.LIKE_LEFT_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * 右模糊
     */
    LIKE_RIGHT(SqlKeyword.LIKE, CompareConstant.LIKE_RIGHT_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * NOT_LIKE
     */
    NOT_LIKE(SqlKeyword.NOT_LIKE, CompareConstant.NOT_LIKE_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * IN
     */
    IN(SqlKeyword.IN, CompareConstant.IN_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },


    /**
     * 多字段in语句
     */
    IN_BATCH(SqlKeyword.IN, CompareConstant.IN_BATCH_SQL) {
        public String getMetaSql(String... names) {
            throw new DataAccessException("多字段in操作不支持该数据类型！");
        }

        public String getBatchMetaSql(Collection<String> paramNames, Collection<String> columnNames) {
            String head = String.join(",", columnNames);
            String end = paramNames.stream().map(e -> ":" + e).collect(joining(","));
            return String.format(getSqlContent(), head, end);
        }
    },

    /**
     * NOT_IN
     */
    NOT_IN(SqlKeyword.NOT_IN, CompareConstant.NOT_IN_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1]);
        }
    },

    /**
     * IS_NULL
     */
    IS_NULL(SqlKeyword.IS_NULL, CompareConstant.IS_NULL_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0]);
        }
    },

    /**
     * IS_NOT_NULL
     */
    IS_NOT_NULL(SqlKeyword.IS_NOT_NULL, CompareConstant.IS_NOT_NULL_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0]);
        }
    },

    /**
     * BETWEEN
     */
    BETWEEN(SqlKeyword.BETWEEN, CompareConstant.BETWEEN_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1], names[2]);
        }
    },

    /**
     * NOT_BETWEEN
     */
    NOT_BETWEEN(SqlKeyword.NOT_BETWEEN, CompareConstant.NOT_BETWEEN_SQL) {
        public String getMetaSql(String... names) {
            return String.format(getSqlContent(), names[0], names[1], names[2]);
        }
    },
    ;

    /**
     * sql关键字
     */
    private final SqlKeyword sqlKeyword;

    /**
     * sql内容
     */
    private final String sqlContent;

    public abstract String getMetaSql(String... names);

    public String getBatchMetaSql(Collection<String> paramNames, Collection<String> columnNames) {
        throw new DataAccessException("该操作类型不支持此种sql组装方式！");
    }

    CompareEnum(SqlKeyword sqlKeyword, String sqlContent) {
        this.sqlKeyword = sqlKeyword;
        this.sqlContent = sqlContent;
    }

    public String getSymbol() {
        return sqlKeyword.getSqlSegment();
    }

    public String getSqlContent() {
        return sqlContent;
    }

    public SqlKeyword getSqlKeyword() {
        return sqlKeyword;
    }
}
