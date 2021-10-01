package com.rzspider.project.park.parkManage.mapper;

import com.rzspider.project.park.parkManage.domain.Person;

import java.util.List;

public interface PersonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Person record);

    int insertSelective(Person record);

    Person selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Person record);

    int updateByPrimaryKey(Person record);

    List<Person> selectAll();

    List<Person> selectAllById(Long userId);

    int batchDeletePersonManage(Integer[] ids);

    Person selectOneById();

    int selectCount();
}