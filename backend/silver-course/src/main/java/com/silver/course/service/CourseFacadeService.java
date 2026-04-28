package com.silver.course.service;

import com.silver.course.model.GeneratedLearnPageResponse;

/**
 * 学习中心门面服务接口。
 * 提供学习内容查询与分页展示能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface CourseFacadeService {

    /**
     * 分页查询用户生成的学习内容。
     * 查询当前登录用户通过 AI 生成的学习内容列表。
     *
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 生成内容分页数据
     */
    GeneratedLearnPageResponse generatedPage(int pageNum, int pageSize);
}
