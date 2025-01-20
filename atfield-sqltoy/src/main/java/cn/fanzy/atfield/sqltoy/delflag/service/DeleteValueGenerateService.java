package cn.fanzy.atfield.sqltoy.delflag.service;

/**
 * 删除值生成服务
 *
 * @author fanzaiyang
 * @date 2025/01/20
 */
public interface DeleteValueGenerateService {

    /**
     * 获取删除值
     *
     * @param clazz 类
     * @return {@link String }
     */
    <T> String generate(Class<T> clazz);
}
