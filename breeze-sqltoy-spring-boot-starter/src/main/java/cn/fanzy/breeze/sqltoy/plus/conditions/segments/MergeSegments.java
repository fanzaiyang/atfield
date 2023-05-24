package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.eumn.SqlKeyword;
import cn.hutool.core.util.StrUtil;

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

    @Override
    public String getSqlSegment() {
        String sql = "";
        if (normal.isEmpty()) {
            if (!groupBy.isEmpty() || !orderBy.isEmpty()) {
                sql =  "1 = 1 "+groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
            }
        } else {
            sql = normal.getSqlSegment() + groupBy.getSqlSegment() + having.getSqlSegment() + orderBy.getSqlSegment();
        }
        return sql + lastSqlSegment;
    }

    @Override
    public Map<String, Object> getSqlSegmentParamMap() {
        return Stream.of(normal.getSqlSegmentParamMap(), groupBy.getSqlSegmentParamMap(), having.getSqlSegmentParamMap(), orderBy.getSqlSegmentParamMap())
                .filter(Objects::nonNull).flatMap(x -> x.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p1));
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
        } else if (firstSqlSegment!=null&&StrUtil.startWith(firstSqlSegment.getSqlSegment(),SqlKeyword.LAST.name())) {
            lastSqlSegment = firstSqlSegment.getSqlSegment().replace(SqlKeyword.LAST.name() + ":", " ");
        } else {
            normal.addAll(list);
        }
    }
}
