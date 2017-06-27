package com.gb.util;

import com.gb.pojo.*;
import com.gb.pojo.Workbook;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/10.
 */
public class PoiZipUtil {


    public static void downZip(List<tbOrder> list, HttpServletRequest req, HttpServletResponse resp, String realPath,List<Workbook> zffs){
            List<String> fileName=new ArrayList<String>();
            String path=realPath+"zipwjj";
            int m=0;
            //定义每页几条
        for (int i = 0; i <=list.size() ; i++) {
            if(i==list.size() && i%500!=0){
                String excelName = PoiZipUtil.makeExcel(m, i, list, path,zffs);
                fileName.add(excelName);
            }

            if(i%500==0 && i!=0){
                String excelName = PoiZipUtil.makeExcel(m, i, list, path,zffs);
                m=i;
                fileName.add(excelName);
            }
        }
        ZipUtill.ZipFiles(req, resp, fileName, "Order.zip", path);
    }



    private static String makeExcel(int m, int j, List<tbOrder> list, String path,List<Workbook> zffs){
        List<tbOrder> list1 = list.subList(m, j);

        HSSFWorkbook workbook = new HSSFWorkbook();

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
        CellStyle cellStyle = PoiStyleUtil.titleStyle(workbook);

//        表头样式
        CellStyle cellStyle1 = PoiStyleUtil.topStyle(workbook);
        //根据支付方式进行创建并分sheet
        for (int i = 0 ; i < zffs.size() ; i++){
            int x = 2;
            HSSFSheet sheet = workbook.createSheet(zffs.get(i).getDescribe());

            //设置日期单元格格式
            PoiStyleUtil.dataStyle(workbook,sheet,8);
            PoiStyleUtil.dataStyle(workbook,sheet,7);
            PoiStyleUtil.dataStyle(workbook,sheet,6);
            PoiStyleUtil.dataStyle(workbook,sheet,9);
            PoiStyleUtil.dataStyle(workbook,sheet,10);
            PoiStyleUtil.dataStyle(workbook,sheet,11);

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
            for (int  o= 0; o < list1.size(); o++) {
                if (list1.get(o).getPay().getDescribe().equals(zffs.get(i).getDescribe())){
                    //创建行
                    HSSFRow row1 = sheet.createRow(y);
                    //创建单元格
                    row1.createCell(2).setCellValue(list1.get(o).getOrderId());
                    row1.createCell(3).setCellValue(list1.get(o).getPayment());
                    row1.createCell(4).setCellValue(list1.get(o).getPay().getDescribe());
                    row1.createCell(5).setCellValue(list1.get(o).getPostFee());
                    row1.createCell(6).setCellValue(list1.get(o).getCreateTime());
                    row1.createCell(7).setCellValue(list1.get(o).getUpdateTime());
                    row1.createCell(8).setCellValue(list1.get(o).getPaymentTime());
                    row1.createCell(9).setCellValue(list1.get(o).getConsignTime());
                    row1.createCell(10).setCellValue(list1.get(o).getEndTime());
                    row1.createCell(11).setCellValue(list1.get(o).getCloseTime());

                    row1.createCell(12).setCellValue(list1.get(o).getShippingName());
                    row1.createCell(13).setCellValue(list1.get(o).getShippingCode());
                    row1.createCell(14).setCellValue(list1.get(o).getOrderStatus().getDescribe());

                    row1.createCell(15).setCellValue(list1.get(o).getUserInfo().getUsername());
                    /*用户是否已经评价*/
                    row1.createCell(16).setCellValue(list1.get(o).getUserBuyerRate().getDescribe());
                    y++;
                }
            }
        }
        String adr = "Order"+m+".xls";
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }

        //excel文件名称
        String excelName = adr;

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(path + "/" + excelName));
            workbook.write(outputStream);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //                k++;

//        fileList.add(excelName);
        return excelName;
    }





    //设置页眉页脚
    private static void headerAndfooter(Sheet sheet, List<tbOrder> list){
        Header header = sheet.getHeader();
        header.setLeft("第一页");
        Footer footer = sheet.getFooter();
        footer.setRight( "总共 " + list.size() + " 条数据 ");
    }




    //设置单元格日期格式
    private static void dataStyle(HSSFWorkbook workbook, HSSFSheet sheet, int i) {
        HSSFCellStyle DateStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        DateStyle.setDataFormat(dataFormat.getFormat("yyyy/mm/dd"));
        DateStyle.setAlignment(HorizontalAlignment.CENTER);
        sheet.setDefaultColumnStyle(i,DateStyle);
    }

    //表头样式
    private static CellStyle titleStyle(HSSFWorkbook workbook){
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setColor(IndexedColors.RED.getIndex());
        font.setBold(true);
        //设置列头样式
        HSSFCellStyle titleCellStyleColumn = workbook.createCellStyle();
        titleCellStyleColumn.setFont(font);
        titleCellStyleColumn.setBorderLeft(BorderStyle.THIN);
        titleCellStyleColumn.setBorderRight(BorderStyle.THIN);
        titleCellStyleColumn.setBorderTop(BorderStyle.THIN);
        titleCellStyleColumn.setBorderBottom(BorderStyle.THIN);
        titleCellStyleColumn.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyleColumn.setFillForegroundColor((short) 15);// 设置背景色
        titleCellStyleColumn.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return titleCellStyleColumn;
    }

    //列名的样式
    private static CellStyle topStyle(HSSFWorkbook workbook){

        // createFont()创建字体并设置样式

        HSSFFont fontColumn = workbook.createFont();
        fontColumn.setFontHeightInPoints((short) 15);
        fontColumn.setColor(IndexedColors.BLACK.getIndex());
        fontColumn.setBold(true);

        //设置列头样式
        HSSFCellStyle cellStyleColumn = workbook.createCellStyle();
        cellStyleColumn.setFont(fontColumn);
        cellStyleColumn.setBorderLeft(BorderStyle.THIN);
        cellStyleColumn.setBorderRight(BorderStyle.THIN);
        cellStyleColumn.setBorderTop(BorderStyle.THIN);
        cellStyleColumn.setBorderBottom(BorderStyle.THIN);
        cellStyleColumn.setAlignment(HorizontalAlignment.CENTER);
        return cellStyleColumn;
    }


    //内容样式
    private static CellStyle contentStyle(HSSFWorkbook workbook){
        //内容样式

        HSSFFont fontColumn = workbook.createFont();
        fontColumn.setFontHeightInPoints((short) 10);
        fontColumn.setColor(IndexedColors.BLACK.getIndex());
        fontColumn.setBold(false);

        HSSFCellStyle cellStyleContent = workbook.createCellStyle();
        cellStyleContent.setFont(fontColumn);
        cellStyleContent.setBorderLeft(BorderStyle.THIN);
        cellStyleContent.setBorderRight(BorderStyle.THIN);
        cellStyleContent.setBorderTop(BorderStyle.THIN);
        cellStyleContent.setBorderBottom(BorderStyle.THIN);
        cellStyleContent.setAlignment(HorizontalAlignment.CENTER);

        return cellStyleContent;
    }

    //自定义列样式
    private static CellStyle lieStyle(HSSFWorkbook workbook){
        HSSFFont fontContent = workbook.createFont();
        fontContent.setFontHeightInPoints((short) 10);
        fontContent.setColor(IndexedColors.BLACK.getIndex());
        fontContent.setBold(false);

        HSSFCellStyle cellStyleContent = workbook.createCellStyle();
        cellStyleContent.setFont(fontContent);
        cellStyleContent.setBorderLeft(BorderStyle.THIN);
        cellStyleContent.setBorderRight(BorderStyle.THIN);
        cellStyleContent.setBorderTop(BorderStyle.THIN);
        cellStyleContent.setBorderBottom(BorderStyle.THIN);
        cellStyleContent.setAlignment(HorizontalAlignment.LEFT);
        cellStyleContent.setFillForegroundColor((short) 15);// 设置背景色
        cellStyleContent.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyleContent;
    }

    //设置表头和列名
    private static void titleRow(HSSFWorkbook workbook,Map<String,Integer> map,HSSFSheet sheet){
        //合并单元格（起始行，终止行，起始列，终止列  map.size()-1）
        CellRangeAddress cra=new CellRangeAddress(0, 0, 2,11 );
        sheet.addMergedRegion(cra);

        HSSFRow topRow = sheet.createRow(0);
        HSSFCell topCell = topRow.createCell(2);
        topCell.setCellValue("信息表");
        topCell.setCellStyle(titleStyle(workbook));

        // 创建表头并设置名称通过createRow(1)来获取表格的第二行
        HSSFRow columnRow = sheet.createRow(1);
        int m = 2;
        //创建表头单元格并对单元格循环赋值
        for(String s : map.keySet()){
            //设置宽度
            sheet.setColumnWidth(m,map.get(s));
            //创建单元格
            HSSFCell columnCell = columnRow.createCell(m);
            //给单元格赋值
            columnCell.setCellValue(s);
            //将表头的样式赋值给表头
            columnCell.setCellStyle(topStyle(workbook));
            m++;
        }
    }

}
