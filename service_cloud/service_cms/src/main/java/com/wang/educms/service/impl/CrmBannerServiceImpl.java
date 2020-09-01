package com.wang.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.educms.entity.CrmBanner;
import com.wang.educms.mapper.CrmBannerMapper;
import com.wang.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    // 查询banner列表，使用 redis 缓存
    @Cacheable(value = "banner", key = "'getBannerList'")
    @Override
    public List<CrmBanner> getBannerList() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        wrapper.last("limit 3");
        List<CrmBanner> bannerList = this.baseMapper.selectList(wrapper);
        return bannerList;
    }
}
