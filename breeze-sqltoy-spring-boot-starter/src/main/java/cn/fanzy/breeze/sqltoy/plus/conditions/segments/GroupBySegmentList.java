package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GroupBySegmentList extends AbstractSegmentList{

    @Override
    public void merge() {
        List<ISqlSegment> mergeSqlSegments = new ArrayList<>();
        List<Integer> deleteIndexes = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < sqlSegments.size(); i++) {
            ISqlSegment sqlSegment = sqlSegments.get(i);
            if (MatchSegment.GROUP_BY.match(sqlSegment)) {
                //第一个GroupBy不删除
                if (count != 0) {
                    deleteIndexes.add(i);
                }
                count++;
                deleteIndexes.add(i + 1);
                mergeSqlSegments.add(sqlSegments.get(i + 1));
            }
        }
        if (count > 1) {
            //删除多余元素
            deleteByIndex(deleteIndexes);
            //合并
            super.add(() -> mergeSqlSegments.stream().filter(Objects::nonNull).map(ISqlSegment::getSqlSegment).collect(Collectors.joining(StringPool.COMMA)));
        }
    }
}
