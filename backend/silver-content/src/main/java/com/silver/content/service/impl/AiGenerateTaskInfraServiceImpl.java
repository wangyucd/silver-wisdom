package com.silver.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.content.mapper.AiGenerateResultMapper;
import com.silver.content.mapper.AiGenerateTaskMapper;
import com.silver.content.model.AiGenerateResultEntity;
import com.silver.content.model.AiGenerateTaskEntity;
import com.silver.content.service.IAiGenerateTaskInfraService;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * AI 生成任务基础数据访问实现。
 * 提供生成任务的 CRUD 操作及结果关联查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class AiGenerateTaskInfraServiceImpl extends ServiceImpl<AiGenerateTaskMapper, AiGenerateTaskEntity>
        implements IAiGenerateTaskInfraService {

    /**
     * AI 生成结果 Mapper
     */
    private final AiGenerateResultMapper aiGenerateResultMapper;

    /**
     * 构造 AI 生成任务基础服务。
     *
     * @param aiGenerateResultMapper AI 生成结果 Mapper
     */
    public AiGenerateTaskInfraServiceImpl(AiGenerateResultMapper aiGenerateResultMapper) {
        this.aiGenerateResultMapper = aiGenerateResultMapper;
    }

    /**
     * 根据任务号查询任务并关联结果。
     *
     * @param taskId 任务号
     * @return 任务信息
     */
    @Override
    public Optional<AiGenerateTaskEntity> findByTaskId(String taskId) {
        AiGenerateTaskEntity task = lambdaQuery().eq(AiGenerateTaskEntity::getTaskId, taskId).one();
        if (task == null) {
            return Optional.empty();
        }
        AiGenerateResultEntity result = aiGenerateResultMapper.selectOne(
                com.baomidou.mybatisplus.core.toolkit.Wrappers.<AiGenerateResultEntity>lambdaQuery()
                        .eq(AiGenerateResultEntity::getTaskId, taskId)
        );
        task.setResult(result);
        return Optional.of(task);
    }
}
