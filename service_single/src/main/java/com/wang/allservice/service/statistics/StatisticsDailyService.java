package com.wang.allservice.service.statistics;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wang.allservice.entity.statistics.StatisticsDaily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    // 生成某一天的统计数据
    void createData(String day);

    // 获取图表统计数据
    Map<String, Object> getShowData(String type, String begin, String end);
}
