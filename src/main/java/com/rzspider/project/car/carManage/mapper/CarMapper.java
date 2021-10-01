package com.rzspider.project.car.carManage.mapper;

import com.rzspider.project.car.carManage.domain.Car;

import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    List<Car> selectAll();

    List<Car> selectCarByPersonId(int id);

    List<Car> selectAbout(Car car);

    int selectCount();

    List<Car> selectNewAll();
}