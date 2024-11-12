package cn.fanzy.atfield.leaf.dao;

import cn.fanzy.atfield.leaf.model.LeafAlloc;

import java.util.List;

/**
 * 主键根道
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
public interface IdGenDao {
    /**
     * 创建表
     */
    void createTable();

    /**
     * 查询全部
     *
     * @return {@link List }<{@link LeafAlloc }>
     */
    List<LeafAlloc> queryLeafAllocList();

    /**
     * 获取叶分配
     *
     * @param tag 标记
     * @return {@link LeafAlloc }
     */
    LeafAlloc getLeafAlloc(String tag);

    /**
     * 获取或创建叶分配
     *
     * @param tag 标记
     * @return {@link LeafAlloc }
     */
    LeafAlloc getOrCreateLeafAlloc(String tag);

    /**
     * update麦克斯主键
     *
     * @param tag   标记
     * @param maxId 麦克斯主键
     */
    void updateMaxId(String tag, long maxId);

    /**
     * 创建 Leaf Alloc
     *
     * @param entity 实体
     */
    void createLeafAlloc(LeafAlloc entity);
}
