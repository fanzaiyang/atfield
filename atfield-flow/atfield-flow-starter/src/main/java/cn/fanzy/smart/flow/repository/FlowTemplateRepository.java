package cn.fanzy.smart.flow.repository;

import cn.fanzy.atfield.core.utils.IdUtils;
import cn.fanzy.smart.flow.model.Pages;
import cn.fanzy.smart.flow.model.entity.FlowTemplateInfoEntity;
import cn.fanzy.smart.flow.utils.SqlConstants;
import cn.fanzy.smart.flow.utils.SqlUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Page;
import cn.hutool.db.PageResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 流模板存储库
 *
 * @author fanzaiyang
 * @date 2024/03/11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FlowTemplateRepository {


    public void createFlowTemplateTable() {
        boolean exists = SqlUtil.isTableExists(SqlConstants.TB_FLOW_TEMPLATE_INFO);
        if (exists) {
            log.warn("数据库表：{}已存在！", SqlConstants.TB_FLOW_TEMPLATE_INFO);
            return;
        }
        try {
            SqlUtil.getDb().execute(SqlConstants.SQL_CREATE_TABLE_TEMPLATE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建流程模板
     *
     * @param entity 流模板信息
     * @return {@link String}
     */
    public String createFlowTemplate(FlowTemplateInfoEntity entity) {
        entity.setId(StrUtil.blankToDefault(entity.getId(), IdUtils.getSnowflakeNextIdStr()));
        entity.setDelFlag(entity.getDelFlag() == null ? 0 : entity.getDelFlag());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        int inserted = 0;
        try {
            inserted = SqlUtil.getDb()
                    .insertOrUpdate(Entity.create(SqlConstants.SQL_INSERT_TABLE_TEMPLATE)
                                    .set("id", entity.getId())
                                    .set("code", entity.getCode())
                                    .set("avatar", entity.getAvatar())
                                    .set("name", entity.getName())
                                    .set("remarks", entity.getRemarks())
                                    .set("order_number", entity.getOrderNumber())
                                    .set("status", entity.getStatus())
                                    .set("tenant_id", entity.getTenantId())
                                    .set("revision", entity.getRevision())
                                    .set("create_by", entity.getCreateBy())
                                    .set("create_time", entity.getCreateTime())
                                    .set("update_by", entity.getUpdateBy())
                                    .set("update_time", entity.getUpdateTime())
                                    .set("del_flag", entity.getDelFlag()),
                            "id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (inserted == 0) {
            throw new RuntimeException("创建流程模板失败!");
        }
        return entity.getId();
    }

    /**
     * 获取流模板
     *
     * @param id 编号
     * @return {@link FlowTemplateInfoEntity}
     */
    public FlowTemplateInfoEntity getFlowTemplate(String id) {
        try {
            Entity entity = SqlUtil.getDb().get(Entity.create(SqlConstants.TB_FLOW_TEMPLATE_INFO)
                    .set("id", id));
            if (entity == null) {
                return null;
            }
            return entity.toBean(FlowTemplateInfoEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取流模板页面
     *
     * @param pageNo   页码，1
     * @param pageSize 页面大小
     * @return {@link PageResult}<{@link FlowTemplateInfoEntity}>
     */
    public Pages<FlowTemplateInfoEntity> queryFlowTemplatePage(int pageNo, int pageSize) {
        try {
            PageResult<Entity> page = SqlUtil.getDb()
                    .page(Entity.create(SqlConstants.TB_FLOW_TEMPLATE_INFO),
                            new Page(pageNo - 1, pageSize));
            if (page == null) {
                return null;
            }
            List<FlowTemplateInfoEntity> list = page.stream().map(item -> item.toBean(FlowTemplateInfoEntity.class)).toList();
            return Pages.of(pageNo, pageSize, page.getTotal(), list);
        } catch (SQLException e) {
            log.error("查询流程模板页面异常！", e);
            throw new RuntimeException("查询流程模板页面异常！", e);
        }
    }
}
