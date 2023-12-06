package cn.fanzy.infra.web.json.property;

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
@ConfigurationProperties(prefix = "infra.json", ignoreUnknownFields = true)
public class JsonProperty {
    /**
     * 模型
     */
    private Model model = new Model("200", "操作成功！", "-1", "操作失败！");
    /**
     * 转换
     */
    private Convert convert = new Convert();
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Model {
        private String defaultOkCode;
        private String defaultOkMessage;
        private String defaultFailCode;
        private String defaultFailMessage;
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
            private String value;
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
