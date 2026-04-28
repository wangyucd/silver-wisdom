package com.silver.course.mapper;

import com.silver.course.model.GeneratedLearnItem;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 学习中心查询 Mapper。
 * 提供学习记录的复杂查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
public interface LearnRecordQueryMapper {

    /**
     * 查询已生成内容总数。
     *
     * @param userId 用户ID
     * @return 总数
     */
    @Select("""
            SELECT COUNT(1)
            FROM learn_record
            WHERE user_id = #{userId}
              AND record_type = 'GENERATED'
            """)
    long countGenerated(@Param("userId") Long userId);

    /**
     * 分页查询已生成内容。
     *
     * @param userId 用户ID
     * @param offset 偏移量
     * @param pageSize 页大小
     * @return 已生成内容列表
     */
    @Select("""
            SELECT lr.biz_id AS taskId,
                   agr.title AS title,
                   agr.summary AS summary,
                   lr.created AS createdAt
            FROM learn_record lr
            LEFT JOIN ai_generate_result agr ON agr.task_id = lr.biz_id
            WHERE lr.user_id = #{userId}
              AND lr.record_type = 'GENERATED'
            ORDER BY lr.created DESC
            LIMIT #{offset}, #{pageSize}
            """)
    List<GeneratedLearnItem> selectGeneratedPage(@Param("userId") Long userId,
                                                 @Param("offset") long offset,
                                                 @Param("pageSize") long pageSize);
}
