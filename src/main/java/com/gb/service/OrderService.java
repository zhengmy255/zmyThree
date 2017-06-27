package com.gb.service;

import com.gb.pojo.Item;
import com.gb.pojo.tbOrder;
import com.gb.util.PageUtil;

import java.util.List;

/**
 * Created by admin on 2017/6/5.
 */
public interface OrderService {
    PageUtil selectOrder(tbOrder tbOrder);

    List<tbOrder> selectOrderList();


    void saveOrder(tbOrder tbOrder);
}
