package cn.fanzy.infra.core.utils;

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
    private final ByteRationUnit unit;

    public ByteRation(long value, String unit) {
        this.value = value;
        this.unit = ByteRationUnit.of(unit);
    }

    public ByteRation(long value, ByteRationUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static ByteRation of(long value, String unit) {
        return new ByteRation(value, unit);
    }
    public static ByteRation of(long value, ByteRationUnit unit) {
        return new ByteRation(value, unit);
    }

    /**
     * 获取字节
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getBytes() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.Bytes.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取位
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getBit() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.Bit.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取Kb
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getKb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.KB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 MB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getMb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.MB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 GB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getGb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.GB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取Tb
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getTb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.TB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 PB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getPb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.PB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 EB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getEb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.EB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    public BigDecimal getZb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.ZB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }

    /**
     * 获取 YB
     *
     * @return {@link BigDecimal}
     */
    public BigDecimal getYb() {
        BigDecimal radix = ByteRationUnit.getRadix(unit.getIndex(), ByteRationUnit.YB.getIndex());
        return BigDecimal.valueOf(this.value)
                .multiply(radix);
    }
}
