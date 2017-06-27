package com.gb.service;

import com.gb.dao.tbMailHistoryMapper;
import com.gb.pojo.tbMailHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2017/6/7.
 */
@Service
public class TbMailHistoryServiceImp implements TbMailHistoryService {
    @Autowired
    private tbMailHistoryMapper tbMailHistoryMapper;

    @Override
    public void addMailHistroy(tbMailHistory mailHistory) {
        tbMailHistoryMapper.insertSelective(mailHistory);
    }
}
