package com.gb.action;

import com.alibaba.fastjson.JSON;
import com.gb.pojo.Item;
import com.gb.pojo.tbMailHistory;
import com.gb.service.ItemService;
import com.gb.service.TbMailHistoryService;
import com.gb.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by admin on 2017/6/2.
 */
@Controller
@RequestMapping("item")
public class ItemAction {
    @Autowired
    private ItemService itemService;
    @Autowired
    private TbMailHistoryService tbMailHistoryService;


    @RequestMapping("upImg")
    @ResponseBody
    public ModelAndView upImg(HttpServletRequest request, @RequestParam("upfile") MultipartFile file){
        ModelAndView mv=new ModelAndView();

        //获取上传的文件名称
        String filename = file.getOriginalFilename();

        //获取项目中 uploadImg文件夹的真实路径  --上传图片存放到web服务器tomcat中
        String realPath = request.getServletContext().getRealPath("/uploadImg");
        //判断文件夹是否存在
        File f = new File(realPath);
        if (!f.exists()) {
            f.mkdirs();
        }
        InputStream inputStream  = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        //装饰设计模式
        try {
            //文件输入流
            inputStream = file.getInputStream();
            bis = new BufferedInputStream(inputStream);
            //截取文件后缀
            String suffix =  filename.substring(filename.lastIndexOf("."));
            fos = new FileOutputStream(realPath+"/"+ UUID.randomUUID().toString()+suffix);
            bos = new BufferedOutputStream(fos);
            byte [] b = new  byte[2048];
            int s = 0;
            while( (s = bis.read(b)) != -1){
                bos.write(b, 0, s);
                bos.flush();
            }
            mv.addObject("msg","上传文件成功");
            mv.setViewName("ok");
        } catch (IOException e) {
            e.printStackTrace();
            mv.addObject("msg","上传文件失败");
            mv.setViewName("error");
        }finally{
            try {
                if (bos != null) {
                    bos.flush();
                    bos.close();
                    bos = null;
                }
                if (fos  != null) {
                    fos.flush();
                    fos.close();
                    fos = null;
                }
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (inputStream != null) {
                    inputStream.close();
                    inputStream = null;
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        return mv;
    }


    //导出Excel
    //根据条件导出Excel
    @RequestMapping("exportExcel")
    @ResponseBody
    public ReturnJson exportExcel(HttpServletRequest request, HttpServletResponse response){

        ReturnJson rj = new ReturnJson();

        List<Item> itemList = itemService.exportExcel();

        //邮件是否发送成功
        int isSuccess = 0;

        if (null != itemList && itemList.size() > 0 ){
            String realPath = request.getServletContext().getRealPath("/");

            String excelPath =   NewPoiUtil.downExcel(itemList,realPath);

            System.err.println(excelPath);

            tbMailHistory mailHistory = new tbMailHistory();
            //发件人
            mailHistory.setAddresser("yangpei310@163.com");
            //密码
            mailHistory.setSendPwd("1018527104");
            //收件人
            mailHistory.setRecipients("2391515491@qq.com");
            //抄送人
            mailHistory.setToRecipients("1521170425@qq.com");
            //密送人
            mailHistory.setsecretRecipients("2391515491@qq.com");
            //邮件主题
            mailHistory.setMailStyle("商品信息表");
           //邮件正文
           // mailHistory.setMailContent("郑梦圆___商品信息excel<a href='http://weibo.com/login.php'>微博</a>");
            //发件人名称
            mailHistory.setEnterName("账上");
            //附件名称
            mailHistory.setAccessoryName(excelPath);

            isSuccess =  JavaMailUtil.sendJavaMail(request,mailHistory);
            //是否成功标志
            mailHistory.setIsSuccessful((short) isSuccess);
            //添加ip
            mailHistory.setIpAddress(IpUtil.getIpAddr(request));
            //邮件发送时间
            mailHistory.setCreatedTime(new Date());


            //mailHistory存储到 邮件历史记录表
            tbMailHistoryService.addMailHistroy(mailHistory);


        }



        if (isSuccess == 0){
            rj.setMsg("email发送成功");
            rj.setSuccess(true);
        }else {
            rj.setMsg("email发送失败");
        }
        return rj;
    }



    @RequestMapping("selectItem")
    @ResponseBody
    public DataGridJson selectItem(HttpServletRequest request, Integer page, Integer rows, String sort, String order, Item item){
        DataGridJson dj=new DataGridJson();
        item.setStart((page-1)*rows);
        item.setEnd((page*rows)+1);
        item.setSort(sort);
        item.setOrder(order);
        PageUtil rePage=new PageUtil();
        rePage=itemService.selectItemCat(item);
        dj.setRows(rePage.getList());
        dj.setTotal(rePage.getTotalCount());
        return dj;
    }
    //单元格修改
    @RequestMapping("editorUpdate")
    @ResponseBody
    public ReturnJson editorUpdate(String rowStr){
        ReturnJson rj=new ReturnJson();

        Item Item = JSON.parseObject(rowStr, Item.class);
        Item.setUpdated(new Date());
        long l=itemService.updateItem(Item);
        if(l>0){
            rj.setMsg("修改成功");
        }else{
            rj.setMsg("不行就拉到");
        }
        return rj;
    }


    //批量删除
    @RequestMapping("deleteItem")
    @ResponseBody
    public ReturnJson deleteItem(HttpServletRequest request, HttpServletResponse response, String strId){
        ReturnJson rj=new ReturnJson();
        Boolean boo=itemService.deleteItem(request,strId);
        if(boo){
            rj.setMsg("删除成功");
        }else{
            rj.setMsg("不行就拉到");
        }
        return rj;
    }

    //新增商品
    @RequestMapping("saveItem")
    @ResponseBody
    public ReturnJson saveItem(Item item){
        ReturnJson rj=new ReturnJson();
        item.setCreated(new Date());
        item.setUpdated(new Date());
        int i=itemService.saveItem(item);
        if(i>0){
            rj.setMsg("成功");
        }else{
            rj.setMsg("失败");
        }
        return rj;
    }


    //跳转到添加页面
    @RequestMapping("toadd")
    public String toadd(){
        return "item/add";
    }
}
