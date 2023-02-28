package cn.fanzy.breeze.sqltoy.model;

import org.sagacity.sqltoy.model.Page;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页 Page 对象接口
 *
 * @author fanzaiyang
 * @since  2023-02-23
 */
public interface IPage<T> extends Serializable {


    /**
     * 自动优化 COUNT SQL【 默认：true 】
     *
     * @return true 是 / false 否
     */
    default boolean optimizeCountSql() {
        return true;
    }

    /**
     * 抵消
     * 计算当前分页偏移量
     *
     * @return long
     */
    default long offset() {
        long current = getCurrent();
        if (current <= 1L) {
            return 0L;
        }
        return Math.max((current - 1) * getSize(), 0L);
    }

    /**
     * 获取页面
     * 当前分页总页数
     *
     * @return long
     */
    default long getPages() {
        if (getSize() == 0) {
            return 0L;
        }
        long pages = getTotal() / getSize();
        if (getTotal() % getSize() != 0) {
            pages++;
        }
        return pages;
    }

    /**
     * 设置页面
     * 内部什么也不干
     * <p>只是为了 json 反序列化时不报错</p>
     *
     * @param pages 页面
     * @return {@link IPage}
     */
    default IPage<T> setPages(long pages) {
        // to do nothing
        return this;
    }

    /**
     * 分页记录列表
     *
     * @return 分页对象记录列表
     */
    List<T> getRecords();

    /**
     * 设置记录
     * 设置分页记录列表
     *
     * @param records 记录
     * @return IPage {@link IPage}
     */
    IPage<T> setRecords(List<T> records);

    /**
     * 当前满足条件总行数
     *
     * @return 总条数
     */
    long getTotal();

    /**
     * 设置总
     * 设置当前满足条件总行数
     *
     * @param total 总计
     * @return IPage
     */
    IPage<T> setTotal(long total);

    /**
     * 获取每页显示条数
     *
     * @return 每页显示条数
     */
    long getSize();

    /**
     * 设置大小
     * 设置每页显示条数
     *
     * @param size 大小
     * @return IPage IPage
     */
    IPage<T> setSize(long size);

    /**
     * 当前页
     *
     * @return 当前页
     */
    long getCurrent();

    /**
     * 设置当前
     * 设置当前页
     *
     * @param current 当前
     * @return IPage
     */
    IPage<T> setCurrent(long current);

    /**
     * IPage 的泛型转换
     *
     * @param mapper 转换函数
     * @param <R>    转换后的泛型
     * @return 转换泛型后的 IPage
     */
    @SuppressWarnings("unchecked")
    default <R> IPage<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getRecords().stream().map(mapper).collect(Collectors.toList());
        return ((IPage<R>) this).setRecords(collect);
    }

    /**
     * sqltoy的Page对象转为mybatis-plus的Ipage对象。
     * @param page Page
     * @return Page
     * @param <R> T
     */
    static <R> IPage<R> convert(Page<R> page) {
        IPage<R> iPage = new MpPage<>(page.getPageNo(), page.getPageSize(), page.getRecordCount());
        iPage.setRecords(page.getRows());
        return iPage;
    }
}
