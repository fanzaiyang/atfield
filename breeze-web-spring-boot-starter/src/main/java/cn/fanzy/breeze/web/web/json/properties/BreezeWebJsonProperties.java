package cn.fanzy.breeze.web.web.json.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @author fanzaiyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "breeze.web.json")
public class BreezeWebJsonProperties implements Serializable {
    private static final long serialVersionUID = -3642562913132999859L;

    private Boolean enable;

    /**
     * 启用数组Null转[]
     */
    private ArrayConfig array = new ArrayConfig(true);
    /**
     * 启用字符串Null转""
     */
    private StringConfig string = new StringConfig(true, "");
    /**
     * 启用数字Null转""
     */
    private NumberConfig number = new NumberConfig(true, null);

    /**
     * 启用Bool Null转false
     */
    private BooleanConfig bool = new BooleanConfig(true, false);

    /**
     * 启用日期 Null转""
     */
    private DateConfig date = new DateConfig(true, "");

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ArrayConfig implements Serializable {
        private static final long serialVersionUID = -8009525539397895880L;
        private Boolean enable;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StringConfig implements Serializable {
        private static final long serialVersionUID = 7206753783746316492L;
        private Boolean enable;
        private String defaultValue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NumberConfig implements Serializable {
        private static final long serialVersionUID = -8830984202144987747L;
        private Boolean enable;
        private Integer defaultValue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BooleanConfig implements Serializable {
        private static final long serialVersionUID = 2351363663391795346L;
        private Boolean enable;
        private Boolean defaultValue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DateConfig implements Serializable {
        private static final long serialVersionUID = 9222925097381238683L;
        private Boolean enable;
        private String defaultValue;
    }


}
