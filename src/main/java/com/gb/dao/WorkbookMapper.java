package com.gb.dao;

import com.gb.pojo.Workbook;
import java.math.BigDecimal;
import java.util.List;

public interface WorkbookMapper {
    int deleteByPrimaryKey(BigDecimal id);

    int insert(Workbook record);

    int insertSelective(Workbook record);

    Workbook selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(Workbook record);

    int updateByPrimaryKey(Workbook record);

    List<Workbook> selectPayType();

}