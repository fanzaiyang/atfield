package cn.fanzy.smart.flow.repository;

import cn.fanzy.atfield.core.utils.IdUtils;
import cn.fanzy.smart.flow.model.Pages;
import cn.fanzy.smart.flow.model.entity.FlowInstanceInfoEntity;
import cn.fanzy.smart.flow.model.entity.FlowTaskInfoEntity;
import cn.fanzy.smart.flow.model.entity.FlowTemplateInfoEntity;
import cn.fanzy.smart.flow.model.params.ParamUpdateTaskFinishDto;
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
 * 流实例存储库
 *
 * @author fanzaiyang
 * @date 2024/03/11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class FlowTaskRepository {


    public void createFlowTaskTable() {
        boolean exists = SqlUtil.isTableExists(SqlConstants.TB_FLOW_TASK_INFO);
        if (exists) {
            log.warn("数据库表：{}已存在！", SqlConstants.TB_FLOW_TASK_INFO);
            return;
        }
        try {
            SqlUtil.getDb().execute(SqlConstants.TB_FLOW_TASK_INFO);
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
    public String createFlowTask(FlowTaskInfoEntity entity) {
        entity.setId(StrUtil.blankToDefault(entity.getId(), IdUtils.getSnowflakeNextIdStr()));
        entity.setDelFlag(entity.getDelFlag() == null ? 0 : entity.getDelFlag());
        entity.setCreateTime(LocalDateTime.now());
        entity.setUpdateTime(LocalDateTime.now());
        int inserted = 0;
        try {
            inserted = SqlUtil.getDb()
                    .insertOrUpdate(Entity.create(SqlConstants.TB_FLOW_INSTANCE_INFO)
                                    .set("id", entity.getId())
                                    .set("flow_template_id", entity.getFlowTemplateId())
                                    .set("flow_instance_id", entity.getFlowInstanceId())
                                    .set("form_id", entity.getFormId())
                                    .set("node_id", entity.getNodeId())
                                    .set("node_name", entity.getNodeName())
                                    .set("handler_id", entity.getHandlerId())
                                    .set("approve_time", entity.getApproveTime())
                                    .set("approve_result", entity.getApproveResult().name())
                                    .set("approve_remarks", entity.getApproveRemarks())
                                    .set("approve_order", entity.getApproveOrder().name())
                                    .set("node_type", entity.getNodeType().name())
                                    .set("order_number", entity.getOrderNumber())
                                    .set("remarks", entity.getRemarks())
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
            throw new RuntimeException("创建流程任务失败!");
        }
        return entity.getId();
    }

    /**
     * 获取流模板
     *
     * @param id 编号
     * @return {@link FlowTemplateInfoEntity}
     */
    public FlowTaskInfoEntity getFlowTask(String id) {
        try {
            Entity entity = SqlUtil.getDb().get(Entity.create(SqlConstants.TB_FLOW_TASK_INFO)
                    .set("id", id));
            if (entity == null) {
                return null;
            }
            return entity.toBean(FlowTaskInfoEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取流模板页面
     *
     * @param pageNo   页码，1
     * @param pageSize 页面大小
     * @return {@link PageResult}<{@link FlowInstanceInfoEntity}>
     */
    public Pages<FlowInstanceInfoEntity> queryFlowInstancePage(int pageNo, int pageSize) {
        try {
            PageResult<Entity> page = SqlUtil.getDb()
                    .page(Entity.create(SqlConstants.TB_FLOW_INSTANCE_INFO),
                            new Page(pageNo - 1, pageSize));
            if (page == null) {
                return null;
            }
            List<FlowInstanceInfoEntity> list = page.stream().map(item -> item.toBean(FlowInstanceInfoEntity.class)).toList();
            return Pages.of(pageNo, pageSize, page.getTotal(), list);
        } catch (SQLException e) {
            log.error("查询流程实例异常！", e);
            throw new RuntimeException("查询流程实例异常！", e);
        }
    }

    public String updateTaskFinish(ParamUpdateTaskFinishDto param) {
        int inserted = 0;
        try {
            FlowTaskInfoEntity flowTask = getFlowTask(param.getTaskId());
            inserted = SqlUtil.getDb()
                    .insertOrUpdate(Entity.create(SqlConstants.TB_FLOW_TASK_INFO)
                                    .set("id", flowTask.getId())
                                    .set("approve_time", flowTask.getApproveTime())
                                    .set("approve_result", flowTask.getApproveResult().name())
                                    .set("approve_remarks", flowTask.getApproveRemarks())
                                    .set("approve_order", flowTask.getApproveOrder().name())
                                    .set("update_by", flowTask.getHandlerId())
                                    .set("update_time", LocalDateTime.now()),
                            "id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (inserted == 0) {
            throw new RuntimeException("创建流程任务失败!");
        }
        return param.getTaskId();
    }
}
