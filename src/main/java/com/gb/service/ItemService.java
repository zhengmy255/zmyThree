package com.gb.service;

import com.gb.pojo.Item;
import com.gb.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
public interface ItemService {
    PageUtil selectItemCat(Item item);

    long updateItem(Item item);

    Boolean deleteItem(HttpServletRequest request, String strId);

    int saveItem(Item item);

    List<Item> exportExcel();
}
