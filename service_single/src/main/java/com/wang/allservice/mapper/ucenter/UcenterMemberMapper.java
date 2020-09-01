package com.wang.allservice.mapper.ucenter;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.allservice.entity.ucenter.UcenterMember;
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
