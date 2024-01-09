package cn.fanzy.atfield.core.utils;

import cn.hutool.core.text.StrPool;
import cn.hutool.crypto.SecureUtil;

/**
 * id 实用程序
 *
 * @author fanzaiyang
 * @date 2023/12/21
 */
public class IdUtil extends cn.hutool.core.util.IdUtil {

    /**
     * 获取安全的主键ID
     * <pre>
     *     可用于不获取id的前提下，知道id的唯一性
     * </pre>
     *
     * @param id 编号
     * @return {@link String}
     */
    public static String getSecId(String... id) {
        return SecureUtil.md5(String.join(StrPool.COLON, id));
    }
}
