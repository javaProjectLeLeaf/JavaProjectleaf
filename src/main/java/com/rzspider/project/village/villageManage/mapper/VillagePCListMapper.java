package com.rzspider.project.village.villageManage.mapper;

import com.rzspider.project.village.villageManage.domain.VillagePCList;
import com.rzspider.project.yd.ydManage.domain.YwMod;

import java.util.List;

public interface VillagePCListMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VillagePCList record);

    int insertSelective(VillagePCList record);

    VillagePCList selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VillagePCList record);

    int updateByPrimaryKey(VillagePCList record);

    List<VillagePCList> selectAll(VillagePCList villagePCList);

    Integer selectCount(VillagePCList villagePCList);

    List<VillagePCList> selectCountyCode();

    List<VillagePCList> selectRegionIdByCountyCode(String ywType);
}