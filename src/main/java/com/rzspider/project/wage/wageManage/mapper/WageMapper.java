package com.rzspider.project.wage.wageManage.mapper;

import com.rzspider.project.wage.wageManage.domain.Wage;

import java.util.List;

public interface WageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Wage record);

    int insertSelective(Wage record);

    Wage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Wage record);

    int updateByPrimaryKey(Wage record);
    List<Wage> selectAll();
    int batchDeleteWageManage(Integer[] ids);
}