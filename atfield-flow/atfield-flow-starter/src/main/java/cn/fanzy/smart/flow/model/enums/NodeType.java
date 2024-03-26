package cn.fanzy.smart.flow.model.enums;

/**
 * 任务类型
 *
 * @author fanzaiyang
 * @date 2024/03/08
 */
public enum NodeType {
    /**
     * 启动任务
     */
    START_TASK,
    /**
     * 用户任务
     */
    USER_TASK,
    /**
     * 抄送
     */
    CC_TASK,
    /**
     * 条件任务
     */
    CONDITION_TASK,
    /**
     * 机器人任务
     */
    ROBOT_TASK,
    /**
     * 结束任务
     */
    END_TASK;
}
