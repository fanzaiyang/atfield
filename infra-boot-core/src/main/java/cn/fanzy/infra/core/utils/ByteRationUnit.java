package cn.fanzy.infra.core.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 字节比例单位
 *
 * @author fanzaiyang
 * @date 2023/12/21
 */
@Getter
public enum ByteRationUnit {

    /**
     * 字节
     */
    Bytes(0, 8),
    /**
     * b
     */
    Bit(1, 1024),
    /**
     * 知识库
     */
    KB(2, 1024),
    /**
     * 兆字节
     */
    MB(3, 1024),
    /**
     * 国标
     */
    GB(4, 1024),
    /**
     * 结核病
     */
    TB(5, 1024),
    /**
     * 铅
     */
    PB(6, 1024),
    /**
     * EB型
     */
    EB(7, 1024),
    /**
     * ZB公司
     */
    ZB(8, 1024),
    /**
     * YB系列
     */
    YB(9, 1024);

    private final int index;
    private final int radix;

    ByteRationUnit(int index, int radix) {
        this.index = index;
        this.radix = radix;
    }

    public static ByteRationUnit of(String unit) {
        for (ByteRationUnit unit1 : values()) {
            if (StrUtil.equals(unit, "b")) {
                return ByteRationUnit.Bytes;
            }
            if (StrUtil.equals(unit, "B")) {
                return ByteRationUnit.Bit;
            }
            if (unit1.name().equalsIgnoreCase(unit)) {
                return unit1;
            }
        }
        return null;
    }

    private static final Integer[] RADIX = new Integer[]{
            8, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024, 1024
    };

    /**
     * 获取基数
     *
     * @param start 开始
     * @param end   结束
     */
    public static int getRadix(int start, int end) {
        Assert.isTrue(start >= 0 && start <= 9, "参数1必须介于0到9之间。");
        Assert.isTrue(end >= 0 && end <= 9, "参数2必须介于0到9之间。");
        if (start == end) {
            return 1;
        }
        if (end > start) {
            // 从小到大
            int radix = 1;
            for (int i = start; i < end; i++) {
                radix = radix * RADIX[start];
            }
            return 1 / radix;
        }
        // 从大到小
        int radix = 1;
        for (int i = end; i < start; i++) {
            radix = radix * RADIX[start];
        }
        return radix;
    }
}
