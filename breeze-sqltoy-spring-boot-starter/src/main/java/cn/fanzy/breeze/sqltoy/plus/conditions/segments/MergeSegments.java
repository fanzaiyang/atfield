package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.eumn.SqlKeyword;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 合并片段
 * 合并sql片段
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public class MergeSegments implements ISqlSegment {

    private static final long serialVersionUID = 845669770756233366L;
    private final AbstractSegmentList normal = new NormalSegmentList();
    private final AbstractSegmentList groupBy = new GroupBySegmentList();
    private final AbstractSegmentList having = new HavingSegmentList();
    private final AbstractSegmentList orderBy = new OrderBySegmentList();

    private String lastSqlSegment = "";
    private boolean skip;

    @Override
    public String getSqlSegment() {
        String logicDeleteField = SpringUtil.getProperty("breeze.sqltoy.logic-delete-field");
        String logicNotDeleteValue = SpringUtil.getProperty("breeze.sqltoy.logic-not-delete-value");
        String sql = "";
        if (normal.isEmpty()) {
            if (!groupBy.isEmpty() || !orderBy.isEmpty()) {
                if (getEnableLogic(logicDeleteField, logicNotDeleteValue)) {
                    sql = StrUtil.format("{}='{}' ", logicDeleteField, logicNotDeleteValue);
                } else {
                    sql = "1 = 1 ";
                }
                sql += groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
            }
        } else {
            if (getEnableLogic(logicDeleteField, logicNotDeleteValue)) {
                sql = StrUtil.format("{}='{}' AND ", logicDeleteField, logicNotDeleteValue);
            }
            sql += "(" + normal.getSqlSegment() + ") " + groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
        }
        return sql + lastSqlSegment;
    }

    @Override
    public Map<String, Object> getSqlSegmentParamMap() {
        return Stream.of(normal.getSqlSegmentParamMap(), groupBy.getSqlSegmentParamMap(), having.getSqlSegmentParamMap(), orderBy.getSqlSegmentParamMap())
                .filter(Objects::nonNull)
                .flatMap(x -> x.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p1));
    }

    public void add(ISqlSegment... iSqlSegments) {
        List<ISqlSegment> list = Arrays.asList(iSqlSegments);
        ISqlSegment firstSqlSegment = list.get(0);
        if (MatchSegment.ORDER_BY.match(firstSqlSegment)) {
            orderBy.addAll(list);
        } else if (MatchSegment.GROUP_BY.match(firstSqlSegment)) {
            groupBy.addAll(list);
        } else if (MatchSegment.HAVING.match(firstSqlSegment)) {
            having.addAll(list);
        } else if (firstSqlSegment != null && StrUtil.startWith(firstSqlSegment.getSqlSegment(), SqlKeyword.LAST.name())) {
            lastSqlSegment = firstSqlSegment.getSqlSegment().replace(SqlKeyword.LAST.name() + ":", " ");
        } else if (firstSqlSegment != null && StrUtil.equalsIgnoreCase(firstSqlSegment.getSqlSegment(), MatchSegment.SKIP.name())) {
            skip = true;
        } else {
            normal.addAll(list);
        }
    }

    public boolean getEnableLogic(String logicDeleteField, String logicNotDeleteValue) {
        if (skip) {
            return false;
        }
        return StrUtil.isNotBlank(logicDeleteField) && StrUtil.isNotBlank(logicNotDeleteValue);
    }
}
