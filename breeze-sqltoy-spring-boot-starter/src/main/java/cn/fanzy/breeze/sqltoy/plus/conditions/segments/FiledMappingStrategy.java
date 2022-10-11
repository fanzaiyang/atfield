package cn.fanzy.breeze.sqltoy.plus.conditions.segments;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字段映射策略
 */
public interface FiledMappingStrategy {

    String getColumnName(String filedName);

    default List<String> getColumnName(List<String> filedNames) {
        if (filedNames != null && filedNames.size() > 0) {
            return filedNames.stream().filter(Objects::nonNull).map(this::getColumnName).collect(Collectors.toList());
        }
        return null;
    }

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
            return DefaultFiledMappingStrategyHolder.instance;
        }

        protected static class DefaultFiledMappingStrategyHolder {
            private static DefaultFiledMappingStrategy instance = new DefaultFiledMappingStrategy();
        }
    }
}
