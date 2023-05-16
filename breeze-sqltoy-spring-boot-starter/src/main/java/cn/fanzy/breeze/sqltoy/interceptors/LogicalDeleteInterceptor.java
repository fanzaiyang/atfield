package cn.fanzy.breeze.sqltoy.interceptors;

import cn.fanzy.breeze.sqltoy.properties.BreezeSqlToyProperties;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.SqlToyContext;
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
 * @since 2023-03-16
 */
@Slf4j
@RequiredArgsConstructor
public class LogicalDeleteInterceptor implements SqlInterceptor {

    private static final String REG_CONDITION = "\\s+where\\s+.*?(\\s+order\\s+|\\s+group\\s+)|\\s+where\\s+.+";
    private static final String REG_REPLACEMENT = "\\s+where\\s+|\\s+order\\s+|\\s+group\\s+|\\[|\\]";

    @Override
    public SqlToyResult decorate(SqlToyContext sqlToyContext, SqlToyConfig sqlToyConfig, OperateType operateType,
                                 SqlToyResult sqlToyResult, Class entityClass, Integer dbType) {
        BreezeSqlToyProperties properties = SpringUtil.getBean(BreezeSqlToyProperties.class);
        if (properties.isSkip()) {
            log.warn("用户选择跳过逻辑删除！");
            return sqlToyResult;
        }
        if(properties.getSkipSqlMode()){
            if(operateType.equals(OperateType.count) || operateType.equals(OperateType.search) ||
                    operateType.equals(OperateType.page) || operateType.equals(OperateType.top) ||
                    operateType.equals(OperateType.random)){
                return sqlToyResult;
            }
        }
        String logicDeleteField = properties.getAlias() + properties.getLogicDeleteField();
        String logicDeleteValue = properties.getLogicDeleteValue();
        String logicNotDeleteValue = properties.getLogicNotDeleteValue();
        if (StrUtil.isBlank(logicDeleteField) && StrUtil.isBlank(logicDeleteValue) && StrUtil.isBlank(logicNotDeleteValue)) {
            return sqlToyResult;
        }
        String sql = sqlToyResult.getSql();
        // 租户字段
        String deleteColumn = logicDeleteField;
        // 保留字处理(实际不会出现保留字用作逻辑删)
        deleteColumn = ReservedWordsUtil.convertWord(deleteColumn, dbType);

        // sql 在where后面已经有逻辑删除条件过滤，无需做处理
        int matchIndex = StringUtil.matchIndex(sql, "(?i)\\Wwhere\\W");

        int groupIndex = StringUtil.matchLastIndex(sql, "(?i)\\Wgroup by\\W");
        if (groupIndex < 0 || groupIndex < matchIndex) {
            groupIndex = StringUtil.matchLastIndex(sql, "(?i)\\Whaving\\W");
        }
        if (groupIndex < 0 || groupIndex < matchIndex) {
            groupIndex = StringUtil.matchLastIndex(sql, "(?i)\\Worder by\\W");
        }
        if (groupIndex < 0 || groupIndex < matchIndex) {
            groupIndex = StringUtil.matchLastIndex(sql, "(?i)\\Wlimit\\W");
        }

        if (matchIndex >= 0) {
            if (StringUtil.matches(sql.substring(matchIndex),
                    "(?i)\\W" + deleteColumn + "(\\s*\\=|\\s+in)")) {
                return sqlToyResult;
            }
        }
        String logicSql = StrUtil.format(" where {} = '{}'  ", deleteColumn, logicNotDeleteValue);
        // 所有基于对象操作和查询、更新操作进行租户过滤
        if (operateType.equals(OperateType.load) || operateType.equals(OperateType.loadAll)
                || operateType.equals(OperateType.update) || operateType.equals(OperateType.updateAll)
                || operateType.equals(OperateType.unique) || operateType.equals(OperateType.saveOrUpdate)
                || operateType.equals(OperateType.singleTable) ||
                operateType.equals(OperateType.count) || operateType.equals(OperateType.search) ||
                operateType.equals(OperateType.page) || operateType.equals(OperateType.top) ||
                operateType.equals(OperateType.random)) {
            // 从where开始替换，避免select a,b from table where id=? for update 场景拼接在最后面是有错误的
            // 对象操作sql由框架生成，where前后是空白
            if (matchIndex < 0 && groupIndex < 0) {
                sqlToyResult.setSql(sql.concat(logicSql));
                return sqlToyResult;
            }
            if (matchIndex < 0 && groupIndex > 0) {
                sql = sql.substring(0, groupIndex).concat(logicSql).concat(sql.substring(groupIndex + 1));
                sqlToyResult.setSql(sql);
                return sqlToyResult;
            }
            // 1. 不存在group,order,limit
            if (groupIndex < 0 || groupIndex < matchIndex) {
                sqlToyResult.setSql(sql.replaceFirst("(?i)\\swhere\\s", logicSql.concat(" and ( ")).concat(" ) "));
                return sqlToyResult;
            }
            sql = sql.substring(0, groupIndex).concat(" ) ").concat(sql.substring(groupIndex + 1));
            sqlToyResult.setSql(sql.replaceFirst("(?i)\\swhere\\s", logicSql.concat(" and ( ")));
        }
        return sqlToyResult;
    }
}
