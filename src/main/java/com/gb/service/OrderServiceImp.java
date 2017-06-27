package com.gb.service;

import com.gb.dao.tbOrderMapper;
import com.gb.pojo.Item;
import com.gb.pojo.tbOrder;
import com.gb.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2017/6/5.
 */
@Service
public class OrderServiceImp implements OrderService{
    @Autowired
    private tbOrderMapper tbOrderMapper;

    @Override
    public PageUtil selectOrder(tbOrder tbOrder) {
        PageUtil orPage=new PageUtil();
        //查询总条数
        int i=tbOrderMapper.selectCount(tbOrder);
        //条件查询
        List<tbOrder> list=tbOrderMapper.selectOrderCat(tbOrder);
        orPage.setList(list);
        orPage.setTotalCount(i);
        return orPage;
    }

    @Override
    public List<tbOrder> selectOrderList() {

        return tbOrderMapper.selectOrderList();
    }

    @Override
    public void saveOrder(tbOrder tbOrder) {
        String uuid=UUID.randomUUID().toString();
        tbOrder.setOrderId(uuid);
        tbOrderMapper.insertSelective(tbOrder);
    }


}
