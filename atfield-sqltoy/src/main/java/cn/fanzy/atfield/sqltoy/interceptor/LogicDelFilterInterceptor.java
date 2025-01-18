package cn.fanzy.atfield.sqltoy.interceptor;

import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.config.model.OperateType;
import org.sagacity.sqltoy.config.model.SqlToyConfig;
import org.sagacity.sqltoy.config.model.SqlToyResult;
import org.sagacity.sqltoy.plugins.SqlInterceptor;
import org.sagacity.sqltoy.utils.StringUtil;

/**
 * 逻辑删除拦截器
 *
 * @author fanzaiyang
 * @date 2024-01-16
 */
@Slf4j
@RequiredArgsConstructor
public class LogicDelFilterInterceptor implements SqlInterceptor {
    private final SqltoyExtraProperties properties;

    public SqlToyResult decorate(SqlToyContext sqlToyContext, SqlToyConfig sqlToyConfig, OperateType operateType, SqlToyResult sqlToyResult, Class entityClass, Integer dbType) {
        // 逻辑删除字段
        if (StringUtil.isBlank(properties.getLogicDeleteField()) ||
                StringUtil.isBlank(properties.getLogicDeleteField()) ||
                StringUtil.isBlank(properties.getLogicNotDeleteValue())) {
            log.debug("请配置spring.sqltoy.db-config.logicDeleteField、logicNotDeleteValue、logicTenantColumn");
            return sqlToyResult;
        }
        EntityMeta entityMeta = sqlToyContext.getEntityMeta(entityClass);
        if (entityMeta == null) {
            return sqlToyResult;
        }
        String logicDelColumn = entityMeta.getColumnName(properties.getLogicDeleteField());
        if (StringUtil.isBlank(logicDelColumn)) {
            log.warn("表{}不存在逻辑删除字段{}", entityMeta.getTableName(), properties.getLogicDeleteField());
            return sqlToyResult;
        }
        String sql = sqlToyResult.getSql();
        // 只处理查询类sql
//        if (!StrUtil.startWith(sql, "select", true)) {
//            return sqlToyResult;
//        }

//        logicDelColumn = ReservedWordsUtil.convertWord(logicDelColumn, dbType);
        int whereIndex = StringUtil.matchIndex(sql, "(?i)\\Wwhere\\W");
        // 如果存在where关键字且逻辑删除字段在where关键字后面
        if (whereIndex > 0 && StringUtil.matches(sql.substring(whereIndex), "(?i)\\W" + logicDelColumn + "(\\s*\\=|\\s+in)")) {
            return sqlToyResult;
        }

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            // 只处理查询类sql
            if (!(statement instanceof Select)) {
                return sqlToyResult;
            }
            Select selectStatement = (Select) statement;
            PlainSelect plainSelect = selectStatement.getPlainSelect();
            log.info("plainSelect Alias:{}", JSONUtil.toJsonStr(plainSelect.getAlias()));
            if (plainSelect.getAlias() != null) {
                log.info("plainSelect Alias name:{},UnquotedName:{}", plainSelect.getAlias().getName(),
                        plainSelect.getAlias().getUnquotedName());
            }
            Expression whereExpression = plainSelect.getWhere();
            // 逻辑删除条件
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new Column(logicDelColumn));
            equalsTo.setRightExpression(new StringValue(properties.getLogicNotDeleteValue()));
            if (whereExpression == null) {
                plainSelect.setWhere(equalsTo);
                sqlToyResult.setSql(plainSelect.toString());
                return sqlToyResult;
            }
            Expression newCondition = new AndExpression(equalsTo, whereExpression);
            plainSelect.setWhere(newCondition);
            sqlToyResult.setSql(plainSelect.toString());
            return sqlToyResult;
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }

    private String getConcatSql(String sql, String sqlPart) {
        if (StrUtil.containsIgnoreCase(sql, "where")) {
            String segment = sql.replaceFirst("(?i)\\swhere\\s", sqlPart + " (");
            if (StrUtil.containsIgnoreCase(segment, "group by")) {
                return segment.replaceFirst("(?i)\\sgroup\\sby\\s", ") group by ");
            }
            if (StrUtil.containsIgnoreCase(segment, "order by")) {
                return segment.replaceFirst("(?i)\\sorder\\sby\\s", ") order by ");
            }
            if (StrUtil.containsIgnoreCase(segment, "having")) {
                return segment.replaceFirst("(?i)\\shaving\\s", ") having ");
            }
            if (StrUtil.containsIgnoreCase(segment, "limit")) {
                return segment.replaceFirst("(?i)\\slimit\\s", ") limit ");
            }
            return segment + " ) ";
        }
        if (StrUtil.containsIgnoreCase(sql, "group by")) {
            return sql.replaceFirst("(?i)\\sgroup\\sby\\s", sqlPart + " 1=1 group by ");
        }
        if (StrUtil.containsIgnoreCase(sql, "order by")) {
            return sql.replaceFirst("(?i)\\sorder\\sby\\s", sqlPart + " 1=1 order by ");
        }
        if (StrUtil.containsIgnoreCase(sql, "having")) {
            return sql.replaceFirst("(?i)\\shaving\\s", sqlPart + " 1=1 having ");
        }
        if (StrUtil.containsIgnoreCase(sql, "limit")) {
            return sql.replaceFirst("(?i)\\slimit\\s", sqlPart + " 1=1 limit ");
        }
        return sql + sqlPart + " 1=1";
    }
}
