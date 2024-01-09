package cn.fanzy.atfield.file.utils;

import lombok.Getter;

/**
 * 微风桶政策枚举
 *
 * @author fanzaiyang
 * @since 2022-08-22
 */
@Getter
public enum BreezeBucketPolicyEnum {
    GetBucketLocation("s3:GetBucketLocation"),
    ListBucket("s3:ListBucket"),
    GetObject("s3:GetObject");

    private final String action;

    BreezeBucketPolicyEnum(String action) {
        this.action = action;
    }

}
