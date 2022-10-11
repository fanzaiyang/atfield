package cn.fanzy.breeze.sqltoy.plus.conditions.segments;

import cn.fanzy.breeze.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.breeze.sqltoy.plus.conditions.toolkit.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderBySegmentList extends AbstractSegmentList{


    @Override
    public void merge() {
        int count = 0;
        List<ISqlSegment[]> mergeSegments = new ArrayList<>();
        List<Integer> deleteIndexes = new ArrayList<>();
        //合并前 -> ORDER BY id1 ASC ORDER BY id2 ASC ORDER BY id3 ASC ORDER BY id4 ASC
        for (int i = 0; i < sqlSegments.size(); i++) {
            ISqlSegment sqlSegment = sqlSegments.get(i);
            if (MatchSegment.ORDER_BY.match(sqlSegment)) {
                ISqlSegment[] iSqlSegments = new ISqlSegment[]{sqlSegments.get(i+1), sqlSegments.get(i+2)};
                //将多余orderBy替换成逗号
                if (count != 0) {
                    deleteIndexes.add(i);
                }
                deleteIndexes.add(i + 1);
                deleteIndexes.add(i + 2);
                mergeSegments.add(iSqlSegments);
                count++;
            }
        }
        if (count > 1) {
            deleteByIndex(deleteIndexes);
            //添加合并之后的orderBy子句
            //合并后 -> ORDER BY id1 ASC,id2 ASC,id3 ASC,id4 ASC
            super.add(() -> mergeSegments.stream().filter(Objects::nonNull).map(s -> s[0].getSqlSegment() + StringPool.SPACE + s[1].getSqlSegment()).collect(Collectors.joining(StringPool.COMMA)));
        }
    }


}
