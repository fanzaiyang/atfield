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
    public long nextId(String key) {
        check(key);
        return client.getAtomicLong(key).incrementAndGet();
    }

    @Override
    public long previousId(String key) {
        check(key);
        return client.getAtomicLong(key).decrementAndGet();
    }

    @Override
    public void init() {
        dao.createTable();
        updateCacheFromDb();
    }

    private void check(String tag) {
        LeafAlloc alloc = LocalStorage.get(property.getCachePrefix() + tag, LeafAlloc.class);
        if (alloc == null) {
            alloc = dao.getOrCreateLeafAlloc(property.getCachePrefix() + tag);
            LocalStorage.put(property.getCachePrefix() + tag, alloc);
        }
    }

    private void updateCacheFromDb() {
        List<LeafAlloc> allocList = dao.queryLeafAllocList();
        if (CollUtil.isEmpty(allocList)) {
            return;
        }
        LocalStorage.clear();
        for (LeafAlloc alloc : allocList) {
            LocalStorage.put(property.getCachePrefix() + alloc.getBizTag(), alloc);
        }

        for (LeafAlloc alloc : allocList) {
            RAtomicLong atomicLong = client.getAtomicLong(property.getCachePrefix() + alloc.getBizTag());
            long currentValue = atomicLong.get();
            if (currentValue < alloc.getMaxId()) {
                atomicLong.set(alloc.getMaxId());
            } else if (currentValue > alloc.getMaxId()) {
                dao.updateMaxId(alloc.getBizTag(), currentValue);
            }
        }
    }
}
