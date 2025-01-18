package cn.fanzy.atfield.sqltoy.interceptor;

import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.config.model.EntityMeta;
import org.sagacity.sqltoy.config.model.OperateType;
import org.sagacity.sqltoy.config.model.SqlToyConfig;
import org.sagacity.sqltoy.config.model.SqlToyResult;
import org.sagacity.sqltoy.plugins.SqlInterceptor;
import org.sagacity.sqltoy.utils.ReservedWordsUtil;
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
        String logicDelColumn = properties.getLogicDeleteField();

        // 实体类需要EntityMeta获取数据字段
        if (SqltoyExtraProperties.FieldTypeEnum.ENTITY.equals(properties.getFieldType())) {
            EntityMeta entityMeta = sqlToyContext.getEntityMeta(entityClass);
            if (!properties.isHumpToUnderline()) {
                if (entityMeta == null) {
                    return sqlToyResult;
                }
                logicDelColumn = entityMeta.getColumnName(properties.getLogicDeleteField());
                if (StringUtil.isBlank(logicDelColumn)) {
                    log.warn("表{}不存在逻辑删除字段{}", entityMeta.getTableName(), properties.getLogicDeleteField());
                    return sqlToyResult;
                }
            }
            logicDelColumn = StrUtil.toUnderlineCase(logicDelColumn);
        }
        String sql = sqlToyResult.getSql();
        int whereIndex = StringUtil.matchIndex(sql, "(?i)\\Wwhere\\W");
        // 如果存在where关键字且逻辑删除字段在where关键字后面
        if (whereIndex > 0 && StringUtil.matches(sql.substring(whereIndex), "(?i)\\W" + logicDelColumn + "(\\s*\\=|\\s+in)")) {
            return sqlToyResult;
        }
        logicDelColumn = ReservedWordsUtil.convertWord(logicDelColumn, dbType);

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            // 只处理查询类sql
            if (!(statement instanceof Select)) {
                return sqlToyResult;
            }
            Select selectStatement = (Select) statement;
            PlainSelect plainSelect = selectStatement.getPlainSelect();
            String tableAlias = "";
            FromItem fromItem = plainSelect.getFromItem();
            if (fromItem != null) {
                Alias alias = fromItem.getAlias();
                if (alias != null) {
                    tableAlias = alias.getName();
                }
            }
            Expression whereExpression = plainSelect.getWhere();
            // 逻辑删除条件
            EqualsTo equalsTo = new EqualsTo();
            Column column = new Column(logicDelColumn);
            if (StrUtil.isNotBlank(tableAlias)) {
                column.setTable(new Table(null, tableAlias));
            }
            equalsTo.setLeftExpression(column);
            equalsTo.setRightExpression(new StringValue(properties.getLogicNotDeleteValue()));
            if (whereExpression == null) {
                plainSelect.setWhere(equalsTo);
                sqlToyResult.setSql(plainSelect.toString());
                return sqlToyResult;
            }
            Expression newCondition = new AndExpression(equalsTo, new ParenthesedExpressionList(whereExpression));
            plainSelect.setWhere(newCondition);
            sqlToyResult.setSql(plainSelect.toString());
            return sqlToyResult;
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
}
