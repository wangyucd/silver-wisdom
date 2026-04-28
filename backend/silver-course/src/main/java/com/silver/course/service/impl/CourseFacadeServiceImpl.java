package com.silver.course.service.impl;

import com.silver.common.auth.StpMiniappUtil;
import com.silver.course.mapper.LearnRecordQueryMapper;
import com.silver.course.model.GeneratedLearnPageResponse;
import com.silver.course.service.CourseFacadeService;
import org.springframework.stereotype.Service;

/**
 * 学习中心门面服务实现。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class CourseFacadeServiceImpl implements CourseFacadeService {

    /**
     * 学习记录查询数据访问。
     */
    private final LearnRecordQueryMapper learnRecordQueryMapper;

    /**
     * 构造学习中心门面服务。
     *
     * @param learnRecordQueryMapper 学习记录查询数据访问
     */
    public CourseFacadeServiceImpl(LearnRecordQueryMapper learnRecordQueryMapper) {
        this.learnRecordQueryMapper = learnRecordQueryMapper;
    }

    /**
     * 查询用户生成内容分页列表。
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 生成内容分页数据
     */
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
