package com.wang.allservice.controller.statistics;


import com.wang.allservice.service.statistics.StatisticsDailyService;
import com.wang.allservice.utils.RetMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-27
 */
@Api(tags = {"数据统计分析管理"})
@RestController
@RequestMapping("/edustatistics/statistics")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsService;

    // 生成某一天的统计数据
    @ApiOperation("生成某一天的统计数据")
    @PostMapping("/createData/{day}")
    public RetMsg createData(
            @ApiParam(name = "day", value = "注册日期")
            @PathVariable("day") String day) {
        statisticsService.createData(day);
        return RetMsg.ok();
    }

    // 获取图表统计数据
    @ApiOperation("获取图表显示数据")
    @GetMapping("/getShowData/{type}/{begin}/{end}")
    public RetMsg getShowData(
            @ApiParam(name = "type", value = "统计类型", required = true)
            @PathVariable("type") String type,
            @ApiParam(name = "begin", value = "开始日期", required = true)
            @PathVariable("begin") String begin,
            @ApiParam(name = "end", value = "结束日期", required = true)
            @PathVariable("end") String end) {
        Map<String, Object> map = statisticsService.getShowData(type, begin, end);
        return RetMsg.ok().data(map);
    }

}

