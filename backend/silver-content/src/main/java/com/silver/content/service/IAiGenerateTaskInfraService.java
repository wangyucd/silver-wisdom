package com.silver.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.silver.content.model.AiGenerateTaskEntity;
import java.util.Optional;

/**
 * AI 生成任务基础数据访问接口。
 * 提供生成任务的基础 CRUD 及结果关联查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
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
