package com.rzspider.project.yd.ydManage.mapper;

import com.rzspider.project.yd.ydManage.domain.YwInfo;
import com.rzspider.project.yd.ydManage.domain.YwMod;

import java.util.List;

public interface YwInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YwInfo record);

    int insertSelective(YwInfo record);

    YwInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YwInfo record);

    int updateByPrimaryKey(YwInfo record);

    int selectPage(YwInfo ywInfo);

    List<YwInfo> selectAll(YwInfo ywInfo);

//    List<YwMod> selectDtlByType();
}