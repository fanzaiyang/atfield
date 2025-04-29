package cn.fanzy.atfield.sqltoy.utils;

import cn.fanzy.atfield.sqltoy.mp.IPage;
import org.sagacity.sqltoy.model.Page;

/**
 * 分页转换工具类
 *
 * @author fanzaiyang
 * @date 2025/04/29
 */
public class Page2Utils {

    /**
     * 页
     *
     * @param iPage i 页
     * @return {@link Page }<{@link T }>
     */
    public static <T> Page<T> page(IPage<T> iPage) {
        Page<T> page = new Page<>((int) iPage.getSize(), iPage.getCurrent());
        page.setRecordCount(iPage.getTotal());
        page.setRows(iPage.getRecords());
        return page;
    }

    /**
     * @param page
     * @return {@link IPage }<{@link T }>
     */
    public static <T> IPage<T> page(Page<T> page) {
        IPage<T> iPage = new cn.fanzy.atfield.sqltoy.mp.Page<>(page.getPageNo(), page.getPageSize());
        iPage.setTotal(page.getRecordCount());
        iPage.setRecords(page.getRows());
        return iPage;
    }

}
