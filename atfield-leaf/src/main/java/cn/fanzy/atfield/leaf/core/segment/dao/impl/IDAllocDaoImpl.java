package cn.fanzy.atfield.leaf.core.segment.dao.impl;

import cn.fanzy.atfield.leaf.core.segment.dao.IDAllocDao;
import cn.fanzy.atfield.leaf.core.segment.model.LeafAlloc;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.sagacity.sqltoy.model.MapKit;

import java.util.List;

/**
 * idalloc DAO impl
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
@RequiredArgsConstructor
public class IDAllocDaoImpl implements IDAllocDao {

    private final SqlToyRepository sqlToyRepository;


    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
        return sqlToyRepository.findBySql("""
                        SELECT biz_tag, max_id, step, update_time FROM leaf_alloc
                        """,
                MapKit.map(),
                LeafAlloc.class);
    }

    @Override
    public LeafAlloc getLeafAlloc(String tag) {
        List<LeafAlloc> leafAllocList = sqlToyRepository.findBySql(
                """
                        SELECT biz_tag, max_id, step FROM leaf_alloc WHERE biz_tag = :tag
                        """,
                MapKit.map("tag", tag)
                , LeafAlloc.class
        );
        if (CollUtil.isEmpty(leafAllocList)) {
            return null;
        }
        return leafAllocList.get(0);
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        sqlToyRepository.executeSql(
                """
                        UPDATE leaf_alloc SET max_id = max_id + step WHERE biz_tag = :tag
                        """,
                MapKit.map("tag", tag)
        );
        return getLeafAlloc(tag);
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        sqlToyRepository.executeSql("""
                        UPDATE leaf_alloc SET max_id = max_id + @value(:step) WHERE biz_tag = :key
                        """,
                MapKit.keys("step", "key")
                        .values(leafAlloc.getStep(), leafAlloc.getKey()));
        return getLeafAlloc(tag);
    }

    @Override
    public List<String> getAllTags() {
        List<LeafAlloc> allocList = sqlToyRepository.findBySql("""
                        SELECT distinct biz_tag FROM leaf_alloc
                        """,
                MapKit.map(),
                LeafAlloc.class);
        return allocList.stream().map(LeafAlloc::getBizTag).toList();
    }
}
