package com.wang.edustatistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wang.commonutis.RetMsg;
import com.wang.edustatistics.client.UcenterClient;
import com.wang.edustatistics.entity.StatisticsDaily;
import com.wang.edustatistics.mapper.StatisticsDailyMapper;
import com.wang.edustatistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    // 生成某一天的统计数据
    @Override
    public void createData(String day) {
        // 添加记录之前删除表中相同日期的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        // 远程调用得到某一天注册人数
        RetMsg register = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) register.getData().get("countRegister");

        // 把获取到的数据添加数据库，统计分析表里面
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(countRegister);                    // 注册人数
        daily.setDateCalculated(day);                           // 统计日期
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));    // 视频播放数 TODO
        daily.setLoginNum(RandomUtils.nextInt(100,200));        // 登录人数   TODO
        daily.setCourseNum(RandomUtils.nextInt(100,200));       // 课程新增数 TODO
        baseMapper.insert(daily);
    }

    // 获取图表统计数据
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        // 根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        List<StatisticsDaily> statisticsList = baseMapper.selectList(wrapper);

        // 返回数据为日期和日期对应的数量
        ArrayList<String> dateCalculatedList = new ArrayList<>();
        ArrayList<Integer> dateNumList = new ArrayList<>();

        // 封装数据
        for (StatisticsDaily daily : statisticsList) {
            dateCalculatedList.add(daily.getDateCalculated());
            switch (type) {
                case "login_num":
                    dateNumList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    dateNumList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    dateNumList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    dateNumList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        // 返回结果集
        Map<String, Object> map = new HashMap<>();
        map.put("dateCalculatedList", dateCalculatedList);
        map.put("dateNumList", dateNumList);
        return map;
    }
}
