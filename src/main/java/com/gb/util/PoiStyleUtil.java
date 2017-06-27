package com.gb.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;

import java.util.List;


/**
 * Created by Administrator on 2017/6/9 0009.
 */
public class PoiStyleUtil {
    //设置页眉页脚
    public static void headerAndfooter(Sheet sheet, List list){
        Header header = sheet.getHeader();
        header.setLeft("第一页");
        Footer footer = sheet.getFooter();
        footer.setRight( "总共 " + list.size() + " 条数据 ");
    }




    //设置单元格日期格式
    public static void dataStyle(HSSFWorkbook workbook, HSSFSheet sheet, int i) {
        HSSFCellStyle DateStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        DateStyle.setDataFormat(dataFormat.getFormat("yyyy/mm/dd"));
        DateStyle.setAlignment(HorizontalAlignment.CENTER);
        sheet.setDefaultColumnStyle(i,DateStyle);
    }

    //表头样式
    public static CellStyle titleStyle(HSSFWorkbook workbook){
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
    public static CellStyle topStyle(HSSFWorkbook workbook){

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
    public static CellStyle contentStyle(HSSFWorkbook workbook){
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
    public static CellStyle lieStyle(HSSFWorkbook workbook){
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

}
