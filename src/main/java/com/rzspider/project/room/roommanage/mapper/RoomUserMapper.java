package com.rzspider.project.room.roommanage.mapper;

import com.rzspider.project.room.roommanage.domain.RoomUser;
import java.util.List;

public interface RoomUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoomUser record);

    RoomUser selectByPrimaryKey(Integer id);

    List<RoomUser> selectAll();

    int updateByPrimaryKey(RoomUser record);
}