package cn.fanzy.atfield.core.utils;

import cn.hutool.core.lang.Assert;
import cn.idev.excel.FastExcel;
import cn.idev.excel.write.handler.WriteHandler;
import cn.idev.excel.write.metadata.style.WriteCellStyle;
import cn.idev.excel.write.metadata.style.WriteFont;
import cn.idev.excel.write.style.HorizontalCellStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 快速 Excel 工具
 *
 * @author fanzaiyang
 * @date 2024/12/24
 */
public class FastExcelUtils {

    /**
     * 写入到response
     *
     * @param fileName 文件名
     * @param response 响应
     */
    public static void write(String fileName, List<?> data, Class<?> clazz, HttpServletResponse response) {
        write(fileName, data, clazz, response, getStrategyStyle());
    }

    /**
     * 写
     *
     * @param fileName     文件名
     * @param data         数据
     * @param clazz        克拉兹
     * @param response     响应
     * @param writeHandler 写入处理程序
     */
    public static void write(String fileName, List<?> data, Class<?> clazz, HttpServletResponse response, WriteHandler writeHandler) {
        Assert.notBlank(fileName, "文件名不能为空!");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        response.setHeader("Content-Disposition", "attachment;filename=" + encodedFileName);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             OutputStream os = response.getOutputStream();) {
            FastExcel.write(bos, clazz)
                    .registerWriteHandler(writeHandler)
                    .sheet("导出结果")
                    .doWrite(data);
            byte[] bosByteArray = bos.toByteArray();
            response.setContentLength(bosByteArray.length);
            os.write(bosByteArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HorizontalCellStyleStrategy getStrategyStyle() {
        // 定义表头样式
        WriteCellStyle headStyle = new WriteCellStyle();
        WriteFont headFont = new WriteFont();
        headFont.setFontHeightInPoints((short) 12);
        headStyle.setWriteFont(headFont);
        // 定义内容样式
        WriteCellStyle contentStyle = new WriteCellStyle();
        // contentStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont contentFont = new WriteFont();
        contentFont.setFontHeightInPoints((short) 11);
        contentStyle.setWriteFont(contentFont);

        return new HorizontalCellStyleStrategy(headStyle, contentStyle);
    }
}
