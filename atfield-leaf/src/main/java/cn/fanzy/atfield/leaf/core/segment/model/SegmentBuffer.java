package cn.fanzy.atfield.leaf.core.segment.model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 双buffer
 */
@Data
public class SegmentBuffer implements Serializable {
    @Serial
    private static final long serialVersionUID = -3184270275020909405L;
    /**
     * 钥匙
     */
    private String key;
    /**
     * 双buffer
     */
    private Segment[] segments;
    /**
     * 当前的使用的segment的index
     */
    private volatile int currentPos;
    /**
     * 下一个segment是否处于可切换状态
     */
    private volatile boolean nextReady;
    /**
     * 是否初始化完成
     */
    private volatile boolean initOk;
    /**
     * 线程是否在运行中
     */
    private final AtomicBoolean threadRunning;
    /**
     * 锁
     */
    private final ReadWriteLock lock;

    /**
     * 步
     */
    private volatile int step;
    /**
     * 最小步长
     */
    private volatile int minStep;
    /**
     * 更新时间戳
     */
    private volatile long updateTimestamp;

    public SegmentBuffer() {
        segments = new Segment[]{new Segment(this), new Segment(this)};
        currentPos = 0;
        nextReady = false;
        initOk = false;
        threadRunning = new AtomicBoolean(false);
        lock = new ReentrantReadWriteLock();
    }

    public Segment getCurrent() {
        return segments[currentPos];
    }

    public Lock rLock() {
        return lock.readLock();
    }

    public Lock wLock() {
        return lock.writeLock();
    }

    public int nextPos() {
        return (currentPos + 1) % 2;
    }

    public void switchPos() {
        currentPos = nextPos();
    }
}
