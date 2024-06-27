package cn.fanzy.atfield.core.utils;

import java.math.BigDecimal;

/**
 * 字节换算
 *
 * @author fanzaiyang
 * @date 2023/12/21
 */
public class ByteRation {

    /**
     * 价值
     */
    private final long value;
    /**
     * 单位
     */
    private final ByteCalcUnit unit;

    public ByteRation(long value, String unit) {
        this.value = value;
        this.unit = ByteCalcUnit.of(unit);
    }

    public ByteRation(long value, ByteCalcUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static ByteRation of(long value, String unit) {
        return new ByteRation(value, unit);
    }

    public static ByteRation of(long value, ByteCalcUnit unit) {
        return new ByteRation(value, unit);
    }

    /**
     * 获取字节
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getBytes() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.Bytes.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取位
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getBit() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.Bit.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取Kb
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getKb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.KB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 MB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getMb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.MB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 GB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getGb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.GB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取Tb
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getTb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.TB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 PB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getPb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.PB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 EB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getEb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.EB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    public BigDecimal getZb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.ZB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 YB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getYb() {
        BigDecimal radix = ByteCalcUnit.getRadix(unit.getIndex(), ByteCalcUnit.YB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }
}
