package cn.fanzy.field.web.json.property;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 基础层JSON配置
 *
 * @author fanzaiyang
 * @date 2023/12/06
 */
@Data
@ConfigurationProperties(prefix = "at.field.web.json", ignoreUnknownFields = true)
public class JsonProperty {
    /**
     * 模型
     */
    private Model model = new Model("200", "操作成功！", "-1", "操作失败！",false,1024);
    /**
     * 转换
     */
    private Convert convert = new Convert();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Model {
        /**
         * 默认ok代码
         */
        private String defaultOkCode = "200";
        /**
         * 默认ok消息
         */
        private String defaultOkMessage = "操作成功！";
        /**
         * 默认失败代码
         */
        private String defaultFailCode = "-1";
        /**
         * 默认失败消息
         */
        private String defaultFailMessage = "操作失败！";
        /**
         * 启用错误堆栈，默认：false
         */
        private boolean enableErrorStack = false;
        /**
         * 错误堆栈大小,默认：1024
         * -1=不限制长度，0-不显示，>0-显示并截断
         */
        private int errorStackSize = 1024;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Convert {
        private Boolean enable;
        private StringCovert string = new StringCovert(true, "");
        private NumberCovert number = new NumberCovert(true, null);
        private BooleanCovert bool = new BooleanCovert(true, null);
        private ArrayCovert array = new ArrayCovert(true);
        private ObjectCovert object = new ObjectCovert(true, "");
        private DateCovert date = new DateCovert(true, "");

        @Data
        @AllArgsConstructor
        public static class StringCovert {
            private boolean enable;
            private String value="";
        }

        @Data
        @AllArgsConstructor
        public static class NumberCovert {
            private boolean enable;
            private String value;
        }

        @Data
        @AllArgsConstructor
        public static class BooleanCovert {
            private boolean enable;
            private Boolean value;
        }

        @Data
        @AllArgsConstructor
        public static class ArrayCovert {
            private boolean enable;
        }

        @Data
        @AllArgsConstructor
        public static class ObjectCovert {
            private boolean enable;
            private String value;
        }

        @Data
        @AllArgsConstructor
        public static class DateCovert {
            private boolean enable;
            private String value;
        }
    }

}
