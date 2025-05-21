package cn.fanzy.atfield.core.model;

/**
 * 当前操作人
 *
 * @author Administrator
 * @date 2024/10/16
 */
public interface IOperator {

    /**
     * 获取用户ID
     *
     * @return {@link String }
     */
    String getId();

    /**
     * 获取名称
     *
     * @return {@link String }
     */
    String getName();
}
