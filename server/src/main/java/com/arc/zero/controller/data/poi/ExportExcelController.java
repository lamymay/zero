package com.arc.zero.controller.data.poi;

import com.arc.utils.Assert;
import com.arc.zero.model.excel.ExcelEntity;
import com.arc.zero.model.excel.ExcelRequest;
import com.arc.zero.model.excel.ExcelUtil;
import com.arc.zero.service.excel.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lamymay
 * @date: 2018/5/5
 */
@Slf4j
@RestController
@RequestMapping("/excel/export")
public class ExportExcelController {

    @Autowired
    private ExcelService excelService;

    @RequestMapping(value = "/v1/{id}", method = RequestMethod.GET)
    public Object export2(@PathVariable Long id, HttpServletResponse response) throws IOException {
        long t1 = System.currentTimeMillis();
        Integer count = excelService.countByJoinId(id);
        long t2 = System.currentTimeMillis();
        ExcelRequest excelEntity = new ExcelRequest();
        excelEntity.setPageSize(100_0000);
        excelEntity.setCurrentPage(1);
        List<ExcelEntity> excelEntities = excelService.listPage(excelEntity);

        long t3 = System.currentTimeMillis();
        System.out.println(excelEntities.size());

        log.debug("count{}条数据，耗时={}ms", count, (t2 - t1));
        log.debug("总数据量{},list{}条数据，耗时={}ms", count, excelEntity.getPageSize(), (t3 - t2));

        return 1;
    }

    /**
     * 导出Excel
     *
     * @param id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/v2/{id}", method = RequestMethod.GET)
    public String export(@PathVariable Long id, HttpServletResponse response) throws IOException {
        long t1 = System.currentTimeMillis();
        Assert.notNull(id);

        int pageSize = 50000;
        int sheetNumber = 1;


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        HSSFWorkbook workbook = new HSSFWorkbook();        // 创建一个工作薄

        //一个sheet写入5w 数据
        Integer count = excelService.countByJoinId(id);
        int sheets;//sheets 数目
        int remainder;//余数

        if (count != 0) {
            sheets = count / pageSize;//总页数
            remainder = count % pageSize;
            if (sheets <= 0 || remainder > 0) {
                sheets = sheets + 1;
            }

            List<ExcelEntity> records = new ArrayList<>(50000);

            log.info("##################################总sheet数目 {},--余数 sheetNumber {}", sheets, sheetNumber);
            ExcelRequest excelEntity = new ExcelRequest();
            excelEntity.setId(id);
            for (sheetNumber = 1; sheetNumber <= sheets; sheetNumber++) {
                excelEntity.setCurrentPage(sheetNumber);
                excelEntity.setPageSize(pageSize);

                //一次循环完成一个sheet  sheet++
                HSSFSheet sheet1 = workbook.createSheet("扫码回执信息" + sheetNumber);
                createTitle(workbook, sheet1);

                records = excelService.listPage(excelEntity);

                //新增数据行，并且设置单元格数据
                int rowNum = 1;
                for (ExcelEntity qrCodeRecord : records) {
                    // log.info("数据导出，时间{}，数据{}", new Date(), qrCodeRecord);
                    HSSFRow row = sheet1.createRow(rowNum);
                    row.createCell(0).setCellValue(qrCodeRecord.getUserId() == null ? "" : qrCodeRecord.getTitle());
                    row.createCell(1).setCellValue(qrCodeRecord.getId());
                    row.createCell(2).setCellValue(qrCodeRecord.getRemark() == null ? "" : formatter.format(qrCodeRecord.getRemark()));
                    rowNum++;
                }
            }
            String fileName = "数据列表列表.xls";
            long t2 = System.currentTimeMillis();

            log.debug("导出{}条数据，耗时={}ms", count, (t2 - t1));
            ExcelUtil.exportExcel(response, workbook, fileName);
        }
        return "download excel";
    }

    //2821408

    public static void main(String[] args) {
        System.out.println("2821408".length());
        System.out.println(3000000 - 2999759);
    }

    private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(1, 12 * 256);
        sheet.setColumnWidth(3, 17 * 256);
        //设置为居中加粗
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
//        font.setFontHeight();
        style.setFont(font);
        HSSFCell cell0;
        cell0 = row.createCell(0);
        cell0.setCellValue("微信昵称");
        cell0.setCellStyle(style);


        HSSFCell cell1;
        cell1 = row.createCell(1);
        cell1.setCellValue("openId");
        cell1.setCellStyle(style);

        HSSFCell cell2;
        cell2 = row.createCell(2);
        cell2.setCellValue("参与时间");
        cell2.setCellStyle(style);
    }

}





