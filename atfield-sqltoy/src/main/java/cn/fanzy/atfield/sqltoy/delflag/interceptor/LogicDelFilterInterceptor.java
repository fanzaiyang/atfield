package cn.fanzy.atfield.sqltoy.delflag.interceptor;

import cn.fanzy.atfield.sqltoy.delflag.context.DelFlagContext;
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

        String logicDelColumn = StrUtil.blankToDefault(properties.getLogicDeleteField(), DelFlagContext.getDeleteField());
        String logicNotDelValue = StrUtil.blankToDefault(properties.getLogicNotDeleteValue(), DelFlagContext.getNoDeleteValue());

        // 逻辑删除字段
        if (StringUtil.isBlank(logicDelColumn) || StringUtil.isBlank(logicNotDelValue)) {
            log.debug("请配置spring.sqltoy.extra.logicDeleteField、logicNotDeleteValue");
            return sqlToyResult;
        }
        // 是否忽略逻辑删除
        if (DelFlagContext.getIgnoreValue() != null && DelFlagContext.getIgnoreValue()) {
            return sqlToyResult;
        }

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
        // 处理查询sql
        String sql = sqlToyResult.getSql();

        try {
            Statement statement = CCJSqlParserUtil.parse(sql);
            // 只处理查询类sql
            if (!(statement instanceof Select selectStatement)) {
                return sqlToyResult;
            }

            // 逻辑删除字段转义
            logicDelColumn = ReservedWordsUtil.convertWord(logicDelColumn, dbType);

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
            // 表别名
            if (StrUtil.isNotBlank(tableAlias)) {
                column.setTable(new Table(null, tableAlias));
            }
            equalsTo.setLeftExpression(column);
            equalsTo.setRightExpression(new StringValue(logicNotDelValue));
            // 查询语句中不存在where条件
            if (whereExpression == null) {
                plainSelect.setWhere(equalsTo);
                sqlToyResult.setSql(plainSelect.toString());
                return sqlToyResult;
            }
            // 查询语句中存在where条件
            // 逻辑删除条件与where条件合并
            Expression newCondition = new AndExpression(equalsTo, new ParenthesedExpressionList<>(whereExpression));
            plainSelect.setWhere(newCondition);
            sqlToyResult.setSql(plainSelect.toString());
            return sqlToyResult;
        } catch (JSQLParserException e) {
            throw new RuntimeException(e);
        }
    }
}
