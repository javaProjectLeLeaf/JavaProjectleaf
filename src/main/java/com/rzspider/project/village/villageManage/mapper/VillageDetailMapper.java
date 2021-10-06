package com.rzspider.project.village.villageManage.mapper;

import com.rzspider.project.village.villageManage.domain.VillageDetail;

public interface VillageDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(VillageDetail record);

    int insertSelective(VillageDetail record);

    VillageDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VillageDetail record);

    int updateByPrimaryKey(VillageDetail record);
}