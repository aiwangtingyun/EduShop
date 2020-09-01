package com.wang.allservice.service.cms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.allservice.entity.cms.CrmBanner;

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
