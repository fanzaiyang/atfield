package cn.fanzy.atfield.leaf.dao;

import cn.fanzy.atfield.leaf.model.LeafAlloc;
import cn.fanzy.atfield.leaf.model.TableInfo;
import cn.fanzy.atfield.leaf.property.LeafIdProperty;
import cn.fanzy.atfield.sqltoy.repository.SqlToyRepository;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sagacity.sqltoy.model.ColumnMeta;
import org.sagacity.sqltoy.model.MapKit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 主键根道impl （实现）
 *
 * @author fanzaiyang
 * @date 2024/11/12
 */
@Slf4j
@RequiredArgsConstructor
public class IdGenDaoImpl implements IdGenDao {
    private final SqlToyRepository repository;
    private final LeafIdProperty property;

    @Override
    public void createTable() {
        String schema = property.getTableSchema();
        String catgelog = null;
        if (StrUtil.isBlank(schema)) {
            try {
                Connection connection = repository.getDataSource().getConnection();
                schema = connection.getSchema();
                catgelog = connection.getCatalog();
                property.setTableSchema(StrUtil.blankToDefault(schema, catgelog));
                log.info("获取当前数据库schema：{}", property.getTableSchema());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        Assert.notBlank(property.getTableSchema(), "获取table-schema失败!");
        Assert.notBlank(property.getTableName(), "配置文件中必须配置leaf.id.table-name!");
        List<TableInfo> list = repository.findBySql("""
                        select table_name from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA= :schema and TABLE_NAME= :tableName
                        """,
                MapKit.keys("schema", "tableName")
                        .values(property.getTableSchema(), property.getTableName()),
                TableInfo.class);
        if (CollUtil.isEmpty(list)) {
            // 数据中不存在表，创建表, max_id, step, update_time
            repository.executeSql(
                    """
                            CREATE TABLE @value(:schemaName).`@value(:tableName)` (
                              `biz_tag` VARCHAR (128) NOT NULL DEFAULT '',
                              `max_id` BIGINT (20) NOT NULL DEFAULT '1',
                              `step` INT (11) NOT NULL,
                              `remarks` VARCHAR (256) DEFAULT NULL,
                              `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY ( `biz_tag` )
                            ) ENGINE = InnoDB
                             COMMENT = '分布式ID数据表'
                            """,
                    MapKit.keys("schemaName", "tableName")
                            .values(property.getTableSchema(), property.getTableName())
            );
            log.info("创建数据表成功！Schema：{}，表名：{}", property.getTableName(), property.getTableName());
            return;
        }
        // 检查表是否存在各个字段:biz_tag,max_id, step, update_time

        List<ColumnMeta> columns = repository.getTableColumns(catgelog, property.getTableSchema(), property.getTableName());
        List<String> colFields = columns.stream().map(item -> item.getColName().toLowerCase()).toList();
        Assert.isTrue(colFields.contains("biz_tag"), "数据表中必须包含biz_tag字段!");
        Assert.isTrue(colFields.contains("max_id"), "数据表中必须包含max_id字段!");
        Assert.isTrue(colFields.contains("step"), "数据表中必须包含step字段!");
        Assert.isTrue(colFields.contains("update_time"), "数据表中必须包含update_time字段!");
        for (ColumnMeta column : columns) {
            if (StrUtil.equalsIgnoreCase(column.getColName(), "max_id")) {
                Assert.isTrue(StrUtil.equalsIgnoreCase(column.getTypeName(), "bigint"), "数据表中max_id字段类型必须为bigint!");
            }
            if (StrUtil.equalsIgnoreCase(column.getColName(), "step")) {
                Assert.isTrue(StrUtil.equalsIgnoreCase(column.getTypeName(), "int"), "数据表中step字段类型必须为int!");
            }
        }
    }

    @Override
    public List<LeafAlloc> queryLeafAllocList() {
        return repository.findBySql(
                "select biz_tag,max_id,step from @value(:tableName)",
                MapKit.keys("tableName")
                        .values(property.getTableName()),
                LeafAlloc.class);
    }

    @Override
    public LeafAlloc getLeafAlloc(String tag) {
        List<LeafAlloc> leafAllocList = repository.findBySql(
                "select biz_tag,max_id,step from @value(:tableName) where biz_tag = :tag",
                MapKit.keys("tableName", "tag")
                        .values(property.getTableName(), tag),
                LeafAlloc.class);
        if (CollUtil.isEmpty(leafAllocList)) {
            return null;
        }
        return leafAllocList.get(0);
    }

    @Override
    public LeafAlloc getOrCreateLeafAlloc(String tag) {
        List<LeafAlloc> leafAllocList = repository.findBySql(
                "select biz_tag,max_id,step from @value(:tableName) where biz_tag = :tag",
                MapKit.keys("tableName", "tag")
                        .values(property.getTableName(), tag),
                LeafAlloc.class);
        if (CollUtil.isEmpty(leafAllocList)) {
            LeafAlloc alloc = new LeafAlloc();
            alloc.setBizTag(tag);
            alloc.setMaxId(0);
            alloc.setStep(1);
            createLeafAlloc(LeafAlloc.builder()
                    .bizTag(tag)
                    .maxId(0)
                    .step(1)
                    .build());
            return alloc;
        }
        return leafAllocList.get(0);
    }

    @Override
    public void updateMaxId(String tag, long maxId) {
        repository.executeSql(
                """
                        update @value(:tableName) set max_id = :maxId where biz_tag = :tag
                        """,
                MapKit.keys("tableName", tag)
                        .values(property.getTableName(), tag)
        );
    }

    @Override
    public void createLeafAlloc(LeafAlloc entity) {
        repository.executeSql(
                """
                        insert into @value(:tableName) (biz_tag,max_id,step)
                          values (:tag,0,1)
                        """,
                MapKit.keys("tableName", "tag", "maxId", "step")
                        .values(property.getTableName(), entity.getBizTag(), entity.getMaxId(), entity.getStep())
        );
    }
}
