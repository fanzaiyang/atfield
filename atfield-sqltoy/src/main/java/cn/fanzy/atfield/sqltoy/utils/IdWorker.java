package cn.fanzy.atfield.sqltoy.utils;

import cn.fanzy.atfield.sqltoy.id.SnowIdGenerator;
import org.sagacity.sqltoy.plugins.id.IdGenerator;

/**
 * id 工作程序
 *
 * @author fanzaiyang
 * @date 2024/09/11
 */
public class IdWorker {
    private static final IdGenerator snowIdGenerator = new SnowIdGenerator();


    /**
     * 下一个ID
     * <pre>
     *     sowId: 雪花算法生成的id，长度为15位
     * </pre>
     * @return {@link String }
     */
    public static String nextSnowId() {
        return snowIdGenerator.getId(null, null, null, null, null,
                        null, 0, 0)
                .toString();
    }
}
