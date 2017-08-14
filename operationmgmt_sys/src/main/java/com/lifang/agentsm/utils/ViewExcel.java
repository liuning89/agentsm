package com.lifang.agentsm.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.lifang.agentsm.model.EstateInfoAboutData;

public class ViewExcel extends AbstractExcelView {
    
    private List<EstateInfoAboutData> allData;
    
    public ViewExcel(List<EstateInfoAboutData> allData) {
        this.allData = allData;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        //String[] headers = {"城市","区域","版块","楼盘名称（小区）","有效库存数","浏览数","实景数","速销数","钥匙数","描述数","店推数","openHouse","带看数","跟进数","收藏数"};
        String[] headers = {"楼盘名称（小区）","有效库存数","实景数","速销数","带看数","店推数","浏览数","描述数","openHouse","跟进数","收藏数","总分"};
        // 声明一个工作薄
        workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet("data");
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
       /* style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);*/
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 13);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
       
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
//        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
//        comment.setAuthor("leno");
   
        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
           HSSFCell cell = row.createCell(i);
           cell.setCellStyle(style);
           HSSFRichTextString text = new HSSFRichTextString(headers[i]);
           cell.setCellValue(text);
        }
        //遍历集合数据，产生数据行
        Iterator<EstateInfoAboutData> it = allData.iterator();
        int index = 0;
        while (it.hasNext()) {
               index++;
               row = sheet.createRow(index);
               EstateInfoAboutData t = (EstateInfoAboutData) it.next();
               row.createCell(0).setCellValue(t.getEstateName());
               row.createCell(1).setCellValue(t.getYxkcNum());
               row.createCell(2).setCellValue(t.getSjNum());
               row.createCell(3).setCellValue(t.getSxNum());
               row.createCell(4).setCellValue(t.getDkNum());
               row.createCell(5).setCellValue(Integer.valueOf(t.getDtNum()));
               row.createCell(6).setCellValue(Integer.valueOf(t.getLlNum()));
               row.createCell(7).setCellValue(Integer.valueOf(t.getMsNum()));
               row.createCell(8).setCellValue(Integer.valueOf(t.getOpenHouse()));
               row.createCell(9).setCellValue(Integer.valueOf(t.getGjNum()));
               row.createCell(10).setCellValue(Integer.valueOf(t.getScNum()));
               Double totalScore=Integer.valueOf(t.getYxkcNum())+Integer.valueOf(t.getSjNum())+Integer.valueOf(t.getSxNum())*10
                       +Integer.valueOf(t.getDkNum())+Integer.valueOf(t.getDtNum())*0.5+Integer.valueOf(t.getLlNum())*0.5+
                       Integer.valueOf(t.getMsNum())*0.5+Integer.valueOf(t.getOpenHouse())*0.5+Integer.valueOf(t.getGjNum())*0.5+
                       Integer.valueOf(t.getScNum())*0.5;
               row.createCell(11).setCellValue(totalScore);
              /* row.createCell(0).setCellValue(t.getCityName());
               row.createCell(1).setCellValue(t.getDicName());
               row.createCell(2).setCellValue(t.getTownName());
               row.createCell(3).setCellValue(t.getEstateName());
               row.createCell(4).setCellValue(t.getYxkcNum());
               row.createCell(5).setCellValue(t.getLlNum());
               row.createCell(6).setCellValue(t.getSjNum());
               row.createCell(7).setCellValue(t.getSxNum());
               row.createCell(8).setCellValue(t.getYsNum());
               row.createCell(9).setCellValue(t.getMsNum());
               row.createCell(10).setCellValue(t.getDtNum());
               row.createCell(11).setCellValue(t.getOpenHouse());
               row.createCell(12).setCellValue(t.getDkNum());
               row.createCell(13).setCellValue(t.getGjNum());
               row.createCell(14).setCellValue(t.getScNum());*/
        }
        try {
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);

            // 设置response参数，可以打开下载页面
            response.reset();
            String filename="";
            if(allData.size()>0&&allData!=null){
                filename=allData.get(0).getCityName()+".xls";
            }
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((filename).getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }

            } catch (final IOException e) {
                throw e;
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
            
        } catch (IOException e) {
           e.printStackTrace();
        }
        
    }

}
