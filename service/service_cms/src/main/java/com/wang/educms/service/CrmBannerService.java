package com.wang.educms.service;

import com.wang.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    // 查询banner列表
    List<CrmBanner> getBannerList();
}
