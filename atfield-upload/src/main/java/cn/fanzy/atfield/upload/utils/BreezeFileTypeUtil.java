package cn.fanzy.atfield.upload.utils;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class BreezeFileTypeUtil {


    /**
     * 得到文件类型
     *
     * @param file 文件
     * @return {@link String}
     */
    public static String getFileType(MultipartFile file) {
        try {
            String type = FileTypeUtil.getType(file.getInputStream(), file.getOriginalFilename());
            if (StrUtil.isBlank(type)) {
                // 如果未获取到文件类型，则根据文件名截取
                String fileName = file.getOriginalFilename();
                return fileName.substring(fileName.lastIndexOf("."));
            }
            return type;
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }


    /**
     * 得到文件类型
     *
     * @param file 文件
     * @return {@link String}
     */
    public static String getFileType(File file) {
        String type = FileTypeUtil.getType(file);
        if (StrUtil.isBlank(type)) {
            // 如果未获取到文件类型，则根据文件名截取
            String fileName = file.getName();
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return type;
    }

    public static String getFileType(String fileName) {
        Assert.notBlank(fileName, "文件名称不能为空！");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
