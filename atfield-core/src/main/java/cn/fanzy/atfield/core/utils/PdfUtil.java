package cn.fanzy.atfield.core.utils;


import cn.hutool.core.io.FileUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * pdf工具类,依赖于iTextPdf，需要在pom.xml中添加如下依赖
 * <pre>
 *   需要添加依赖：
 *  &lt;dependency>
 *     &lt;groupId>com.itextpdf&lt;/groupId>
 *     &lt;artifactId>itextpdf&lt;/artifactId>
 *     &lt;version>5.5.13.4&lt;/version>
 * &lt;/dependency>
 * &lt;dependency>
 *     &lt;groupId>com.itextpdf&lt;/groupId>
 *     &lt;artifactId>html2pdf&lt;/artifactId>
 *     &lt;version>5.0.5&lt;/version>
 * &lt;/dependency>
 * 注意：
 * 1. Html尽量规范
 * 2. html不支持float样式（关键字）
 * 3. 不要设置表格最小宽度
 * 4. html片段的样式，请使用内联样式，不要使用外部样式表，否则可能导致样式丢失
 * </pre>
 *
 * @author fanzaiyang
 * @date 2023-06-02
 */
public class PdfUtil {

    /**
     * 将html字符串转换为pdf并下载到客户端
     * <pre>
     *      默认A4大小，下载到客户端
     * </pre>
     *
     * @param html        html字符串
     * @param outFileName 输出文件名，带路径，如：/home/user/test.pdf
     * @param response    {@link HttpServletResponse}
     */
    public static void exportPdf(String html, String outFileName, HttpServletResponse response) {
        exportPdf(html, outFileName, response, PageSize.A4, null, null);
    }

    /**
     * 将html字符串转换为pdf并下载到客户端
     *
     * @param html        html字符串
     * @param outFileName 输出文件名，带路径，如：/home/user/test.pdf
     * @param response    {@link HttpServletResponse}
     * @param pageSize    页面大小{@link PageSize}
     * @param fontProgram 字体文件路径
     * @param encoding    字体编码{@link PdfEncodings}
     */
    public static void exportPdf(String html, String outFileName, HttpServletResponse response, PageSize pageSize, String fontProgram, String encoding) {
        try {
            response.setContentType("application/pdf;charset=UTF-8");
            response.setHeader("Content-Disposition",
                    "inline;filename=" + URLEncoder.encode(outFileName, StandardCharsets.UTF_8));
            execute(html, pageSize,
                    new PdfWriter(response.getOutputStream()),
                    getConverterProperties(fontProgram, encoding));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 将html字符串转换为pdf并保存到指定文件
     * <pre>
     *     默认A4大小，保存到文件
     * </pre>
     *
     * @param html        html字符串
     * @param outFileName 输出文件名，带路径，如：/home/user/test.pdf
     */
    public static void exportPdf(String html, String outFileName) {
        try {
            File file = FileUtil.file(outFileName);
            execute(html, PageSize.A4, new PdfWriter(file), getConverterProperties(null, null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * 将html字符串转换为pdf并保存到指定文件
     *
     * @param html        html字符串
     * @param outFileName 输出文件名，带路径，如：/home/user/test.pdf
     * @param pageSize    页面大小{@link PageSize}
     * @param fontProgram 字体文件路径
     * @param encoding    字体编码{@link PdfEncodings}
     */
    public static void exportPdf(String html, String outFileName, PageSize pageSize, String fontProgram, String encoding) {
        try {
            File file = FileUtil.file(outFileName);
            execute(html, pageSize,
                    new PdfWriter(file),
                    getConverterProperties(fontProgram, encoding));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void execute(String html, PageSize pageSize, PdfWriter pdfWriter, ConverterProperties properties) {
        PdfDocument pdfDocument = null;
        try {
            pdfDocument = new PdfDocument(pdfWriter);
            //设置为A4大小
            pdfDocument.setDefaultPageSize(pageSize == null ? PageSize.A4 : pageSize);
            //添加水印
            //  pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new WaterMarkEventHandler(waterMark));
            HtmlConverter.convertToPdf(html, pdfDocument, properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (pdfWriter != null && !pdfWriter.isCloseStream()) {
                try {
                    pdfWriter.close();
                } catch (IOException ignored) {
                }
            }
            if (pdfDocument != null && !pdfDocument.isClosed()) {
                pdfDocument.close();
            }
        }
    }

    /**
     * 设置字体
     *
     * @param fontProgram the path of the font program file
     * @param encoding    the font encoding. See {@link PdfEncodings}
     * @return {@link ConverterProperties }
     */
    public static ConverterProperties getConverterProperties(String fontProgram, String encoding) {
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new FontProvider();
        PdfFont pdfFont = null;
        try {
            pdfFont = PdfFontFactory.createFont(fontProgram, encoding == null ? PdfEncodings.IDENTITY_H : encoding);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fontProvider.addFont(pdfFont.getFontProgram(), encoding == null ? PdfEncodings.IDENTITY_H : encoding);
        properties.setFontProvider(fontProvider);
        return properties;
    }

}

