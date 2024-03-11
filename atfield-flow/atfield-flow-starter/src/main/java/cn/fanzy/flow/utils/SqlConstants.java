package cn.fanzy.flow.utils;

/**
 * SQL 常量
 *
 * @author fanzaiyang
 * @date 2024/03/11
 */
public class SqlConstants {

    public static final String TB_FLOW_TEMPLATE_INFO = "flow_template_info";
    public static final String TB_FLOW_TASK_INFO = "flow_task_info";
    public static final String TB_FLOW_INSTANCE_INFO = "flow_instance_info";
    /**
     * SQL 删除表模板
     */
    public static final String SQL_DROP_TABLE_TEMPLATE = "DROP TABLE IF EXISTS `flow_template_info`";
    /**
     * SQL:flow_template_info
     */
    public static final String SQL_CREATE_TABLE_TEMPLATE = """
            CREATE TABLE `flow_template_info`  (
              `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
              `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
              `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
              `name` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
              `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
              `order_number` int(11) NULL DEFAULT 1 COMMENT '序号',
              `status` smallint(1) NULL DEFAULT 1 COMMENT '状态；1-启用，2-禁用',
              `tenant_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户号',
              `revision` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '乐观锁',
              `create_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
              `update_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
              `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志;0-未删除，1-已删除',
              PRIMARY KEY (`id`) USING BTREE
            ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
            """;

    /**
     * SQL 删除表实例
     */
    public static final String SQL_DROP_TABLE_INSTANCE = "DROP TABLE IF EXISTS `flow_instance_info`";
    /**
     * sql:flow_instance_info
     */
    public static final String SQL_CREATE_TABLE_INSTANCE = """
            CREATE TABLE `flow_instance_info`  (
              `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
              `code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
              `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
              `form_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单ID',
              `flow_template_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程ID',
              `flow_status` smallint(1) NULL DEFAULT NULL COMMENT '流程状态；0-草稿，1-进行中，2-已完成，3-废弃，4-异常',
              `apply_user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请人ID',
              `apply_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
              `flow_current_node_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前节点ID',
              `flow_current_node_name` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前节点名称',
              `flow_current_handler_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '当前处理人',
              `flow_receive_time` datetime NULL DEFAULT NULL COMMENT '接收时间',
              `flow_next_handler_ids` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下一个处理人',
              `flow_next_node_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下一个节点ID',
              `flow_next_node_name` varchar(90) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下一个节点名称',
              `flow_template_info` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程模板信息',
              `status` smallint(1) NULL DEFAULT 1 COMMENT '流程状态；0-草稿，1-进行中，2-已完成，3-废弃，4-异常',
              `tenant_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户号',
              `revision` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '乐观锁',
              `create_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
              `update_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
              `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志;0-未删除，1-已删除',
              PRIMARY KEY (`id`) USING BTREE
            ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
            """;
    /**
     * SQL 删除表任务
     */
    public static final String SQL_DROP_TABLE_TASK = "DROP TABLE IF EXISTS `flow_task_info`";
    /**
     * sql:flow_task_info
     */
    public static final String SQL_CREATE_TABLE_TASK = """
            CREATE TABLE `flow_task_info`  (
              `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
              `flow_instance_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例ID',
              `node_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点ID',
              `handler_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '处理人ID',
              `approve_time` datetime NULL DEFAULT NULL COMMENT '审批时间',
              `approve_result` smallint(1) NULL DEFAULT 1 COMMENT '审批结果，1-通过，0-驳回，-1-删除',
              `approve_remarks` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批意见',
              `approve_type` smallint(1) NULL DEFAULT 1 COMMENT '审批类型，1-重走流程，2-不重走',
              `status` smallint(1) NULL DEFAULT 1 COMMENT '任务状态；1-有效，0-无效',
              `tenant_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '租户号',
              `revision` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '乐观锁',
              `create_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
              `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
              `update_by` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '更新人',
              `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
              `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标志;0-未删除，1-已删除',
              PRIMARY KEY (`id`) USING BTREE
            ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;
            """;

    public static final String SQL_INSERT_TABLE_TEMPLATE = """
            INSERT INTO flow_template_info (
                  	id,
                  	`code`,
                  	avatar,
                  	title,
                  	remarks,
                  	order_number,
                  	`status`,
                  	tenant_id,
                  	revision,
                  	create_by,
                  	create_time,
                  	update_by,
                  	update_time,
                  	del_flag
                  )
                  VALUES
                  	(?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;
    /**
     * SQL逻辑删除
     */
    public static final String SQL_DELETE_TABLE_TEMPLATE = """
            UPDATE flow_template_info SET del_flag=?,update_by=?,update_time=? WHERE id=?
            """;
    /**
     * SQL 插入表实例
     */
    public static final String SQL_INSERT_TABLE_INSTANCE = """
            INSERT INTO flow_instance_info (
            	id,
            	`code`,
            	title,
            	form_id,
            	flow_template_id,
            	flow_status,
            	apply_user_id,
            	apply_time,
            	flow_current_node_id,
            	flow_current_node_name,
            	flow_current_handler_ids,
            	flow_receive_time,
            	flow_next_handler_ids,
            	flow_next_node_id,
            	flow_next_node_name,
            	flow_template_info,
            	`status`,
            	tenant_id,
            	revision,
            	create_by,
            	create_time,
            	update_by,
            	update_time,
            	del_flag
            )
            VALUES
            	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
            """;
}
