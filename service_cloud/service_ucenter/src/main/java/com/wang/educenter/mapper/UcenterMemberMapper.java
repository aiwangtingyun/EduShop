package com.wang.educenter.mapper;

import com.wang.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author 王廷云
 * @since 2020-08-16
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    // 查询某一天的注册人数
    Integer countRegisterDay(@Param("day") String day);
}
