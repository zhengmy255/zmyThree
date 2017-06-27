package com.gb.dao;

import com.gb.pojo.tbMailHistory;
import java.math.BigDecimal;

public interface tbMailHistoryMapper {
    int deleteByPrimaryKey(BigDecimal seqMail);

    int insert(tbMailHistory record);

    int insertSelective(tbMailHistory record);

    tbMailHistory selectByPrimaryKey(BigDecimal seqMail);

    int updateByPrimaryKeySelective(tbMailHistory record);

    int updateByPrimaryKey(tbMailHistory record);
}