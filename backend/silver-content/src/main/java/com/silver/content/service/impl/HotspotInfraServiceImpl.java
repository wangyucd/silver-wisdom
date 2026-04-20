package com.silver.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.silver.content.mapper.HotspotMapper;
import com.silver.content.model.HotspotEntity;
import com.silver.content.service.IHotspotInfraService;
import org.springframework.stereotype.Service;

/**
 * 热点基础数据访问实现。
 */
@Service
public class HotspotInfraServiceImpl extends ServiceImpl<HotspotMapper, HotspotEntity> implements IHotspotInfraService {
}
