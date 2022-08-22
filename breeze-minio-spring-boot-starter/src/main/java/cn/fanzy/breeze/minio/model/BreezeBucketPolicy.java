package cn.fanzy.breeze.minio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微风桶政策
 *
 * @author fanzaiyang
 * @date 2022-08-22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BreezeBucketPolicy {
    @JsonProperty(value = "Version")
    private String Version;
    @JsonProperty(value = "Statement")
    private List<Statement> Statement;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Statement {
        @JsonProperty(value = "Effect")
        private String Effect;
        @JsonProperty(value = "Principal")
        private Map<String,Object> Principal=new HashMap<>();
        @JsonProperty(value = "Resource")
        private List<String> Resource;
        @JsonProperty(value = "Action")
        private List<String> Action;
    }
}
