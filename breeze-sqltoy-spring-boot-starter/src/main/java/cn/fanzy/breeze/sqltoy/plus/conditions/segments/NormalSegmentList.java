package cn.fanzy.breeze.sqltoy.plus.conditions.segments;

import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.eumn.SqlKeyword;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class NormalSegmentList extends AbstractSegmentList {

    @Override
    public void addAll(List<ISqlSegment> iSqlSegments) {
        Assert.notNull(iSqlSegments, "sql segment can not be null");
        //自动追加and
        autoAddAnd(iSqlSegments);
        for (ISqlSegment iSqlSegment : iSqlSegments) {
            add(iSqlSegment);
        }
    }

    @Override
    public void merge() {
        mergeAndOrSegments(MatchSegment.AND_OR.getPredicate());
        filterFirst(MatchSegment.AND_OR.getPredicate());
        filterLast(MatchSegment.AND_OR.getPredicate());
    }

    private void autoAddAnd(List<ISqlSegment> iSqlSegments) {
        if (sqlSegments == null || sqlSegments.isEmpty()) {
            return;
        }
        ISqlSegment firstSegment = iSqlSegments.get(0);
        ISqlSegment lastValue = sqlSegments.get(sqlSegments.size() - 1);
        /* 只有 and() 以及 or() 以及 not() 会进入 */
        if (!MatchSegment.NOT.match(firstSegment) && !MatchSegment.AND_OR.match(firstSegment)) {
            boolean matchLastAndOrNot = MatchSegment.AND_OR.match(lastValue) || MatchSegment.NOT.match(lastValue);
            if (!matchLastAndOrNot) {
                super.add(SqlKeyword.AND);
            }
        }
    }

    /**
     * 剔除第一个
     * @param predicate Predicate
     */
    public void filterFirst(Predicate<ISqlSegment> predicate) {
        if (sqlSegments == null || sqlSegments.isEmpty()) {
            return;
        }
        ISqlSegment iSqlSegment = sqlSegments.get(0);
        if (predicate.test(iSqlSegment)) {
            sqlSegments.remove(0);
        }
    }

    /**
     * 剔除最后一个
     * @param predicate predicate
     */
    public void filterLast(Predicate<ISqlSegment> predicate) {
        if (sqlSegments == null || sqlSegments.isEmpty()) {
            return;
        }
        ISqlSegment iSqlSegment = sqlSegments.get(sqlSegments.size() - 1);
        if (predicate.test(iSqlSegment)) {
            sqlSegments.remove(sqlSegments.size() - 1);
        }
    }

    /**
     * 合并多个sql片段
     * @param predicate predicate
     */
    public void mergeAndOrSegments(Predicate<ISqlSegment> predicate) {
        if (sqlSegments == null || sqlSegments.isEmpty()) {
            return;
        }
        List<Integer> adjacentIndexes = new ArrayList<>();
        for (int i = 0; i < sqlSegments.size(); i++) {
            ISqlSegment sqlSegment = sqlSegments.get(i);
            if (predicate.test(sqlSegment)) {
                adjacentIndexes.add(i);
            }
        }
        List<List<Integer>> sameIndexes = new ArrayList<>();
        for (int i = 0; i < adjacentIndexes.size(); i++) {
            Integer index = adjacentIndexes.get(i);
            if (i < adjacentIndexes.size() - 1 && adjacentIndexes.get(i + 1) - index == 1) {
                int count = 1;
                List<Integer> sameIndex = new ArrayList<>();
                sameIndexes.add(sameIndex);
                sameIndex.add(adjacentIndexes.get(i));
                for (int j = i + 1; j < adjacentIndexes.size(); j++) {
                    if (adjacentIndexes.get(j) - index == count) {
                        sameIndex.add(adjacentIndexes.get(j));
                        count++;
                        i++;
                    } else {
                        break;
                    }
                }
            }
        }
        List<Integer> deleteIndexes = new ArrayList<>();
        for (List<Integer> sameIndex : sameIndexes) {
            if (sameIndex != null && !sameIndex.isEmpty()) {
                for (int i = 0; i < sameIndex.size(); i++) {
                    if (i < sameIndex.size() - 1) {
                        deleteIndexes.add(sameIndex.get(i));
                    }
                }
            }
        }
        //删除多余元素
        deleteByIndex(deleteIndexes);
    }
}
