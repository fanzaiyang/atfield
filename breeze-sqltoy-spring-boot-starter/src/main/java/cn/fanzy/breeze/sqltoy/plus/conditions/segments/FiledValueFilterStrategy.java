package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


/**
 * 提起值过滤策略
 *
 * @author fanzaiyang
 * @date 2023-05-06
 */
public interface FiledValueFilterStrategy {

    /**
     * 验证
     *
     * @param value 价值
     * @return boolean
     */
    boolean validate(Object value);

    class FiledValueFilterStrategyHolder {

        private static FiledValueFilterStrategy filedValueFilterStrategy;

        static {
            filedValueFilterStrategy = DefaultFiledValueFilterStrategy.getInstance();
        }

        public static void setFiledValueFilterStrategy(FiledValueFilterStrategy filedValueFilterStrategy) {
            FiledValueFilterStrategyHolder.filedValueFilterStrategy = filedValueFilterStrategy;
        }

        public static FiledValueFilterStrategy getInstance() {
            return filedValueFilterStrategy;
        }

    }

    /**
     * 默认值过滤策略
     */
    class DefaultFiledValueFilterStrategy implements FiledValueFilterStrategy {

        private DefaultFiledValueFilterStrategy() {}

        @Override
        public boolean validate(Object value) {
            if (value == null) {
                return false;
            }
            return true;
        }

        public static FiledValueFilterStrategy getInstance() {
            return DefaultFiledValueFilterStrategyHolder.INSTANCE;
        }

        protected static class DefaultFiledValueFilterStrategyHolder {
            private static final FiledValueFilterStrategy INSTANCE = new DefaultFiledValueFilterStrategy();
        }
    }
}
