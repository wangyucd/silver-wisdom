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
 */
@Service
public class AiGenerateTaskInfraServiceImpl extends ServiceImpl<AiGenerateTaskMapper, AiGenerateTaskEntity>
        implements IAiGenerateTaskInfraService {

    private final AiGenerateResultMapper aiGenerateResultMapper;

    public AiGenerateTaskInfraServiceImpl(AiGenerateResultMapper aiGenerateResultMapper) {
        this.aiGenerateResultMapper = aiGenerateResultMapper;
    }

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
