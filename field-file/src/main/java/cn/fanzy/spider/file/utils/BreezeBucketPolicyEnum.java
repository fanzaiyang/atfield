package cn.fanzy.spider.file.utils;

/**
 * 微风桶政策枚举
 *
 * @author fanzaiyang
 * @since 2022-08-22
 */
public enum BreezeBucketPolicyEnum {
    GetBucketLocation("s3:GetBucketLocation"),
    ListBucket("s3:ListBucket"),
    GetObject("s3:GetObject");

    private final String action;

    BreezeBucketPolicyEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
