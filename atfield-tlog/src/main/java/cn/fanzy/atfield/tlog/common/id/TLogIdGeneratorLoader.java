package cn.fanzy.atfield.tlog.common.id;


import lombok.Getter;

public class TLogIdGeneratorLoader {

    @Getter
    private static TLogIdGenerator idGenerator = new TLogDefaultIdGenerator();

    public static void setIdGenerator(TLogIdGenerator idGenerator) {
        TLogIdGeneratorLoader.idGenerator = idGenerator;
    }
}
