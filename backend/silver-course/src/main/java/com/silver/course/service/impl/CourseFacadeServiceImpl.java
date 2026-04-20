package com.silver.course.service.impl;

import com.silver.common.auth.StpMiniappUtil;
import com.silver.course.mapper.LearnRecordQueryMapper;
import com.silver.course.model.GeneratedLearnPageResponse;
import com.silver.course.service.CourseFacadeService;
import org.springframework.stereotype.Service;

/**
 * 学习中心门面服务实现。
 */
@Service
public class CourseFacadeServiceImpl implements CourseFacadeService {

    private final LearnRecordQueryMapper learnRecordQueryMapper;

    public CourseFacadeServiceImpl(LearnRecordQueryMapper learnRecordQueryMapper) {
        this.learnRecordQueryMapper = learnRecordQueryMapper;
    }

    @Override
    public GeneratedLearnPageResponse generatedPage(int pageNum, int pageSize) {
        int normalizedPageNum = Math.max(pageNum, 1);
        int normalizedPageSize = Math.max(pageSize, 1);
        Long userId = StpMiniappUtil.stpLogic().isLogin() ? StpMiniappUtil.stpLogic().getLoginIdAsLong() : 10001L;
        GeneratedLearnPageResponse response = new GeneratedLearnPageResponse();
        response.setTotal(learnRecordQueryMapper.countGenerated(userId));
        response.setList(learnRecordQueryMapper.selectGeneratedPage(
                userId,
                (long) (normalizedPageNum - 1) * normalizedPageSize,
                normalizedPageSize
        ));
        return response;
    }
}
