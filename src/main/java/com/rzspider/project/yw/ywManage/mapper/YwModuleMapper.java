package com.rzspider.project.yw.ywManage.mapper;

import com.rzspider.project.yw.ywManage.domain.YwInfoList;
import com.rzspider.project.yw.ywManage.domain.YwModule;

import java.util.List;

public interface YwModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YwModule record);

    int insertSelective(YwModule record);

    YwModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YwModule record);

    int updateByPrimaryKey(YwModule record);

    List<YwModule> selectAll();

    List<YwModule> selectType();

    List<YwModule> selectByType(String str);

    YwModule selectYwContent(String yw_dtl);
}