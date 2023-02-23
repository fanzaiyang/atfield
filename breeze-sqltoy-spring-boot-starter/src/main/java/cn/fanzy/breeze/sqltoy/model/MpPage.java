package cn.fanzy.breeze.sqltoy.model;

import java.util.Collections;
import java.util.List;

/**
 * 简单分页模型
 *
 * @author fanzaiyang
 * @since  2023-02-23
 */
public class MpPage<T> implements IPage<T>{
    private static final long serialVersionUID = 8545996863226528798L;

    /**
     * 查询数据列表
     */
    protected List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    protected long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    protected long size = 10;

    /**
     * 当前页
     */
    protected long current = 1;

    /**
     * 是否进行 count 查询
     */
    protected boolean searchCount = true;


    public MpPage() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public MpPage(long current, long size) {
        this(current, size, 0);
    }

    public MpPage(long current, long size, long total) {
        this(current, size, total, true);
    }


    public MpPage(long current, long size, long total, boolean searchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.size = size;
        this.total = total;
        this.searchCount = searchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public MpPage<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public MpPage<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public MpPage<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public MpPage<T> setCurrent(long current) {
        this.current = current;
        return this;
    }


    public static <T> MpPage<T> of(long current, long size, long total, boolean searchCount) {
        return new MpPage<>(current, size, total, searchCount);
    }


    public MpPage<T> setSearchCount(boolean searchCount) {
        this.searchCount = searchCount;
        return this;
    }

    @Override
    public long getPages() {
        // 解决 github issues/3208
        return IPage.super.getPages();
    }

    /* --------------- 以下为静态构造方式 --------------- */
    public static <T> MpPage<T> of(long current, long size) {
        return of(current, size, 0);
    }

    public static <T> MpPage<T> of(long current, long size, long total) {
        return of(current, size, total, true);
    }

    public static <T> MpPage<T> of(long current, long size, boolean searchCount) {
        return of(current, size, 0, searchCount);
    }

}
