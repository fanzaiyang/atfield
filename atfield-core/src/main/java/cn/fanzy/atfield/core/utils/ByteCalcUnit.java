package cn.fanzy.atfield.core.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 字节比例单位
 *
 * @author fanzaiyang
 * @date 2023/12/21
 */
@Getter
public enum ByteCalcUnit {
    /**
     * 比特,最基本存储单元
     */
    Bit(0, 8),
    /**
     * 字节
     */
    Bytes(1, 1024),
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

    ByteCalcUnit(int index, int radix) {
        this.index = index;
        this.radix = radix;
    }

    public static ByteCalcUnit of(String unit) {
        for (ByteCalcUnit unit1 : values()) {
            if (StrUtil.equals(unit, "b")) {
                return ByteCalcUnit.Bytes;
            }
            if (StrUtil.equals(unit, "B")) {
                return ByteCalcUnit.Bit;
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
    public static BigDecimal getRadix(int start, int end) {
        Assert.isTrue(start >= 0 && start <= 9, "参数1必须介于0到9之间。");
        Assert.isTrue(end >= 0 && end <= 9, "参数2必须介于0到9之间。");
        if (start == end) {
            return BigDecimal.ONE;
        }
        if (end > start) {
            // 从小到大
            int radix = 1;
            for (int i = start; i < end; i++) {
                radix = radix * RADIX[i];
            }
            return BigDecimal.ONE
                    .divide(BigDecimal.valueOf(radix),
                            10,
                            RoundingMode.HALF_UP);
        }
        // 从大到小
        int radix = 1;
        for (int i = end; i < start; i++) {
            radix = radix * RADIX[i];
        }
        return BigDecimal.valueOf(radix);
    }

    /**
     * 获取大小描述
     *
     * @param bit 字节大小
     * @return {@link String }
     */
    public static String getText(BigDecimal bit) {
        if (bit.compareTo(new BigDecimal("100")) < 0) {
            return bit + "b";
        }
        BigDecimal kb = bit.divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_UP);
        if (kb.compareTo(new BigDecimal("100")) < 0) {
            return kb + "Kb";
        }
        BigDecimal mb = kb.divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_UP);
        if (mb.compareTo(new BigDecimal("100")) < 0) {
            return mb + "Mb";
        }
        BigDecimal gb = mb.divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_UP);
        if (gb.compareTo(new BigDecimal("100")) < 0) {
            return gb + "Mb";
        }
        BigDecimal tb = gb.divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_UP);
        if (tb.compareTo(new BigDecimal("100")) < 0) {
            return tb + "Tb";
        }

        BigDecimal pb = tb.divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_UP);
        if (pb.compareTo(new BigDecimal("100")) < 0) {
            return pb + "Pb";
        }
        BigDecimal eb = pb.divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_UP);
        return eb + "Eb";
    }
}
