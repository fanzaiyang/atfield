package cn.fanzy.atfield.leaf.core.segment.dao.impl;

import cn.fanzy.atfield.leaf.core.common.TableInfo;
import cn.fanzy.atfield.leaf.core.segment.dao.IDAllocDao;
import cn.fanzy.atfield.leaf.core.segment.model.LeafAlloc;
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
 * idalloc DAO impl
 *
 * @author fanzaiyang
 * @date 2024/11/11
 */
@Slf4j
@RequiredArgsConstructor
public class IDAllocDaoImpl implements IDAllocDao {

    private final SqlToyRepository sqlToyRepository;
    private final LeafIdProperty property;


    @Override
    public List<LeafAlloc> getAllLeafAllocs() {
        return sqlToyRepository.findBySql(
                "SELECT biz_tag, max_id, step, update_time FROM @value(:tableName)",
                MapKit.map("tableName", property.getTableName()),
                LeafAlloc.class);
    }

    @Override
    public LeafAlloc getLeafAlloc(String tag) {
        List<LeafAlloc> leafAllocList = sqlToyRepository.findBySql(
                """
                        SELECT biz_tag, max_id, step FROM @value(:tableName) WHERE biz_tag = :tag
                        """,
                MapKit.keys("tableName", "tag")
                        .values(property.getTableName(), tag)
                , LeafAlloc.class
        );
        if (CollUtil.isEmpty(leafAllocList)) {
            return null;
        }
        return leafAllocList.get(0);
    }

    @Override
    public LeafAlloc updateMaxIdAndGetLeafAlloc(String tag) {
        sqlToyRepository.executeSql(
                """
                        UPDATE @value(:tableName) SET max_id = max_id + step WHERE biz_tag = :tag
                        """,
                MapKit.keys("tableName", "tag")
                        .values(property.getTableName(), tag)
        );
        return getLeafAlloc(tag);
    }

    @Override
    public LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc) {
        sqlToyRepository.executeSql("""
                        UPDATE @value(:tableName) SET max_id = max_id + @value(:step) WHERE biz_tag = :key
                        """,
                MapKit.keys("tableName", "step", "key")
                        .values(property.getTableName(), leafAlloc.getStep(), leafAlloc.getKey()));
        return getLeafAlloc(leafAlloc.getBizTag());
    }

    @Override
    public List<String> getAllTags() {
        List<LeafAlloc> allocList = sqlToyRepository.findBySql("""
                        SELECT distinct biz_tag FROM @value(:tableName)
                        """,
                MapKit.map("tableName", property.getTableName()),
                LeafAlloc.class);
        return allocList.stream().map(LeafAlloc::getBizTag).toList();
    }

    @Override
    public void createOrUpdateTable() {
        String schema = property.getTableSchema();
        String catgelog = null;
        if (StrUtil.isBlank(schema)) {
            try {
                Connection connection = sqlToyRepository.getDataSource().getConnection();
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
        List<TableInfo> list = sqlToyRepository.findBySql("""
                        select table_name from INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA= :schema and TABLE_NAME= :tableName
                        """,
                MapKit.keys("schema", "tableName")
                        .values(property.getTableSchema(), property.getTableName()),
                TableInfo.class);
        if (CollUtil.isEmpty(list)) {
            // 数据中不存在表，创建表, max_id, step, update_time
            sqlToyRepository.executeSql(
                    """
                            CREATE TABLE #[@value(:schema).]`@value(:tableName)` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `biz_tag` VARCHAR (128) NOT NULL DEFAULT '',
                              `max_id` BIGINT (20) NOT NULL DEFAULT '1',
                              `step` INT (11) NOT NULL,
                              `description` VARCHAR (256) DEFAULT NULL,
                              `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY ( `biz_tag` )
                            ) ENGINE = InnoDB
                             COMMENT = '分布式ID数据表'
                            """,
                    MapKit.keys("schema", "tableName")
                            .values(property.getTableSchema(), property.getTableName())
            );
            log.info("创建数据表成功！Schema：{}，表名：{}", property.getTableName(), property.getTableName());
            return;
        }
        // 检查表是否存在各个字段:biz_tag,max_id, step, update_time

        List<ColumnMeta> columns = sqlToyRepository.getTableColumns(catgelog, property.getTableSchema(), property.getTableName());
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
}
