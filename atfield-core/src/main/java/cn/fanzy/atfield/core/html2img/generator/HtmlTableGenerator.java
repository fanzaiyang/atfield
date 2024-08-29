package cn.fanzy.atfield.core.html2img.generator;


import cn.fanzy.atfield.core.html2img.table.HtmlTable;
import cn.fanzy.atfield.core.html2img.table.HtmlTh;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author  wyq
 * @since 2021/8/26 15:33
 */
@Slf4j
public class HtmlTableGenerator {


    /**
     *  简单的表格生成
     *  1.根据实体类注解生成
     *  2.页脚数据 lastData不传则没有页脚
     *  3.序号生成根据 @HtmlTable 中 orderFlag（true和false）判断
     * @param collection 需要生成数据的List
     * @param tClass     类型
     * @param lastData   合计（页脚数据）
     * @param fileSaveUrl fileSaveUrl
     */
    public void  dataToImage (Collection<?> collection, Class<?> tClass,Object lastData,String fileSaveUrl) {
        HtmlImageGenerator htmlImageGenerator = new HtmlImageGenerator();
        //处理table
        HtmlTable table = tClass.getAnnotation(HtmlTable.class);
        StringBuilder str = new StringBuilder();
        if(table!=null){
            str.append("  <div style=\"text-align: center; width: 600px;margin:20px;\">" +
                    "  <table cellspacing=\""+table.cellspacing()+"\" cellpadding="+table.cellpadding()+"\" border= \""+table.border()+"\" " +
                    "   style=\""+table.tableStyle()+"\">"+
                    "  <caption style=\""+table.captionStyle()+"\">"+table.captionTitle()+"</caption>");
        }
        String orderStyle = table.orderStyle();
        //处理th 和 td
        //获取所有字段
        Field[] fields = tClass.getDeclaredFields();
        //有注解的字段
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = null;
        if(fields!=null&&fields.length>0) {
            //处理标题
            str.append("<thead><tr style=\""+table.theadTrStyle()+"\">");
            //遍历第一个序号标志
            boolean varFlag = true;
            for (Field field : fields) {
                HtmlTh th = field.getAnnotation(HtmlTh.class);
                //判断序号是否开启
                if(table.orderFlag()&&varFlag){
                    str.append("<th style=\""+orderStyle+"\" >序号</th>");
                    varFlag =false;
                }
                if (th!=null) {
                    str.append("<th style=\""+th.thStyle()+"\" >"+th.name()+" </th>");
                    map = new HashMap<String, Object>();
                    map.put("field",field);
                    map.put("anno",th);
                    mapList.add(map);
                }
            }
            str.append("</tr> </thead>");
        }

        //处理数据
        if (collection!=null&&!collection.isEmpty()&&mapList!=null&&mapList.size()>0){
            int orderNumber = 0;
            for(Object obj:collection){
                str.append("<tr>");
                //序号判断
                if(table.orderFlag()){
                    //取第一个的样式
                    HtmlTh th = (HtmlTh) mapList.get(0).get("anno");
                    str.append("<td style=\""+orderStyle+"\" >"+(orderNumber+1)+"</td>");
                }
                for(int x=0;x<mapList.size();x++){
                    Map<String,Object> map1 = mapList.get(x);
                    HtmlTh th = (HtmlTh) map1.get("anno");
                    Field field = (Field) map1.get("field");
                    str.append("<td style=\""+th.tdStyle()+"\">");
                    try {
                        Field valueField = obj.getClass().getDeclaredField(field.getName());
                        //获取私有
                        valueField.setAccessible(true);
                        Object value = valueField.get(obj);
                        str.append(value==null?"":value.toString());
                    }catch (Exception e){
                        log.error(e.getMessage(),e);
                    }
                    str.append("</td>");
                }
                str.append("</tr>");
                orderNumber++;
            }
        }
        //table 页脚处理
        if (lastData!=null&&(lastData.getClass().getName().equals(tClass.getName()))){
            str.append("<tfoot> <tr>");
            if(table.orderFlag()){
                //取第一个的样式
                str.append("<th style=\""+orderStyle+"\" >合计</th>");
            }
            for(int x=0;x<mapList.size();x++){
                Map<String,Object> map1 = mapList.get(x);
                HtmlTh th = (HtmlTh) map1.get("anno");
                Field field = (Field) map1.get("field");
                str.append("<th style=\""+th.tdStyle()+"\">");
                try {
                    Field valueField = lastData.getClass().getDeclaredField(field.getName());
                    //获取私有
                    valueField.setAccessible(true);
                    Object value = valueField.get(lastData);
                    str.append(value==null?"":value.toString());
                }catch (Exception e){
                    log.error(e.getMessage(),e);
                }
                str.append("</th>");
            }
            str.append("</tr></tfoot>");
        }

        str.append("</table></div>");
        htmlImageGenerator.loadHtml(str.toString());
        htmlImageGenerator.saveAsImage(fileSaveUrl);
    }
}
