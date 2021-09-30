package com.rzspider.project.salary.salarymanage.mapper;

import com.rzspider.project.salary.salarymanage.domain.Salary;

import java.util.List;

public interface SalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Salary record);

    int insertSelective(Salary record);

    Salary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Salary record);

    int updateByPrimaryKey(Salary record);

    List<Salary> selectAll();

    int selectCount();
}