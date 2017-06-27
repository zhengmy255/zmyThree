package com.gb.quartz;

import com.gb.pojo.tbOrder;
import com.gb.pojo.Workbook;
import com.gb.service.OrderService;

import com.gb.service.WorkbookService;
import com.gb.util.NewPoiUtil;
import com.gb.util.PoiStyleUtil;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ejb.Schedule;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/6/9 0009.
 */


//注解 注入 定时任务类
@Component
public class ZhuJieQuartzJobs {
    //用来查询订单表,以便分sheet
    @Autowired
    private OrderService ordersService;
    //查询字典表类型
    @Autowired
    private WorkbookService workbookService;

    //注解版表达式只允许有6位
   // @Scheduled(cron = "0/20 * * * * ?")
    public void exportOrderExcel(){
//        System.out.print("####################"+new Date());

        List<tbOrder> orderInfoList =  ordersService.selectOrderList();


        //支付方式 区分 sheet
        //select * from zx_workbook where type = 'zffs'
        List<Workbook> zffs =  workbookService.selectPayType();



        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        //合并单元格（起始行，终止行，起始列，终止列  map.size()-1）
        CellRangeAddress cra=new CellRangeAddress(0, 2, 2,16 );
        //增加map集合，用来作为Excel表格中的表头
        Map<String,Integer> map = new LinkedHashMap<String, Integer>();
        map.put("订单id", 5000);
        map.put("支付金额", 5000);
        map.put("支付方式", 5000);
        map.put("邮费", 5000);
        map.put("创建时间", 5000);
        map.put("更新时间",5000);
        map.put("付款时间",5000);
        map.put("发货时间",5000);
        map.put("交易完成时间",5000);
        map.put("交易关闭时间",5000);
        map.put("物流名称",5000);
        map.put("物流编号",5000);
        map.put("订单状态", 5000);
        map.put("订单买家", 5000);
        map.put("是否已评价", 5000);

        /*标题样式*/
        CellStyle cellStyle = PoiStyleUtil.titleStyle(hssfWorkbook);

//        表头样式
        CellStyle cellStyle1 = PoiStyleUtil.topStyle(hssfWorkbook);
        //根据支付方式进行创建并分sheet
        for (int i = 0 ; i < zffs.size() ; i++){
            int x = 2;
            HSSFSheet sheet = hssfWorkbook.createSheet(zffs.get(i).getDescribe());

            //设置日期单元格格式
            PoiStyleUtil.dataStyle(hssfWorkbook,sheet,8);
            PoiStyleUtil.dataStyle(hssfWorkbook,sheet,7);
            PoiStyleUtil.dataStyle(hssfWorkbook,sheet,6);
            PoiStyleUtil.dataStyle(hssfWorkbook,sheet,9);
            PoiStyleUtil.dataStyle(hssfWorkbook,sheet,10);
            PoiStyleUtil.dataStyle(hssfWorkbook,sheet,11);

            //设置标题
            sheet.addMergedRegion(cra);
            HSSFRow topRow = sheet.createRow(0);
            HSSFCell topCell = topRow.createCell(2);
            topCell.setCellValue("订单信息表");
            topCell.setCellStyle(cellStyle);
            //设置表头
            HSSFRow row = sheet.createRow(3);
            for (String s : map.keySet()) {
                //设置宽度
                sheet.setColumnWidth(x,map.get(s));
                //创建单元格
                HSSFCell columnCell = row.createCell(x);
                //给单元格赋值
                columnCell.setCellValue(s);
                //将表头的样式赋值给表头
                columnCell.setCellStyle(cellStyle1);
                x++;
            }

            int y = 4;
            //订单数据
            for (int  j= 0; j < orderInfoList.size(); j++) {

                System.out.print("======================="+orderInfoList.get(j).getPay().getDescribe());
                System.out.print("****************************"+zffs.get(i).getDescribe());
                if (orderInfoList.get(j).getPay().getDescribe().equals(zffs.get(i).getDescribe())){
                    //创建行
                    HSSFRow row1 = sheet.createRow(y);
                    //创建单元格
                    row1.createCell(2).setCellValue(orderInfoList.get(j).getOrderId());
                    row1.createCell(3).setCellValue(orderInfoList.get(j).getPayment());
                    row1.createCell(4).setCellValue(orderInfoList.get(j).getPay().getDescribe());
                    row1.createCell(5).setCellValue(orderInfoList.get(j).getPostFee());
                    row1.createCell(6).setCellValue(orderInfoList.get(j).getCreateTime());
                    row1.createCell(7).setCellValue(orderInfoList.get(j).getUpdateTime());
                    row1.createCell(8).setCellValue(orderInfoList.get(j).getPaymentTime());
                    row1.createCell(9).setCellValue(orderInfoList.get(j).getConsignTime());
                    row1.createCell(10).setCellValue(orderInfoList.get(j).getEndTime());
                    row1.createCell(11).setCellValue(orderInfoList.get(j).getCloseTime());

                    row1.createCell(12).setCellValue(orderInfoList.get(j).getShippingName());
                    row1.createCell(13).setCellValue(orderInfoList.get(j).getShippingCode());
                    row1.createCell(14).setCellValue(orderInfoList.get(j).getOrderStatus().getDescribe());

                    row1.createCell(15).setCellValue(orderInfoList.get(j).getUserInfo().getUsername());
                    /*用户是否已经评价*/
                    row1.createCell(16).setCellValue(orderInfoList.get(j).getUserBuyerRate().getDescribe());
                    y++;
                }
            }
        }

        File f = new File("D:/orderList"+UUID.randomUUID()+".xls");

        try {
            hssfWorkbook.write(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

/*        for (OrderInfo order: orderInfoList) {
            System.err.print("----------------------------------------------"+order);
        }*/

    }


   // @Scheduled(cron = "0/5 * * * * ? *")
    public void exportOrderExcel2(){
        System.out.print("####################"+new Date());
    }


}
