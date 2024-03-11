package cn.fanzy.flow.model;

import cn.hutool.core.util.PageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 页
 *
 * @author fanzaiyang
 * @date 2024/03/11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pages<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 3179974334516819442L;

    private int pageNo;
    private int pageSize;
    private int total;
    private int totalPage;
    private List<T> data;

    public Pages(int pageNo, int pageSize, int total, List<T> data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
        this.totalPage=PageUtil.totalPage(total, pageSize);
    }

    public static <T> Pages<T> of(int pageNo, int pageSize, int total, List<T> data) {
        return new Pages<>(pageNo, pageSize, total, data);
    }

    public int getTotalPage() {
        return PageUtil.totalPage(total, pageSize);
    }
}
