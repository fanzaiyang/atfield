package cn.fanzy.atfield.sqltoy.flex.query;

import lombok.Data;

import java.io.Serializable;

/**
 * 查询条件
 *
 * @author fanzaiyang
 * @date 2024/01/17
 */
@Data
public class QueryCondition implements Serializable {
    private String segment;
    private String keyword;
}
