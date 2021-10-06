package com.rzspider.project.village.villageManage.mapper;

import com.rzspider.project.village.villageManage.domain.Village;

public interface VillageMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Village record);

    int insertSelective(Village record);

    Village selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Village record);

    int updateByPrimaryKey(Village record);
}