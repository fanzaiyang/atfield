package cn.fanzy.breeze.core.html2img.generator;


import cn.fanzy.breeze.core.html2img.link.LinkHarvester;
import cn.fanzy.breeze.core.html2img.link.LinkInfo;
import cn.fanzy.breeze.core.html2img.util.FormatNameUtil;
import cn.fanzy.breeze.core.html2img.util.SynchronousHTMLEditorKit;
import cn.hutool.core.img.FontUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.setting.Setting;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * html图像发生器
 *
 * @author fanzaiyang
 * @date 2021/08/26
 */
@Slf4j
public class HtmlImageGenerator {
    private final JEditorPane editorPane;
    static final Dimension DEFAULT_SIZE = new Dimension(800, 800);

    public HtmlImageGenerator() {
        editorPane = createJEditorPane();
    }

    public ComponentOrientation getOrientation() {
        return editorPane.getComponentOrientation();
    }

    public void setOrientation(ComponentOrientation orientation) {
        editorPane.setComponentOrientation(orientation);
    }

    public Dimension getSize() {
        return editorPane.getSize();
    }

    public void setSize(Dimension dimension) {
        editorPane.setSize(dimension);
    }

    public void loadUrl(URL url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadUrl(String url) {
        try {
            editorPane.setPage(url);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while loading %s", url), e);
        }
    }

    public void loadHtml(String html) {
        editorPane.setText(html);
        onDocumentLoad();
    }

    public String getLinksMapMarkup(String mapName) {
        final StringBuilder markup = new StringBuilder();
        markup.append("<map name=\"").append(mapName).append("\">\n");
        for (LinkInfo link : getLinks()) {
            final List<Rectangle> bounds = link.getBounds();
            for (Rectangle bound : bounds) {
                final int x1 = (int) bound.getX();
                final int y1 = (int) bound.getY();
                final int x2 = (int) (x1 + bound.getWidth());
                final int y2 = (int) (y1 + bound.getHeight());
                markup.append(String.format("<area coords=\"%s,%s,%s,%s\" shape=\"rect\"", x1, y1, x2, y2));
                for (Map.Entry<String, String> entry : link.getAttributes().entrySet()) {
                    String attName = entry.getKey();
                    String value = entry.getValue();
                    markup.append(" ").append(attName).append("=\"").append(value.replace("\"", "&quot;")).append("\"");
                }
                markup.append(">\n");
            }
        }
        markup.append("</map>\n");
        return markup.toString();
    }

    public List<LinkInfo> getLinks() {
        final LinkHarvester harvester = new LinkHarvester(editorPane);
        return harvester.getLinks();
    }

    public void saveAsHtmlWithMap(String file, String imageUrl) {
        saveAsHtmlWithMap(new File(file), imageUrl);
    }

    public void saveAsHtmlWithMap(File file, String imageUrl) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
            writer.append("<html>\n<head></head>\n");
            writer.append("<body style=\"margin: 0; padding: 0; text-align: center;\">\n");
            final String htmlMap = getLinksMapMarkup("map");
            writer.write(htmlMap);
            writer.append("<img border=\"0\" usemap=\"#map\" src=\"");
            writer.append(imageUrl);
            writer.append("\"/>\n");
            writer.append("</body>\n</html>");
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while saving '%s' html file", file), e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ignore) {
                }
            }
        }

    }

    public void saveAsImage(String file) {
        saveAsImage(new File(file));
    }

    public void saveAsImage(File file) {

        BufferedImage img = getBufferedImage();
        try {
            final String formatName = FormatNameUtil.formatForFilename(file.getName());
            ImageIO.write(img, formatName, file);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Exception while saving '%s' image", file), e);
        }
    }

    protected void onDocumentLoad() {
    }

    public Dimension getDefaultSize() {
        return DEFAULT_SIZE;
    }

    public BufferedImage getBufferedImage() {
        Dimension prefSize = editorPane.getPreferredSize();
        BufferedImage img = new BufferedImage(prefSize.width, editorPane.getPreferredSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();//获得一个Graphics2D对象
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        editorPane.setSize(prefSize);
        editorPane.paint(g2);
        g2.dispose();
        return img;
    }

    protected JEditorPane createJEditorPane() {
        final JEditorPane editorPane = new JEditorPane();
        editorPane.setSize(getDefaultSize());
        editorPane.setEditable(false);
        final SynchronousHTMLEditorKit kit = new SynchronousHTMLEditorKit();
        editorPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        editorPane.setEditorKitForContentType("text/html", kit);
        editorPane.setContentType("text/html;charset=utf-8");
        editorPane.putClientProperty("charset", "utf-8");
        Font font = getFont();
        if (font != null) {
            editorPane.setFont(font);
        }
        editorPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("page")) {
                    onDocumentLoad();
                }
            }
        });
        return editorPane;
    }

    public Font getFont() {
        try {
            //读取classpath下的font.setting，不使用变量
            Setting setting = new Setting("font.setting");
            String fontPath = setting.getStr("font.path");
            Assert.notBlank(fontPath, "请设置在font.setting文件中，设置font.path属性。");
            if (StrUtil.startWith(fontPath, "http")) {
                // 字体来源网络
                log.info("字体来源网络文件：{}", fontPath);
                String fonName = fontPath.substring(fontPath.lastIndexOf("/"));
                String localFontPath = FileUtil.getTmpDirPath() + FileUtil.FILE_SEPARATOR + fonName;
                if (!FileUtil.exist(localFontPath)) {
                    HttpUtil.downloadFileFromUrl(fontPath, localFontPath);
                }
                Font font = FontUtil.createFont(FileUtil.file(localFontPath));
                return font.deriveFont(20f);
            }
            if (StrUtil.startWith(fontPath, "classpath:")) {
                // 字体来源资源文件
                log.info("字体来源资源文件：{}", fontPath);
                InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fontPath.replaceAll("classpath:", ""));
                Font font = Font.createFont(Font.TRUETYPE_FONT, resourceAsStream);
                return font.deriveFont(20f);
            }
            // 字体来源本地
            log.info("字体来源于配置的本地路径：{}", fontPath);
            Font font = FontUtil.createFont(FileUtil.file(fontPath));
            return font.deriveFont(20f);
        } catch (Exception e) {
            log.error("使用默认字体：SansSerif：{}", e.getMessage());
            return FontUtil.createSansSerifFont(20);
        }
    }
}
