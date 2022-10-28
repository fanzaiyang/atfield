package cn.fanzy.breeze.sqltoy.plus.conditions.eumn;


import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.StringPool;

/**
 * @description: 条件枚举
 * @author: 高总辉
 * @create: 2021-12-30 15:48
 */
public enum CompareEnum {

    EQ(SqlKeyword.EQ) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + StringPool.COLON + paramName;
        }
    },

    NE(SqlKeyword.NE) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + StringPool.COLON + paramName;
        }
    },

    GT(SqlKeyword.GT) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + StringPool.COLON + paramName;
        }
    },

    LT(SqlKeyword.LT) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + StringPool.COLON + paramName;
        }
    },

    GE(SqlKeyword.GE) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + StringPool.COLON + paramName;
        }
    },

    LE(SqlKeyword.LE) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + StringPool.COLON + paramName;
        }
    },

    LIKE(SqlKeyword.LIKE) {
        public String getMetaSql(String paramName, String columnName) {
            //"code like concat('%', :code,'%')"
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + "concat(" + StringPool.PERCENT  + "," + StringPool.COLON + paramName+ "," + StringPool.PERCENT + ")";
        }
    },

    LIKE_RIGHT(SqlKeyword.LIKE) {
        public String getMetaSql(String paramName, String columnName) {
            //"code like concat(:code,'%')"
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + "concat(" + StringPool.COLON + paramName+ "," + StringPool.PERCENT + ")";
        }
    },

    LIKE_LEFT(SqlKeyword.LIKE) {
        //"code like concat('%', :code)"
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + "concat(" + StringPool.PERCENT  + "," + StringPool.COLON + paramName + ")";
        }
    },

    IN(SqlKeyword.IN) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + " (:" + paramName + ")";
        }
    },

    NOT_IN(SqlKeyword.NOT_IN) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol() + StringPool.SPACE + " (:" + paramName + ")";
        }
    },

    IS_NULL(SqlKeyword.IS_NULL) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol();
        }
    },

    IS_NOT_NULL(SqlKeyword.IS_NOT_NULL) {
        public String getMetaSql(String paramName, String columnName) {
            return columnName + StringPool.SPACE + getSymbol();
        }
    },

   ;

    SqlKeyword sqlKeyword;

    public abstract String getMetaSql(String paramName, String columnName);

    private CompareEnum( SqlKeyword sqlKeyword) {
        this.sqlKeyword = sqlKeyword;
    }

    public String getSymbol() {
        return sqlKeyword.getSqlSegment();
    }

    public SqlKeyword getSqlKeyword() {
        return sqlKeyword;
    }
}
