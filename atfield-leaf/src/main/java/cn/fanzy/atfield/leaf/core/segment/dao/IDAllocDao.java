package cn.fanzy.atfield.leaf.core.segment.dao;


import cn.fanzy.atfield.leaf.core.segment.model.LeafAlloc;

import java.util.List;

/**
 * idalloc DAO
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
public interface IDAllocDao {
    /**
     * 获取所有叶分配
     *
     * @return {@link List }<{@link LeafAlloc }>
     */
    List<LeafAlloc> getAllLeafAllocs();

    /**
     * 获取叶分配
     *
     * @param tag 标记
     * @return {@link LeafAlloc }
     */
    LeafAlloc getLeafAlloc(String tag);

    /**
     * 更新麦克斯主键和获取叶alloc （分配）
     *
     * @param tag 标记
     * @return {@link LeafAlloc }
     */
    LeafAlloc updateMaxIdAndGetLeafAlloc(String tag);

    /**
     * 更新麦克斯主键由习惯步和获取叶alloc （分配）
     *
     * @param leafAlloc 叶子分配
     * @return {@link LeafAlloc }
     */
    LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc);

    /**
     * 获取所有标签
     *
     * @return {@link List }<{@link String }>
     */
    List<String> getAllTags();

    void createOrUpdateTable();
}
