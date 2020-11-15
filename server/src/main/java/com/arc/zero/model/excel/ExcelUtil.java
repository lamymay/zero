package com.arc.zero.model.excel;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/11/1 16:13
 */
public class ExcelUtil {

    public static void exportExcel(HttpServletResponse response, Workbook wb,
                                   String fileName) throws IOException {
        // 如果文件名有中文，必须URL编码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        // ContentType 可以不设置
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        wb.write(response.getOutputStream());
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }


    public static void exportSSHExcel(HttpServletResponse response, Workbook wb,
                                      String fileName) {
        try {
            // 如果文件名有中文，必须URL编码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.reset();
            // ContentType 可以不设置
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            wb.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void exportSSHFExl(HttpServletResponse response, Workbook wb,
                                     String fileName) throws IOException {
        OutputStream os = null;
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/force-download"); // 设置下载类型
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName); // 设置文件的名称
            os = response.getOutputStream(); // 输出流
            wb.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static final String EMPTY = "";

    /**
     * 读取Excel表格表头的内容
     *
     * @return String 表头内容的数组
     * @author zengwendong
     */
    public static String[] readExcelTitle(Workbook wb) throws Exception {
        if (wb == null) {
            throw new Exception("Workbook对象为空！");
        }
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(0);
        // 标题总列数
        int colNum = row.getPhysicalNumberOfCells();
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = row.getCell(i).getCellFormula();
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     *
     * @return Map 包含单元格数据内容的Map对象
     * @author zengwendong
     */
    public static List<Map<Integer, String>> readExcelContent(ExcelHelper excelHelper) throws Exception {
        if (excelHelper == null || excelHelper.getSheet() == null
                || excelHelper.getStartLine() == null || excelHelper.getPageSize() == null) {
            throw new RuntimeException("参数错误");
        }
        List<Map<Integer, String>> content = new ArrayList<>();
        // 得到总行数
        int rowNum = excelHelper.getSheet().getLastRowNum();
        Row row = excelHelper.getSheet().getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = excelHelper.getStartLine(); i < excelHelper.getPageSize() + excelHelper.getStartLine() && i <= rowNum; i++) {
            row = excelHelper.getSheet().getRow(i);
            Map<Integer, String> cellValue = new HashMap<>(colNum);
            for (int j = 0; j < colNum; j++) {
                if (row == null) {
                    continue;
                }
                String obj = getStringVal(row.getCell(j));
                cellValue.put(j, obj);
            }
            content.add(cellValue);
        }

        return content;
    }

    private static String getStringVal(Cell cell) {
        if (cell == null) {
            return EMPTY;
        }
        return cell.getStringCellValue();

//        switch (cell.getCellType()){
//            case Cell:
//                return cell.getBooleanCellValue() ? "TRUE" : "FALSE";
//            case Cell.CELL_TYPE_FORMULA:
//                return cell.getCellFormula();
//            case Cell.CELL_TYPE_NUMERIC:
//                cell.setCellType(Cell.CELL_TYPE_STRING);
//                return cell.getStringCellValue();
//            case CellCELL_TYPE_STRING:
//                return cell.getStringCellValue();
//            default:return EMPTY;
//        }
    }

    public static void writeExcelContent(Sheet sheet, List<Map<Integer, String>> dataList) {
        if (sheet == null) {
            throw new RuntimeException();
        }
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        int totalCount = sheet.getPhysicalNumberOfRows();
        for (int i = totalCount; i < dataList.size() + totalCount; i++) {
            Map<Integer, String> data = dataList.get(i - totalCount);
            if (data != null) {
                Row row = sheet.createRow(i);
                for (int j = 0; j < data.size(); j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(data.get(j));
                }
            }
        }

    }
}
