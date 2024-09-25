package cn.fanzy.atfield.core.utils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源实用程序
 * <pre>
 *     增强读取resource目录下文件能力
 * </pre>
 *
 * @author fanzaiyang
 * @date 2024/09/25
 */
public class ResourceUtils extends org.springframework.util.ResourceUtils {

    /**
     * 将资源作为流获取
     * <pre>
     *     返回流的目的：在jar文件中，不能直接通过文件资源路径拿到文件，但是可以在jar包中拿到文件流。
     *     2. 待优化：把resource下文件放入缓存中，提高性能。
     * </pre>
     *
     * @param relativeFilePath 相对文件路径
     * @return {@link InputStream }
     */
    public static InputStream getResourceAsStream(String relativeFilePath) {
        if (!StrUtil.startWith(relativeFilePath, "classpath:") && FileUtil.exist(relativeFilePath)) {
            return FileUtil.getInputStream(relativeFilePath);
        }
        ClassPathResource resource = new ClassPathResource(
                StrUtil.startWith(relativeFilePath, "classpath:") ?
                        relativeFilePath :
                        "classpath:" + relativeFilePath
        );
        try {
            return resource.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
