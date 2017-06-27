package com.gb.service;

import com.gb.dao.WorkbookMapper;
import com.gb.pojo.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/6/9.
 */
@Service
public class WorkbookServiceImp implements WorkbookService {
    @Autowired
    private WorkbookMapper workbookMapper;

    @Override
    public List<Workbook> selectPayType() {
        return workbookMapper.selectPayType();
    }
}
