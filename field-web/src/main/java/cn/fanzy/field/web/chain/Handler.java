package cn.fanzy.field.web.chain;

import java.io.Serializable;

/**
 * 链式处理接口
 *
 * @author fanzaiyang
 * @date 2023/12/13
 */
public interface Handler {
    /**
     * 获取组名称
     *
     * @return {@link String}
     */
    default String getGroupName() {
        return "default";
    }

    /**
     * 处理逻辑
     *
     * @param request 参数
     * @return {@link Serializable} 返回值
     */
    Serializable process(Serializable request);

    /**
     * 执行顺序，值越小越先执行。
     * 默认值：0
     *
     * @return int
     */
    int getOrder();
}
