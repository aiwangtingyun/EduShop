package com.wang.allservice.controller.cms;


import com.wang.allservice.entity.cms.CrmBanner;
import com.wang.allservice.service.cms.CrmBannerService;
import com.wang.allservice.utils.RetMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前台banner管理接口
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
@Api(tags = {"网站首页Banner列表"})
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取首页banner列表")
    @GetMapping("/getBannerList")
    public RetMsg index() {
        List<CrmBanner> list = bannerService.getBannerList();
        return RetMsg.ok().data("list", list);
    }

}

