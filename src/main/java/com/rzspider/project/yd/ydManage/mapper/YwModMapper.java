package com.rzspider.project.yd.ydManage.mapper;

import com.rzspider.project.yd.ydManage.domain.YwMod;

import java.util.List;

public interface YwModMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YwMod record);

    int insertSelective(YwMod record);

    YwMod selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YwMod record);

    int updateByPrimaryKey(YwMod record);

    List<YwMod> selectAll(YwMod ywMod);

    List<YwMod> selectAllByType();

    List<YwMod> selectDtlByType(String ywType);

    YwMod selectDtlByDtl(String ywDtl);

    int selectPage(YwMod ywMod);
}