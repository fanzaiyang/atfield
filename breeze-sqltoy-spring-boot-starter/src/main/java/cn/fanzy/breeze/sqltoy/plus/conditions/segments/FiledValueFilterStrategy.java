package cn.fanzy.breeze.sqltoy.plus.conditions.segments;


public interface FiledValueFilterStrategy {

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
            return DefaultFiledValueFilterStrategyHolder.instance;
        }

        protected static class DefaultFiledValueFilterStrategyHolder {
            private static FiledValueFilterStrategy instance = new DefaultFiledValueFilterStrategy();
        }
    }
}
