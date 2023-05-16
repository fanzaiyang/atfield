package cn.fanzy.breeze.sqltoy.plus.conditions.segments;

import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.StringPool;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象部分列表
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public abstract class AbstractSegmentList implements ISqlSegment {

    private static final long serialVersionUID = 8309103623436823734L;
    protected List<ISqlSegment> sqlSegments = new ArrayList<>();

    /**
     * 合并
     */
    public abstract void merge();

    public void add(ISqlSegment iSqlSegment) {
        if (iSqlSegment != null) {
            sqlSegments.add(iSqlSegment);
        }
    }

    public void addAll(List<ISqlSegment> iSqlSegments) {
        Assert.notNull(iSqlSegments, "sql segment can not be null");
        for (ISqlSegment iSqlSegment : iSqlSegments) {
            add(iSqlSegment);
        }
    }

    @Override
    public String getSqlSegment() {
        if (sqlSegments == null || sqlSegments.isEmpty()) {
            return StringPool.EMPTY;
        }
        //合并sql片段
        merge();
        return sqlSegments.stream().filter(Objects::nonNull).map(ISqlSegment::getSqlSegment).collect(Collectors.joining(StringPool.SPACE)) + StringPool.SPACE;
    }

    @Override
    public Map<String, Object> getSqlSegmentParamMap() {
        Map<String, Object> allMap = new HashMap<>();
        sqlSegments.forEach((k) -> {
            if (k.getSqlSegmentParamMap() != null) {
                allMap.putAll(k.getSqlSegmentParamMap());
            }
        });
        return allMap;
    }

    public void deleteByIndex(List<Integer> deleteIndexes) {
        if (deleteIndexes != null && !deleteIndexes.isEmpty()) {
            Iterator<ISqlSegment> sqlSegmentIterator = sqlSegments.iterator();
            int count = 0;
            while (sqlSegmentIterator.hasNext()) {
                sqlSegmentIterator.next();
                if (deleteIndexes.contains(count)) {
                    sqlSegmentIterator.remove();
                }
                count++;
            }
        }
    }

    public boolean isEmpty() {
        return sqlSegments == null || sqlSegments.isEmpty();
    }
}
