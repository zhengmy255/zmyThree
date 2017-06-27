package com.gb.service;

import com.gb.dao.ItemMapper;
import com.gb.pojo.Item;
import com.gb.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
@Service
public class ItemServiceImp implements ItemService{
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public long updateItem(Item item) {
        item.setUpdated(new Date());
        long i = itemMapper.updateByPrimaryKeySelective(item);
        return i;
    }

    @Override
    public int saveItem(Item item) {
        item.setCreated(new Date());
        item.setUpdated(new Date());
        int i=itemMapper.insertSelective(item);
        return i;
    }

    /**
     * 导出,无条件
     * @return
     */
    @Override
    public List<Item> exportExcel() {

        return itemMapper.exportExcel();
    }

    @Override
    public Boolean deleteItem(HttpServletRequest request, String strId) {
        String[] split = strId.split(",");
        List<String> idList=new ArrayList();
        for (int i=0;i<split.length;i++){
            idList.add(split[i].trim());
        }
        try {
            int c = itemMapper.deleteItemById(idList);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    @Override
    public PageUtil selectItemCat(Item item) {
        PageUtil rePage=new PageUtil();
        int i=itemMapper.selectCount(item);
        List<Item> list=itemMapper.selectItemCat(item);
        rePage.setList(list);
        rePage.setTotalCount(i);
        return rePage;
    }
}
