package com.rzspider.project.yw.ywManage.mapper;

import com.rzspider.project.yw.ywManage.domain.YwInfoList;

import java.util.List;

public interface YwInfoListMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(YwInfoList record);

    int insertSelective(YwInfoList record);

    YwInfoList selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(YwInfoList record);

    int updateByPrimaryKey(YwInfoList record);

    List<YwInfoList> selectAll();

    List<YwInfoList> selectAboutType(YwInfoList ywInfoList1);

    int insertYwInfoLists(List<YwInfoList> ywInfoLists);
}