package cn.fanzy.atfield.sqltoy.plus.conditions.segments;





import cn.fanzy.atfield.sqltoy.plus.conditions.ISqlSegment;
import cn.fanzy.atfield.sqltoy.plus.conditions.toolkit.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 集团根据段列表
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public class GroupBySegmentList extends AbstractSegmentList{

    private static final long serialVersionUID = -6316805670741381215L;

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
