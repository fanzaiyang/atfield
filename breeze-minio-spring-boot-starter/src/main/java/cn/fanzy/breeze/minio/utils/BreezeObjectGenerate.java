package cn.fanzy.breeze.minio.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.IdUtil;

import java.util.Date;

/**
 * Minio对象生成
 *
 * @author fanzaiyang
 * @date 2022-08-22
 */
public class BreezeObjectGenerate {


    /**
     * 生成日期风格前缀，以斜杠结尾。
     *
     * @return {@link String}
     */
    public static String datePrefix() {
        // 生成年月
        String prefix = DateUtil.format(new Date(), "yyyy/MM/dd");
        return prefix + "/";
    }

    /**
     * 对象名称
     *
     * @param fileType 文件类型
     * @return {@link String}
     */
    public static String objectName(String fileType) {
        return datePrefix() + IdUtil.objectId() + StrPool.DOT + fileType;
    }
}
