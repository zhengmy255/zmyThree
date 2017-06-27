package com.gb.dao;

import com.gb.pojo.ItemCat;

import java.util.List;

public interface ItemCatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItemCat record);

    int insertSelective(ItemCat record);

    ItemCat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItemCat record);

    int updateByPrimaryKey(ItemCat record);

    List<ItemCat> selectItemCat();

}