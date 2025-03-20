package cn.fanzy.atfield.web.param;


import cn.fanzy.atfield.core.spring.SpringUtils;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * param page dto
 * 基本参数
 *
 * @author fanzaiyang
 * @since 2022-08-16
 */
@Setter
public class QueryPageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1241624388851356884L;
    /**
     * 当前页，默认：1
     */
    private Integer pageNum;
    /**
     * 每页个数，默认：20
     */
    private Integer pageSize;

    public Integer getPageNum() {
        if (pageNum == null || pageNum < 1) {
            String pageNum = SpringUtils.getRequest().getParameter("pageNum");
            if (StrUtil.isBlank(pageNum)) {
                pageNum = SpringUtils.getRequest().getParameter("pageNo");
            }
            return NumberUtil.isNumber(pageNum) ? NumberUtil.parseInt(pageNum) : 1;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            String pageSize = SpringUtils.getRequest().getParameter("pageSize");
            return NumberUtil.isNumber(pageSize) ? NumberUtil.parseInt(pageSize) : 20;
        }
        return pageSize;
    }
}
