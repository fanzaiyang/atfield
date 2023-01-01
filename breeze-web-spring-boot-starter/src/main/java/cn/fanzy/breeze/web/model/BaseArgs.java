package cn.fanzy.breeze.web.model;


import cn.fanzy.breeze.web.utils.SpringUtils;
import cn.hutool.core.util.NumberUtil;

/**
 * 基本参数
 *
 * @author fanzaiyang
 * @since 2022-08-16
 */
public class BaseArgs {

    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        if (pageNum == null || pageNum < 1) {
            String pageNum = SpringUtils.getRequest().getParameter("pageNum");
            return NumberUtil.isNumber(pageNum) ? NumberUtil.parseInt(pageNum) : 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            String pageSize = SpringUtils.getRequest().getParameter("pageSize");
            return NumberUtil.isNumber(pageSize) ? NumberUtil.parseInt(pageSize) : 15;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
