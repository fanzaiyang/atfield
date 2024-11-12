package cn.fanzy.atfield.leaf.gen;

import cn.fanzy.atfield.core.storage.LocalStorage;
import cn.fanzy.atfield.leaf.dao.IdGenDao;
import cn.fanzy.atfield.leaf.model.LeafAlloc;
import cn.fanzy.atfield.leaf.property.LeafIdProperty;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

import java.util.List;

/**
 * 段主键发电机
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
@Slf4j
@RequiredArgsConstructor
public class SegmentRedisIdGenerator implements RedisIdGenerator {
    private final IdGenDao dao;
    private final LeafIdProperty property;
    private final RedissonClient client;

    @Override
    public long nextId(String tag) {
        check(tag);
        return client.getAtomicLong(getCacheKey(tag)).incrementAndGet();
    }

    @Override
    public long previousId(String tag) {
        check(tag);
        return client.getAtomicLong(getCacheKey(tag)).decrementAndGet();
    }

    @Override
    public void init() {
        dao.createTable();
        updateCacheFromDb();
    }

    private void check(String tag) {
        LeafAlloc alloc = LocalStorage.get(getCacheKey(tag), LeafAlloc.class);
        if (alloc == null) {
            alloc = dao.getOrCreateLeafAlloc(tag);
            LocalStorage.put(getCacheKey(tag), alloc);
        }
    }

    private void updateCacheFromDb() {
        List<LeafAlloc> allocList = dao.queryLeafAllocList();
        if (CollUtil.isEmpty(allocList)) {
            return;
        }
        LocalStorage.clear();
        for (LeafAlloc alloc : allocList) {
            LocalStorage.put(getCacheKey(alloc.getBizTag()), alloc);
        }

        for (LeafAlloc alloc : allocList) {
            RAtomicLong atomicLong = client.getAtomicLong(getCacheKey(alloc.getBizTag()));
            long currentValue = atomicLong.get();
            if (currentValue < alloc.getMaxId()) {
                atomicLong.set(alloc.getMaxId());
            } else if (currentValue > alloc.getMaxId()) {
                dao.updateMaxId(alloc.getBizTag(), currentValue);
            }
        }
    }

    private String getCacheKey(String key) {
        return property.getCachePrefix() + key;
    }
}
