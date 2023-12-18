package cn.fanzy.infra.web.param;


import cn.fanzy.infra.core.spring.SpringUtils;
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
public class ParamPageDto implements Serializable {

    @Serial
    private static final long serialVersionUID = -1241624388851356884L;
    private Integer pageNum;
    private Integer pageSize;

    public Integer getPageNum() {
        if (pageNum == null || pageNum < 1) {
            String pageNum = SpringUtils.getRequest().getParameter("pageNum");
            if(StrUtil.isBlank(pageNum)){
                pageNum = SpringUtils.getRequest().getParameter("pageNo");
            }
            return NumberUtil.isNumber(pageNum) ? NumberUtil.parseInt(pageNum) : 1;
        }
        return pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null || pageSize < 1) {
            String pageSize = SpringUtils.getRequest().getParameter("pageSize");
            return NumberUtil.isNumber(pageSize) ? NumberUtil.parseInt(pageSize) : 15;
        }
        return pageSize;
    }
}
