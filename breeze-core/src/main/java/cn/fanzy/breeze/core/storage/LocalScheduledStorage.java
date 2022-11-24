package cn.fanzy.breeze.core.storage;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 全局存储工具该工具主要是一个基于内存的KV键值对存储工具。 <strong>线程安全的</strong>
 *
 * @author fanzaiyang
 * @date 2022-08-16
 */
@Slf4j
public final class LocalScheduledStorage {
    private static ScheduledExecutorService swapExpiredPool = new ScheduledThreadPoolExecutor(10);
    /**
     * 缓存
     */
    private static Map<String, Node> cacheMap = new ConcurrentHashMap<>(1024);

    private ReentrantLock lock = new ReentrantLock();
    /**
     * 让过期时间最小的数据排在队列前，在清除过期数据时
     * ，只需查看缓存最近的过期数据，而不用扫描全部缓存
     */
    private PriorityQueue<Node> expireQueue = new PriorityQueue<>(1024);


    public LocalScheduledStorage() {
        //使用默认的线程池，每x秒清除一次过期数据
        //线程池和调用频率 最好是交给调用者去设置。
        swapExpiredPool.scheduleWithFixedDelay(new SwapExpiredNodeWork(), 60, 60, TimeUnit.SECONDS);
    }

    /**
     * 根据键获取存储的值
     *
     * @param key 存储的键
     * @return 存储的值
     */
    public synchronized static Object get(String key) {
        Assert.notNull(key, "key值不能为空");
        Node node = cacheMap.get(key);
        Assert.notNull(node, "未找到键为「{}」的内容！", key);
        if (DateUtil.currentSeconds() > node.getExpireTime()) {
            remove(key);
            return null;
        }
        return node.getValue();
    }

    /**
     * 根据存储的键获取存储的数据，然后清楚当前存储的数据
     *
     * @param key 存储的键
     * @return 存储的数据的类型
     */
    public synchronized static Object pop(String key) {
        try {
            return get(key);
        } finally {
            clear();
        }
    }

    /**
     * 向本地线程副本里存储信息
     *
     * @param key          信息的键
     * @param value        信息的值
     * @param expireSecond 过期时间，单位：秒，大于0。
     */
    public synchronized static void put(String key, Object value, int expireSecond) {
        Assert.notNull(key, "key值不能为空");
        cacheMap.put(key, Node.builder()
                .key(key).value(value)
                .expireTime(DateUtil.currentSeconds() + expireSecond)
                .build());
    }

    /**
     * 移除本地线程副本里存储信息的某个值
     *
     * @param key 信息的键
     */
    public synchronized static void remove(String key) {
        Assert.notNull(key, "key值不能为空");
        cacheMap.remove(key);
    }

    /**
     * 清空本地线程副本
     */
    public synchronized static void clear() {
        cacheMap.clear();
    }

    /**
     * 获取本地线程副本里的键值对集合
     *
     * @return 本地线程副本里的键值对集合
     */
    public static Map<String, Node> getAll() {

        return cacheMap;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Node implements Comparable<Node> {
        /**
         * 键
         */
        private String key;
        /**
         * 值
         */
        private Object value;
        /**
         * 到期时间
         */
        private long expireTime;

        @Override
        public int compareTo(Node o) {

            long r = this.expireTime - o.expireTime;
            return r > 0 ? 1 : r < 0 ? -1 : 0;
        }
    }

    private class SwapExpiredNodeWork implements Runnable {

        @Override
        public void run() {
            long now = DateUtil.currentSeconds();
            while (true) {
                lock.lock();
                try {
                    Node node = expireQueue.peek();
                    //没有数据了，或者数据都是没有过期的了
                    if (node == null || node.expireTime > now) {
                        return;
                    }
                    cacheMap.remove(node.key);
                    expireQueue.poll();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
