package com.silver.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.content.mapper.CategoryMapper;
import com.silver.content.model.CategoryEntity;
import com.silver.content.service.ICategoryInfraService;
import org.springframework.stereotype.Service;

/**
 * 分类基础数据访问实现。
 * 提供分类的 CRUD 操作及基础查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class CategoryInfraServiceImpl extends ServiceImpl<CategoryMapper, CategoryEntity> implements ICategoryInfraService {
}
