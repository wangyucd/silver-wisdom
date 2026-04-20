package com.silver.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silver.content.model.AiGenerateTaskEntity;
import java.util.Optional;

/**
 * AI 生成任务基础数据访问接口。
 */
public interface IAiGenerateTaskInfraService extends IService<AiGenerateTaskEntity> {

    /**
     * 按任务号查询任务并附加结果。
     *
     * @param taskId 任务号
     * @return 任务信息
     */
    Optional<AiGenerateTaskEntity> findByTaskId(String taskId);
}
