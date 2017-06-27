package com.gb.quartz;

import com.gb.pojo.Item;
import com.gb.pojo.tbMailHistory;
import com.gb.service.ItemService;
import com.gb.service.TbMailHistoryService;
import com.gb.util.IpUtil;
import com.gb.util.JavaMailUtil;
import com.gb.util.NewPoiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8 0008.
 */
public class QuartzJobs  {

    /*利用RequestContextListener监听器获取request对象的方式一 */
    @Autowired
    private HttpServletRequest request;


    @Autowired
    private ItemService itemService;

    @Autowired
    private TbMailHistoryService  mailHistoryService;


    private static int counter = 0; //统计触发几次

    public void sendEmail()  {
        System.err.println("*****************");
        int isSuccess = 0;

        //Request
        /*利用RequestContextListener监听器获取request对象的方式二 */
    //    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        List<Item> itemList = itemService.exportExcel();

//        String path = request.getServletContext().getRealPath("/");

        String pp = "D:\\IdeaProjects\\ty-parent\\ty-modules\\md-backstage\\target\\md-backstage";
        String excelPath =   NewPoiUtil.downExcel(itemList,pp);

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
        mailHistoryService.addMailHistroy(mailHistory);

        System.err.println("(" + counter++ + ")");
    }



    //定时任务
    public void printTime(){
        System.err.print("------------------------------------"+new Date());
    }


}
