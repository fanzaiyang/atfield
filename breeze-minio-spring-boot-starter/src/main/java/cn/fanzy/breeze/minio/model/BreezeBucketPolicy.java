package cn.fanzy.breeze.minio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

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
    private String version;
    @JsonProperty(value = "Statement")
    private List<Statement> statement;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Statement {
        @JsonProperty(value = "Effect")
        private String effect;
        @JsonProperty(value = "Principal")
        private String principal;
        @JsonProperty(value = "Resource")
        private String resource;
        @JsonProperty(value = "Action")
        private List<String> action;
    }
}
