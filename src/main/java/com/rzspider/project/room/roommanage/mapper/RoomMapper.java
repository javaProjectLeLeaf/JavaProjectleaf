package com.rzspider.project.room.roommanage.mapper;

import com.rzspider.project.room.roommanage.domain.Room;

import java.util.List;

public interface RoomMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Room record);

    Room selectByPrimaryKey(Integer id);

    List<Room> selectAll();

    int updateByPrimaryKey(Room record);
}