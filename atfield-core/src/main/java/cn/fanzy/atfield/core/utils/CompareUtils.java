package cn.fanzy.atfield.core.utils;

import cn.fanzy.atfield.core.annotation.Compare;
import cn.fanzy.atfield.core.model.CompareNode;
import cn.fanzy.atfield.core.model.ComparedNode;
import cn.hutool.core.annotation.Alias;
import cn.hutool.core.util.StrUtil;
import org.sagacity.sqltoy.config.annotation.Column;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 比较 Utils
 *
 * @author Administrator
 * @date 2024/10/17
 */
public class CompareUtils {
    /**
     * 比较
     *
     * @param source 源
     * @param target 目标
     * @return {@link List }<{@link String }>
     */
    public static <T> List<String> compare(T source, T target) {
        return compare(source, target, null, true);
    }

    /**
     * 比较
     *
     * @param source 源
     * @param target 目标
     * @return {@link List }<{@link String }>
     */
    public static <T> List<String> compare(T source, T target, Boolean ignoreNoAnnotation) {
        return compare(source, target, null, ignoreNoAnnotation == null || ignoreNoAnnotation);
    }

    /**
     * 比较 STR
     *
     * @param source 源
     * @param target 目标
     * @return {@link String }
     */
    public static <T> String compareStr(T source, T target) {
        List<String> compare = compare(source, target, true);
        return StrUtil.join("；", compare);
    }

    /**
     * 比较 STR
     *
     * @param source  源
     * @param target  目标
     * @param segment 段
     * @return {@link String }
     */
    public static <T> String compareStr(T source, T target, String segment) {
        List<String> compare = compare(source, target, true);
        return StrUtil.join(segment, compare);
    }

    /**
     * 比较 STR
     *
     * @param source              源
     * @param target              目标
     * @param ignoreCompareFields 忽略比较字段
     * @param segment             段
     * @return {@link String }
     */
    public static <T> String compareStr(T source, T target, List<String> ignoreCompareFields, String segment, boolean ignoreNoAnnotation) {
        List<String> compare = compare(source, target, ignoreCompareFields, ignoreNoAnnotation);
        return StrUtil.join(segment, compare);
    }

    public static <T> List<ComparedNode> compareNode(T source, T target) {
        return compareNode(source, target, null, true);
    }

    /**
     * 比较节点
     *
     * @param source 源
     * @param target 目标
     * @return {@link List }<{@link ComparedNode }>
     */
    public static <T> List<ComparedNode> compareNode(T source, T target, boolean ignoreNoAnnotation) {
        return compareNode(source, target, null, ignoreNoAnnotation);
    }

    /**
     * 比较节点
     *
     * @param source              源
     * @param target              目标
     * @param ignoreCompareFields 忽略比较字段
     * @return {@link List }<{@link ComparedNode }>
     */
    public static <T> List<ComparedNode> compareNode(T source, T target, List<String> ignoreCompareFields, boolean ignoreNoAnnotation) {
        if (Objects.isNull(source) && Objects.isNull(target)) {
            return List.of();
        }
        Map<String, CompareNode> sourceMap = getFiledValueMap(source, ignoreNoAnnotation);
        Map<String, CompareNode> targetMap = getFiledValueMap(target, ignoreNoAnnotation);
        if (sourceMap.isEmpty() && targetMap.isEmpty()) {
            return List.of();
        }
        // 如果源数据为空
        if (sourceMap.isEmpty()) {
            return doEmptyNode(targetMap, ignoreCompareFields);
        }
        // 如果源数据不为空，则显示属性变化情况
        return doCompareNode(sourceMap, targetMap, ignoreCompareFields);
    }

    public static <T> List<String> compare(T source, T target, List<String> ignoreCompareFields) {

        // 如果源数据不为空，则显示属性变化情况
        return compare(source, target, ignoreCompareFields, false);
    }

    /**
     * 比较
     *
     * @param source              源
     * @param target              目标
     * @param ignoreCompareFields 忽略比较字段
     * @return {@link List }<{@link String }>
     */
    public static <T> List<String> compare(T source, T target, List<String> ignoreCompareFields, boolean ignoreNoAnnotation) {
        if (Objects.isNull(source) && Objects.isNull(target)) {
            return List.of();
        }
        Map<String, CompareNode> sourceMap = getFiledValueMap(source, ignoreNoAnnotation);
        Map<String, CompareNode> targetMap = getFiledValueMap(target, ignoreNoAnnotation);
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

    /**
     * 做空
     *
     * @param targetMap           目标地图
     * @param ignoreCompareFields 忽略比较字段
     * @return {@link List }<{@link String }>
     */
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

    /**
     * do empty node （执行空节点）
     *
     * @param targetMap           目标地图
     * @param ignoreCompareFields 忽略比较字段
     * @return {@link List }<{@link ComparedNode }>
     */
    private static List<ComparedNode> doEmptyNode(Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        List<ComparedNode> list = new ArrayList<>();
        Collection<CompareNode> values = targetMap.values();
        for (CompareNode node : values) {
            Object o = Optional.ofNullable(node.getFieldValue()).orElse("");
            if (Objects.nonNull(ignoreCompareFields) && ignoreCompareFields.contains(node.getFieldKey())) {
                continue;
            }
            if (StrUtil.isBlank(o.toString())) {
                continue;
            }
            list.add(ComparedNode.builder()
                    .fieldKey(node.getFieldKey()).fieldName(node.getFieldName())
                    .fieldValue("空")
                    .newFieldValue(node.getFieldValue())
                    .build());
        }
        return list;
    }

    /**
     * do compare node
     *
     * @param sourceMap           源映射
     * @param targetMap           目标地图
     * @param ignoreCompareFields 忽略比较字段
     * @return {@link List }<{@link ComparedNode }>
     */
    private static List<ComparedNode> doCompareNode(Map<String, CompareNode> sourceMap, Map<String, CompareNode> targetMap, List<String> ignoreCompareFields) {
        List<ComparedNode> list = new ArrayList<>();
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
                list.add(ComparedNode.builder()
                        .fieldKey(sn.getFieldKey()).fieldName(sn.getFieldName())
                        .fieldValue(sv)
                        .newFieldValue(tv)
                        .build());
            }
        }
        return list;
    }

    /**
     * 进行比较
     *
     * @param sourceMap           源映射
     * @param targetMap           目标地图
     * @param ignoreCompareFields 忽略比较字段
     * @return {@link List }<{@link String }>
     */
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


    /**
     * 获取字段值映射
     *
     * @param t t
     * @return {@link Map }<{@link String }, {@link CompareNode }>
     */
    private static <T> Map<String, CompareNode> getFiledValueMap(T t, boolean ignoreNoAnnotation) {
        if (Objects.isNull(t)) {
            return Collections.emptyMap();
        }
        Field[] fields = t.getClass().getDeclaredFields();
        if (fields.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, CompareNode> map = new LinkedHashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldKey = field.getName();
            String fieldName = null;
            Alias alias = field.getAnnotation(Alias.class);
            if (!Objects.isNull(alias)) {
                fieldName = alias.value();
            }
            if (StrUtil.isBlank(fieldName)) {
                Compare compareAnnotation = field.getAnnotation(Compare.class);
                if (compareAnnotation != null) {
                    fieldName = compareAnnotation.value();
                }
            }
            if (StrUtil.isBlank(fieldName)) {
                Column compareAnnotation = field.getAnnotation(Column.class);
                if (compareAnnotation != null) {
                    fieldName = compareAnnotation.comment();
                }
            }
            if (ignoreNoAnnotation && StrUtil.isBlank(fieldName)) {
                continue;
            }
            if (StrUtil.isBlank(fieldName)) {
                fieldName = fieldKey;
            }
            try {
                CompareNode node = new CompareNode();
                node.setFieldKey(fieldKey);
                node.setFieldValue(field.get(t));
                node.setFieldName(fieldName);
                map.put(field.getName(), node);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }


        }
        return map;
    }

    /**
     * 获取 HTML
     *
     * @param nodeList 节点列表
     * @return {@link String }
     */
    public static String getHtml(List<ComparedNode> nodeList) {
        String text = "";
        for (ComparedNode node : nodeList) {
            text += String.format("<strong>{}</strong> 由 <em class=\"sv\">{}<em> 变更为 <em  class=\"tv\">{}</em>",
                    node.getFieldName(), ObjectUtils.isEmpty(node.getFieldValue()) ? "空" : node.getFieldValue(), node.getNewFieldValue());
        }
        return text;
    }

    /**
     * 获取文本
     *
     * @param nodeList 节点列表
     * @return {@link String }
     */
    public static String getText(List<ComparedNode> nodeList) {
        String text = "";
        for (ComparedNode node : nodeList) {
            text += String.format("{} 由 {} 变更为 {}",
                    node.getFieldName(), ObjectUtils.isEmpty(node.getFieldValue()) ? "空" : node.getFieldValue(), node.getNewFieldValue());
        }
        return text;
    }
}


