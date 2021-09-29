package com.rzspider.project.deal.dealManage.mapper;

import com.rzspider.project.deal.dealManage.domain.Trader;

import java.util.List;

public interface TraderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Trader record);

    int insertSelective(Trader record);

    Trader selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Trader record);

    int updateByPrimaryKey(Trader record);

    List<Trader> selectAll();
}