package com.silver.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.content.mapper.HotspotMapper;
import com.silver.content.model.HotspotEntity;
import com.silver.content.service.IHotspotInfraService;
import org.springframework.stereotype.Service;

/**
 * 热点基础数据访问实现。
 * 提供热点的 CRUD 操作及基础查询能力。
 *
 * @author wangyu03
 * @since 2026/04/27 10:00
 */
@Service
public class HotspotInfraServiceImpl extends ServiceImpl<HotspotMapper, HotspotEntity> implements IHotspotInfraService {
}
