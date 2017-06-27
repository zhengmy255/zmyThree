package com.gb.dao;

import com.gb.pojo.tbOrder;

import java.util.List;

public interface tbOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(tbOrder record);

    int insertSelective(tbOrder record);

    tbOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(tbOrder record);

    int updateByPrimaryKey(tbOrder record);

    int selectCount(tbOrder tbOrder);

    List<tbOrder> selectOrderCat(tbOrder tbOrder);

    List<tbOrder> selectOrderList();
/*
新增待用
    void saveOrder(tbOrder tbOrder);
*/

}