package cn.fanzy.atfield.core.shortid.enums;

import lombok.Getter;

/**
 * seq 位长枚举
 *
 * @author fanzaiyang
 * @date 2024/12/20
 */
@Getter
public enum SeqBitLengthEnum {
    LENGTH15((byte) 6),
    LENGTH16((byte) 10),
    LENGTH17((byte) 12);

    private final byte seqBitLength;

    SeqBitLengthEnum(byte seqBitLength) {
        this.seqBitLength = seqBitLength;
    }
}
