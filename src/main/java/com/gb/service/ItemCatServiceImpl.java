package com.gb.service;

import com.gb.dao.ItemCatMapper;
import com.gb.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    @Override
    public List<ItemCat> selectItemCat() {
        List<ItemCat> list=itemCatMapper.selectItemCat();
        return list;
    }
}
