package com.gb.action;

import com.gb.pojo.Item;
import com.gb.pojo.Workbook;
import com.gb.pojo.tbOrder;
import com.gb.service.OrderService;
import com.gb.service.WorkbookService;
import com.gb.util.DataGridJson;
import com.gb.util.PageUtil;
import com.gb.util.PoiZipUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Created by admin on 2017/6/5.
 */
@Controller
@RequestMapping("order")
public class OrderAction
{
    @Autowired
    private OrderService orderService;
    @Autowired
    private WorkbookService workbookService;



    //调用新增页面
    @RequestMapping("chooseFile")
    public String chooseFile(){
        return "chooseFile";
    }



    /**
     * 读取zip文件内容
     */
    @RequestMapping(value = "readZip",method = RequestMethod.POST)
    @ResponseBody
    public void readZip(HttpServletRequest req,@RequestParam("upexcel") MultipartFile files) {
        //存放多个excel的文件名
        List<String> strList=new ArrayList<>();
        //存放所有的excel中所有的item数据
        List<tbOrder> list=new ArrayList<tbOrder>();
        String realPath = req.getServletContext().getRealPath("/");
        //path 多个excel存放的文件夹路径
        String path=realPath+"/"+"aa";

        /*******MultipartFile 转换成  File  **************/

        File ff = null;
        try {
            //File类中jdk创建临时文件的方法 createTempFile("临时文件名称","临时文件的后缀名")
            //如果后缀名为null采用默认后缀名.tmp
            ff=File.createTempFile("tmp", null);
            //      ff=File.createTempFile("tmp", ".zip");
            // 将压缩包files赋值给ff空临时文件  transferTo()转换文件内容
            files.transferTo(ff);
            //临时文件使用完成之后自动删除
            ff.deleteOnExit();
        } catch (IOException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        /*******MultipartFile 转换成  File  **************/

        //****************************************
        //File fil = new File("D:\\itemdent.zip");
        String fileName=null;
        /*******ZipInputStream 压缩字节输入流 读取 ff该File文件  **************/
        ZipInputStream zipIn = null;
        try {
//        	new FileInputStream(fil)

            Charset gbk = Charset.forName("gbk");
            zipIn = new ZipInputStream(new FileInputStream(ff),gbk);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /*******ZipInputStream 压缩字节输入流 读取 ff该File文件  **************/

        ZipEntry zipEn = null;
        ZipFile zfil = null;
        try {
//            zfil = new ZipFile("D:\\itemdent.zip");
            zfil = new ZipFile(ff);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            while ((zipEn = zipIn.getNextEntry()) != null) {
                if (!zipEn.isDirectory()) { // 判断此zip项是否为目录
                    System.out.println(zipEn.getName() + ":\t");
                    /**
                     * 把是文件的zip项读出缓存，
                     * zfil.getInputStream(zipEn)：返回输入流读取指定zip文件条目的内容 zfil：new
                     * ZipFile();供阅读的zip文件 zipEn：zip文件中的某一项
                     */
                    fileName=zipEn.getName();
                    strList.add(fileName);
                    File f = new File(path);

                    if (!f.exists()){
                        f.mkdirs();
                    }

                    OutputStream zos = new FileOutputStream(f+"/"+fileName);
                    InputStream in = zfil.getInputStream(zipEn);



                    byte[] b = new byte[3072];
                    int s = 0 ;
                    while (-1!=(s = in.read(b))) {
                        zos.write(b);
                        zos.flush();
                    }
                    in.close();
                    zos.close();
                }
                zipIn.closeEntry();// 关闭当前打开的项
            }
            File file =null;
            for (int a = 0; a < strList.size(); a++) {
                file = new File(path+"/"+strList.get(a));

                FileInputStream input = new FileInputStream(file);

                org.apache.poi.ss.usermodel.Workbook workbook = null;
                Sheet sheet = null;
                Row row = null;
                Cell cell = null;
                workbook = new HSSFWorkbook(input);

                int numberOfSheets = workbook.getNumberOfSheets();
                System.out.println("====================excel表格中sheet的数量："+numberOfSheets);

                //获取文档表格
                sheet = workbook.getSheetAt(0);
                //获取sheet的总行数
                // int rowsNum = sheet.getLastRowNum();
                //获取sheet的总列数
                int colsNum = sheet.getRow(1).getLastCellNum();

                String cellValue ;
                for(int i = 2 ; sheet.getRow(i)!=null ; i++){
                   // Item item=new Item();
                    tbOrder order=new tbOrder();
                    //获取行对象
                    row = sheet.getRow(i);
                    //从第一列开始查下标为0
                    for(int j = 3 ; j < colsNum ; j++){
                        //获取单元格对象
                        cell = row.getCell(j);
                        //将对应的单元格信息读取出来后赋值给对应的属性
                        if(j==3){//订单金额
                            cellValue = cell.getStringCellValue();
                            order.setPayment(cellValue);
                        }
                        if(j==4){//支付方式
                            cellValue = cell.getStringCellValue();
                            order.setPayment(cellValue);
                        }
                        if(j==5){//邮费
                            //将单元格内容设置为String，取的时候再转其他所需要的类型
                            cell.setCellType(CellType.STRING);;
                            cellValue = cell.getStringCellValue();
                            Integer valueOf = Integer.valueOf(cellValue);
                            BigDecimal val=new BigDecimal(valueOf);
                            order.setPaymentType(val);
                        }

                    }
                    //将对象放入集合中
                    list.add(order);
                }
            }

            //批量新增order集合   循环新增未批量
            for (int i = 0; i < list.size(); i++) {
                orderService.saveOrder(list.get(i));
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                zfil.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }



    /**
     * 跳转到添加页面
     * @return
     */
    //跳转到添加页面
    @RequestMapping("toaddOrder")
    public String toaddOrder(){
        return "order/addOrder";
    }








    //压缩下载方法

    @RequestMapping("exportExcelZip")
    public void exportExcelZip(HttpServletRequest request, HttpServletResponse response){
        //查询所有信息集合
        List<tbOrder> orderInfos = orderService.selectOrderList();
        String realPath = request.getServletContext().getRealPath("/");
        List<Workbook> zffs =  workbookService.selectPayType();
        PoiZipUtil.downZip(orderInfos,request,response,realPath,zffs);
    }






    //跳转到订单页面
    @RequestMapping("toorder")
    public String toOrder(){
        return "order/orderShow";
    }
    //页面加载查询订单表
    @RequestMapping("selectOrder")
    @ResponseBody
    public DataGridJson selectOrder(HttpServletRequest request, Integer page, Integer rows, String sort, String order, tbOrder tbOrder){
        DataGridJson dj=new DataGridJson();
        tbOrder.setStart((page-1)*rows);
        tbOrder.setEnd((page*rows)+1);
        tbOrder.setSort(sort);
        tbOrder.setOrder(order);
        PageUtil orPage=new PageUtil();
        orPage=orderService.selectOrder(tbOrder);
        dj.setRows(orPage.getList());
        dj.setTotal(orPage.getTotalCount());
        return dj;
    }
}
