package cn.fanzy.breeze.core.html2img.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * java数据转图片实体类注解
 * @Author wyq
 * @Date 2021/8/26 14:40
 * @Version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HtmlTable {
    //单元格间距
    int cellspacing() default 0;
    //单元格内文字与边框的距离
    int cellpadding() default 0;
    //边框
    int border() default 0;
    //table style
    String tableStyle() default "width: 800px;border:solid #add9c0; border-width:1px 0px 0px 1px;";
    //文字说明
    String captionTitle() default "";
    //文字说明
    String captionStyle() default "text-align: center;font-weight: bolder;font-size: 18px;padding-bottom:15px;";
    //标题 thead 的tr 样式
    String theadTrStyle() default "background-color:#F9F9F9";
    // tbody 的 tr 样式
    String tbodyTrStyle() default "";
    //页脚内容 的 th 样式
    String tfootThStyle() default "border:solid #add9c0; border-width:0px 1px 1px 0px;padding:4px;text-align:center;";
    //序号开启
    boolean orderFlag() default true;

    String orderStyle() default "border:solid #add9c0; border-width:0px 1px 1px 0px;padding:4px;text-align:center;width:60px";
}
