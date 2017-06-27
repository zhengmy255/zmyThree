package com.gb.action;

import com.gb.pojo.ItemCat;
import com.gb.pojo.Tree;
import com.gb.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */
@Controller
@RequestMapping("itemcat")
public class ItemCatAction {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("selectItemCatList")
    @ResponseBody
    public List<ItemCat> selectItemCatList(){
        List<ItemCat> list=itemCatService.selectItemCat();
        ArrayList<ItemCat> treeList = new ArrayList<>();
        //一级节点
        ItemCat yiji = null;
        //子节点list
        ArrayList<ItemCat> childList = null;
        //子级节点
        ItemCat child = null;
        //节点的自定义属性 如 url等。。。
        HashMap<String, String> nodeAttr = null;
        for (int i = 0; i < list.size(); i++) {
            //pid== null 说明一级节点
            if (list.get(i).getParentId() == 0) {
                yiji = new ItemCat();
                yiji.setId(list.get(i).getId());
                yiji.setName(list.get(i).getName());
//                yiji.setIconCls(list.get(i).getIconCls());
                yiji.setCreated(list.get(i).getCreated());
                yiji.setUpdated(list.get(i).getUpdated());
                childList = new ArrayList<>();

                //循环遍历子节点
                for (int j = 0; j < list.size(); j++) {
                    //当前循环的节点的父级id 等于  上层循环节点的id
                    if (list.get(j).getParentId() != 0 &&
                            list.get(i).getId().equals(list.get(j).getParentId()) ) {
                        //实例化子节点
                        child = new ItemCat();
                        //节点属性赋值
                        child.setId(list.get(j).getId());
                        child.setName(list.get(j).getName());
                        child.setParentId(list.get(j).getParentId());
//                yiji.setIconCls(list.get(i).getIconCls());
                        child.setCreated(list.get(j).getCreated());
                        child.setUpdated(list.get(j).getUpdated());
                        //实例化 自定义节点属性map
//                        nodeAttr = new HashMap<>();
//                        nodeAttr.put("url", list.get(j).getUrl());
//                        child.setAttributes(nodeAttr);
                        //子节点list 添加 child节点
                        childList.add(child);

                        //循环递归调用
                        selectChildLista(list, child);
                    }
                }
                yiji.setChildren(childList);
                treeList.add(yiji);
            }
        }
        return treeList;
    }


    //封装递归方法
    private void selectChildLista(List<ItemCat> list,ItemCat prarentNode){
        //子节点list
        ArrayList<ItemCat> childList = new ArrayList<>();
        //子级节点
        ItemCat child = null;
        //节点的自定义属性 如 url等。。。
        HashMap<String, String> nodeAttr = null;
        //循环遍历子节点
        for (int j = 0; j < list.size(); j++) {
            //当前循环的节点的父级id 等于  上层循环节点的id
            if (list.get(j).getParentId() != 0 &&
                    prarentNode.getId().equals(list.get(j).getParentId()) ) {
                //实例化子节点
                child = new ItemCat();
                //节点属性赋值
                child.setId(list.get(j).getId());
                child.setName(list.get(j).getName());
                child.setParentId(list.get(j).getParentId());
//                yiji.setIconCls(list.get(i).getIconCls());
                child.setCreated(list.get(j).getCreated());
                child.setUpdated(list.get(j).getUpdated());
                //实例化 自定义节点属性map
//                nodeAttr = new HashMap<>();
//                nodeAttr.put("url", list.get(j).getUrl());
//                child.setAttributes(nodeAttr);
                //子节点list 添加 child节点
                childList.add(child);

                //递归调用查找子节点 n层
                selectChildLista(list, child);
            }
        }
        prarentNode.setChildren(childList);

    }




    @RequestMapping("selectItemCat")
    @ResponseBody
    public List<ItemCat> selectItemCat(){
        List<ItemCat> list=itemCatService.selectItemCat();
        return list;
    }

    //跳转到添加页面
    @RequestMapping("tocat")
    public String tocat(){
        return "item/itemCat";
    }


    @RequestMapping("selectItemCatTree")
    @ResponseBody
    public List<Tree> selectItemCatTree(){
        List<ItemCat> list=itemCatService.selectItemCat();
        ArrayList<Tree> treeList = new ArrayList<>();
        //一级节点
        Tree yiji = null;
        //子节点list
        ArrayList<Tree> childList = null;
        //子级节点
        Tree child = null;
        //节点的自定义属性 如 url等。。。
        HashMap<String, String> nodeAttr = null;
        for (int i = 0; i < list.size(); i++) {
            //pid== null 说明一级节点
            if (list.get(i).getParentId() == 0) {
                yiji = new Tree();
                yiji.setId(list.get(i).getId());
                yiji.setText(list.get(i).getName());
//                yiji.setIconCls(list.get(i).getIconCls());
                yiji.setState("open");
                childList = new ArrayList<>();

                //循环遍历子节点
                for (int j = 0; j < list.size(); j++) {
                    //当前循环的节点的父级id 等于  上层循环节点的id
                    if (list.get(j).getParentId() != null &&
                            list.get(i).getId().equals(list.get(j).getParentId()) ) {
                        //实例化子节点
                        child = new Tree();
                        //节点属性赋值
                        child.setId(list.get(j).getId());
                        child.setText(list.get(j).getName());
//                        child.setIconCls(list.get(j).getIconCls());
                        child.setPid(list.get(j).getParentId());
                        child.setState("open");
                        //实例化 自定义节点属性map
//                        nodeAttr = new HashMap<>();
//                        nodeAttr.put("url", list.get(j).getUrl());
//                        child.setAttributes(nodeAttr);
                        //子节点list 添加 child节点
                        childList.add(child);

                        //循环递归调用
                        selectChildList(list, child);
                    }
                }
                yiji.setChildren(childList);
                treeList.add(yiji);
            }
        }
        return treeList;
    }


    //封装递归方法
    private void selectChildList(List<ItemCat> list,Tree prarentNode){
        //子节点list
        ArrayList<Tree> childList = new ArrayList<>();
        //子级节点
        Tree child = null;
        //节点的自定义属性 如 url等。。。
        HashMap<String, String> nodeAttr = null;
        //循环遍历子节点
        for (int j = 0; j < list.size(); j++) {
            //当前循环的节点的父级id 等于  上层循环节点的id
            if (list.get(j).getParentId() != null &&
                    prarentNode.getId().equals(list.get(j).getParentId()) ) {
                //实例化子节点
                child = new Tree();
                //节点属性赋值
                child.setId(list.get(j).getId());
                child.setText(list.get(j).getName());
//                child.setIconCls(list.get(j).getIconCls());
                child.setPid(list.get(j).getParentId());
                child.setState("open");
                //实例化 自定义节点属性map
//                nodeAttr = new HashMap<>();
//                nodeAttr.put("url", list.get(j).getUrl());
//                child.setAttributes(nodeAttr);
                //子节点list 添加 child节点
                childList.add(child);

                //递归调用查找子节点 n层
                selectChildList(list, child);
            }
        }
        prarentNode.setChildren(childList);

    }

}
