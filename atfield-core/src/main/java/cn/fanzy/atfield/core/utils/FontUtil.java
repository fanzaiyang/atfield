package cn.fanzy.atfield.core.utils;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * 字体实用程序
 *
 * @author fanzaiyang
 * @date 2024/01/23
 */
public class FontUtil extends cn.hutool.core.img.FontUtil {

    /**
     * 获取字体长度
     *
     * @param font 字体
     * @param text 发短信
     * @return int
     */
    public static int getFontLength(Font font, String text) {
        FontRenderContext context = new FontRenderContext(new AffineTransform(), true, true);
        int offset = 0;
        for (int i = 0; i < text.length(); i++) {
            String charAt = String.valueOf(text.charAt(i));
            Rectangle rectangle = font.getStringBounds(charAt, context).getBounds();
            offset += rectangle.width;
        }
        return offset;
    }
}
