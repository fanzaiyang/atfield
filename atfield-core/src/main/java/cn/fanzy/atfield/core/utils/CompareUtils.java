package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.annotation.Compare;
import cn.fanzy.atfield.core.model.CompareNode;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 比较 Utils
 *
 * @author Administrator
 * @date 2024/10/17
 */
public class CompareUtils {

    public static <T> List<String> compare(T source, T target) {
        return compare(source, target, null);
    }

    public static <T> String compareStr(T source, T target) {
        List<String> compare = compare(source, target);
        return StrUtil.join("；", compare);
    }

    public static <T> String compareStr(T source, T target, String segment) {
        List<String> compare = compare(source, target);
        return StrUtil.join(segment, compare);
    }

    public static <T> String compareStr(T source, T target, List<String> ignoreCompareFields, String segment) {
        List<String> compare = compare(source, target, ignoreCompareFields);
        return StrUtil.join(segment, compare);
    }

    public static <T> List<String> compare(T source, T target, List<String> ignoreCompareFields) {
        if (Objects.isNull(source) && Objects.isNull(target)) {
            return List.of();
        }
        Map<String, CompareNode> sourceMap = getFiledValueMap(source);
        Map<String, CompareNode> targetMap = getFiledValueMap(target);
        if (sourceMap.isEmpty() && targetMap.isEmpty()) {
            return List.of();
        }
        // 如果源数据为空
        if (sourceMap.isEmpty()) {
            return doEmpty(targetMap, ignoreCompareFields);
        }
        // 如果源数据不为空，则显示属性变化情况
        return doCompare(sourceMap, targetMap, ignoreCompareFields);
    }

    private static List<String> doEmpty(Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        List<String> list = new ArrayList<>();
        Collection<CompareNode> values = targetMap.values();
        for (CompareNode node : values) {
            Object o = Optional.ofNullable(node.getFieldValue()).orElse("");
            if (Objects.nonNull(ignoreCompareFields) && ignoreCompareFields.contains(node.getFieldKey())) {
                continue;
            }
            if (StrUtil.isBlank(o.toString())) {
                continue;
            }
            String html = StrUtil.format("<strong>{}</strong> 由 <em class=\"sv\">空<em> 变更为 <em class=\"tv\">{}</em>",
                    node.getFieldName(), node.getFieldValue());
            list.add(html);
        }
        return list;
    }

    private static List<String> doCompare(Map<String, CompareNode> sourceMap, Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        List<String> list = new ArrayList<>();
        Set<String> keys = sourceMap.keySet();
        for (String key : keys) {
            CompareNode sn = sourceMap.get(key);
            CompareNode tn = targetMap.get(key);
            if (Objects.nonNull(ignoreCompareFields) && ignoreCompareFields.contains(sn.getFieldKey())) {
                continue;
            }
            String sv = Optional.ofNullable(sn.getFieldValue()).orElse("").toString();
            String tv = Optional.ofNullable(tn.getFieldValue()).orElse("").toString();
            // 只有两者属性值不一致时, 才显示变化情况
            if (!StrUtil.equals(sv, tv)) {
                String html = StrUtil.format("<strong>{}</strong> 由 <em class=\"sv\">{}<em> 变更为 <em  class=\"tv\">{}</em>",
                        sn.getFieldName(), sv, tv);
                list.add(html);
            }
        }
        return list;
    }


    private static <T> Map<String, CompareNode> getFiledValueMap(T t) {
        if (Objects.isNull(t)) {
            return Collections.emptyMap();
        }
        Field[] fields = t.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, CompareNode> map = new LinkedHashMap<>();
        for (Field field : fields) {
            Compare compareAnnotation = field.getAnnotation(Compare.class);
            if (Objects.isNull(compareAnnotation)) {
                continue;
            }
            field.setAccessible(true);
            try {
                String fieldKey = field.getName();
                CompareNode node = new CompareNode();
                node.setFieldKey(fieldKey);
                node.setFieldValue(field.get(t));
                node.setFieldName(compareAnnotation.value());
                map.put(field.getName(), node);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
