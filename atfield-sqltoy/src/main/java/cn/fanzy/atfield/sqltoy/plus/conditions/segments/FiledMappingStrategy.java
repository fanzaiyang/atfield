package cn.fanzy.atfield.sqltoy.plus.conditions.segments;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字段映射策略
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public interface FiledMappingStrategy {

    /**
     * 得到列名字
     *
     * @param filedName 提起名字
     * @return {@link String}
     */
    String getColumnName(String filedName);

    /**
     * 得到列名字
     *
     * @param filedNames 提起名字
     * @return {@link List}<{@link String}>
     */
    default List<String> getColumnName(List<String> filedNames) {
        if (filedNames != null && filedNames.size() > 0) {
            return filedNames.stream().filter(Objects::nonNull).map(this::getColumnName).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 得到分裂列名字
     *
     * @param filedNames 提起名字
     * @param regex      正则表达式
     * @return {@link String}
     */
    default String getSplitColumnName(List<String> filedNames, String regex) {
        if (filedNames != null && filedNames.size() > 0) {
            return filedNames.stream().filter(Objects::nonNull).map(this::getColumnName).collect(Collectors.joining(regex));
        }
        return null;
    }

    /**
     * 默认字段映射策略
     */
    class DefaultFiledMappingStrategy implements FiledMappingStrategy {

        private DefaultFiledMappingStrategy() {}

        @Override
        public String getColumnName(String filedName) {
            return filedName;
        }

        public static DefaultFiledMappingStrategy getInstance() {
            return DefaultFiledMappingStrategyHolder.INSTANCE;
        }

        protected static class DefaultFiledMappingStrategyHolder {
            private static final DefaultFiledMappingStrategy INSTANCE = new DefaultFiledMappingStrategy();
        }
    }
}
