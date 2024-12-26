
package cn.fanzy.atfield.sqltoy.id;

import cn.fanzy.atfield.core.shortid.contract.IIdGenerator;
import cn.fanzy.atfield.core.shortid.contract.IdGeneratorOptions;
import cn.fanzy.atfield.core.shortid.idgen.DefaultIdGenerator;
import org.sagacity.sqltoy.SqlToyConstants;
import org.sagacity.sqltoy.SqlToyContext;
import org.sagacity.sqltoy.plugins.id.IdGenerator;

import java.util.Date;

/**
 * 15位雪花算法ID生成器
 *
 * @author fanzaiyang
 * @date 2024/12/24
 */
public class SnowIdGenerator implements IdGenerator {
    private static IIdGenerator generator = null;

    @Override
    public Object getId(String tableName, String signature, String[] relatedColumns, Object[] relatedColValue, Date bizDate, String idJavaType, int length, int sequenceSize) {
        if (generator == null) {
            try {
                initialize(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return generator.newLong();
    }

    @Override
    public void initialize(SqlToyContext sqlToyContext) throws Exception {
        if (generator == null) {
            IdGeneratorOptions options = new IdGeneratorOptions((short) SqlToyConstants.WORKER_ID);
            options.setSeqBitLength((byte) 6);
            // 雪花算法起始时间戳：2024-01-01 00:00:00
            options.setBaseTime(1704038400000L);
            generator = new DefaultIdGenerator(options);
        }
    }
}
