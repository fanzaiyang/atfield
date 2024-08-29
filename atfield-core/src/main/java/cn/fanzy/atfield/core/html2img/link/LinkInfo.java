package cn.fanzy.atfield.core.html2img.link;

import java.awt.*;
import java.util.List;
import java.util.Map;


/**
 * 链接信息
 *
 * @author fanzaiyang
 * @since 2021/08/26
 */
public class LinkInfo {
    private final Map<String, String> attributes;
    private final List<Rectangle> bounds;

    public LinkInfo(Map<String, String> attributes, List<Rectangle> bounds) {
        this.attributes = attributes;
        this.bounds = bounds;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<Rectangle> getBounds() {
        return bounds;
    }
}
