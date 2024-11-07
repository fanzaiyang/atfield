package cn.fanzy.atfield.sqltoy.interceptor;

import cn.fanzy.atfield.sqltoy.property.SqltoyExtraProperties;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        logicDelColumn = ReservedWordsUtil.convertWord(logicDelColumn, dbType);
        int whereIndex = StringUtil.matchIndex(sql, "(?i)\\Wwhere\\W");
        // 如果存在where关键字且逻辑删除字段在where关键字后面
        if (whereIndex > 0 && StringUtil.matches(sql.substring(whereIndex), "(?i)\\W" + logicDelColumn + "(\\s*\\=|\\s+in)")) {
            return sqlToyResult;
        }

        String where = " where ";
        String sqlPart = where
                .concat(logicDelColumn).concat("='")
                .concat(properties.getLogicNotDeleteValue())
                .concat("' and ");
        if (operateType.equals(OperateType.load) ||
            operateType.equals(OperateType.loadAll) ||
            operateType.equals(OperateType.update) ||
            operateType.equals(OperateType.updateAll) ||
            operateType.equals(OperateType.delete) ||
            operateType.equals(OperateType.deleteAll) ||
            operateType.equals(OperateType.unique) ||
            operateType.equals(OperateType.saveOrUpdate) ||
            operateType.equals(OperateType.singleTable)) {

            if (operateType.equals(OperateType.saveOrUpdate) && sql.indexOf(" when matched then update set ") > 0) {
                int onTenantIndex = sql.indexOf(") tv on (");
                int end = onTenantIndex + ") tv on (".length();
                String aliasName = sql.substring(end, sql.indexOf(".", end)).trim();
                sqlPart = sqlPart
                        .replaceFirst(where, "").
                        replaceFirst(logicDelColumn, aliasName + "." + logicDelColumn);
                sql = sql.replaceFirst("\\)\\s+tv\\s+on\\s+\\(", ") tv on (".concat(sqlPart));
                sqlToyResult.setSql(sql);
            } else {
                sql = getConcatSql(sql, sqlPart);
                sqlToyResult.setSql(sql);
            }
        }

        return sqlToyResult;

    }

    private String getConcatSql(String sql, String sqlPart) {
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
}
