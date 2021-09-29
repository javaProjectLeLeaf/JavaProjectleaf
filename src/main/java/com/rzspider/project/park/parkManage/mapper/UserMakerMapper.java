package com.rzspider.project.park.parkManage.mapper;

import com.rzspider.project.park.parkManage.domain.UserMaker;

public interface UserMakerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserMaker record);

    int insertSelective(UserMaker record);

    UserMaker selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserMaker record);

    int updateByPrimaryKey(UserMaker record);
}