package com.wang.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.commonutils.RetMsg;
import com.wang.educms.entity.CrmBanner;
import com.wang.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
@Api(tags = {"后台banner管理接口"})
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("{page}/{limit}")
    public RetMsg index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<CrmBanner> pageParam = new Page<>(page, limit);
        bannerService.page(pageParam, null);
        return RetMsg.ok().data("list", pageParam.getRecords()).data("total", pageParam.getTotal());
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("add")
    public RetMsg save(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return RetMsg.ok();
    }

    @ApiOperation(value = "根据id获取Banner")
    @GetMapping("/get/{id}")
    public RetMsg get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return RetMsg.ok().data("item", banner);
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("/update")
    public RetMsg updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return RetMsg.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("/remove/{id}")
    public RetMsg remove(@PathVariable String id) {
        bannerService.removeById(id);
        return RetMsg.ok();
    }

}

