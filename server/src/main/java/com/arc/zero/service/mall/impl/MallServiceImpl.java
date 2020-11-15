package com.arc.zero.service.mall.impl;

import com.arc.core.model.domain.mall.Mall;
import com.arc.core.model.domain.mall.MallArea;
import com.arc.core.model.domain.mall.MallQuestion;
import com.arc.core.model.domain.mall.MallTask;
import com.arc.core.model.domain.system.SysFile;
import com.arc.zero.mapper.mall.MallAreaMapper;
import com.arc.zero.mapper.mall.MallMapper;
import com.arc.zero.mapper.system.SysFileMapper;
import com.arc.zero.service.mall.MallQuestionService;
import com.arc.zero.service.mall.MallService;
import com.arc.zero.service.mall.MallTaskService;
import com.arc.zero.utils.NameUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * @author: yechao
 * @date: 2018/12/26 11:31
 * @description:
 */
@Slf4j
@Service
public class MallServiceImpl implements MallService {


    @Resource
    private MallMapper mallMapper;

    @Resource
    private MallAreaMapper areaMapper;

    @Resource
    private MallQuestionService mallQuestionService;

    @Resource
    private MallTaskService taskService;

    @Resource
    private SysFileMapper fileMapper;

    @Override

    public Long save(Mall mall) {
        return mallMapper.save(mall) == 1 ? mall.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return mallMapper.delete(id);
    }

    @Override
    @Transactional
    public int update(Mall mall) {
        return mallMapper.update(mall);
    }

    @Override
    public Mall get(Long id) {
        return mallMapper.get(id);
    }

    @Override
    public List<Mall> list() {
        return mallMapper.list();
    }

    @Override
    public Boolean importExcel(Long fileId) {
        SysFile file = fileMapper.get(fileId);
        log.debug("path--->{}" + file.getPath());
        return importExcelToDb(file.getPath());
    }

    /**
     * 解析excel
     *
     * @param path
     * @return
     */
    private Boolean importExcelToDb(String path) {
        //1整sheet页解析好还是   2 解析后直接返回list对象？
        Map<Integer, List<String>> rowsMap = getRowsMap(path);
//        String cell = getCellValue(rowsMap, 0, 1);
        int yIndex = rowsMap.size();

        //@todo 可能会溢出？
        int xIndex = rowsMap.get(0).size();

        System.out.println("xIndex--yIndex=          " + xIndex + "  " + yIndex);
        //y 坐标比较准确， x坐标及其不准确
//        xIndex = 190;

        //按行取值， 则按行 迭代，--列编号递增，即固定y，增大x
        for (int y = 1; y < yIndex; y++) {

            Mall mall = new Mall();
            MallArea area = new MallArea();
            MallTask task = new MallTask();

            for (int x = 0; x < xIndex; x++) {
                log.debug("坐标 x,y（{}，{}）=   {}", x, y, getCellValue(rowsMap, x, y));
            }

            //mall信息处理
            mall.setTaskName(getCellValue(rowsMap, 2, y));
            mall.setMallName(getCellValue(rowsMap, 26, y));
            mall.setMallArea(getCellValue(rowsMap, 27, y));
            mall.setProvinceName(getCellValue(rowsMap, 27, y));
            mall.setCityName(getCellValue(rowsMap, 27, y));
            mall.setDistrictName(getCellValue(rowsMap, 27, y));
            mall.setTownName(NameUtil.getCodeInName(mall.getMallName()));
            mall.setNote("商场与地址地址是一对一的关系，现在暂时2019/01/15先在mall表中记录地址信息");

            mallMapper.save(mall);


            //mall area 信息入库
            String areaName = getCellValue(rowsMap, 23, y);
            area.setAreaName(NameUtil.getNameInName(areaName));
            area.setShortCode(NameUtil.getCodeInName(areaName));
            Integer areaResult = saveArea(area);

            areaName = getCellValue(rowsMap, 24, y);
            area.setAreaName(NameUtil.getNameInName(areaName));
            area.setShortCode(NameUtil.getCodeInName(areaName));
             areaResult += saveArea(area);

            areaName = getCellValue(rowsMap, 25, y);
            area.setAreaName(NameUtil.getNameInName(areaName));
            area.setShortCode(NameUtil.getCodeInName(areaName));
            areaResult +=   saveArea(area);



            //task 信息入库

            //0 工单id
            //1 任务id
            //2
            task.setWorkerId((long) NameUtil.getIntegerInCodeString(getCellValue(rowsMap, 0, y)));
            task.setMallId((long) NameUtil.getIntegerInCodeString(getCellValue(rowsMap, 1, y)));
            task.setTitle(getCellValue(rowsMap, 2, y));
            task.setTaskType(getCellValue(rowsMap, 3, y));

            //salary 报酬 Integer = double扩大100X
            task.setAdmin(getCellValue(rowsMap, 21, y));
            task.setDescription(getCellValue(rowsMap, 22, y));


            task.setUpdateDate(NameUtil.getDateCodeString(getCellValue(rowsMap, 11, y)));
            task.setCreateDate(NameUtil.getDateCodeString(getCellValue(rowsMap, 12, y)));

            task.setOnlineDate(NameUtil.getDateCodeString(getCellValue(rowsMap,"N",y)));//N
            task.setOfflineDate(NameUtil.getDateCodeString(getCellValue(rowsMap, 14, y)));//O=14

            task.setTaskFinishDate(NameUtil.getDateCodeString(getCellValue(rowsMap, 15, y)));//P=15

            task.setBeginDate(NameUtil.getDateCodeString(getCellValue(rowsMap, 16, y)));// Q=16
            task.setEndDate(NameUtil.getDateCodeString(getCellValue(rowsMap, 17, y)));//R=17

            task.setCustomerNumber(getCellValue(rowsMap,"AK",y));//AK
            task.setAdmin(getCellValue(rowsMap,"V",y));
            taskService.save(task);

            //question

            //题目1

            //从BR=69 开始 后面每隔4列就是一个问题
            for (int questionIndex = 69; questionIndex < yIndex; ) {
                //question信息入库
                MallQuestion question = new MallQuestion();
                question.setQuestion(getCellValue(rowsMap,questionIndex,y));//BR=69
                question.setAnswer(getCellValue(rowsMap, questionIndex+1, y));
                question.setReason(getCellValue(rowsMap, questionIndex+2, y));
                question.setNote(getCellValue(rowsMap, questionIndex+3, y));
                mallQuestionService.save(question);


                questionIndex += 4;

            }




        }
        //注意 行列都是从0开始的  小心越界
        return true;
    }

    private Integer saveArea(MallArea area) {
        try {
            return areaMapper.save(area);
        } catch (Exception e) {
            log.error("{}", e);
            e.printStackTrace();
            System.out.println("area 错误" + e.getCause());
        }
        return 0;

    }



//    /**
//     * 直接解析，返回数据集合，直接入库
//     *
//     * @param path
//     * @return
//     */
//    private  Map<BaseModel, List<Object>> getSheetToDomain(String path) {
//
//        // 打开指定位置的Excel文件
//        FileInputStream file = null;
//        try {
//            file = new FileInputStream(new File(path));
//            Workbook workbook = new XSSFWorkbook(file);
//
//            // 打开Excel中的第一个Sheet
//            Sheet sheet = workbook.getSheetAt(0);
//
//            // 读取Sheet中的数据  <key,value> <行数,<列数，值>>
//            Map<BaseModel, List<Object>> map = new HashMap<>();
//            Map<Integer, List<String>> data = new HashMap<>();
//
//            //i 表示行数 左上角为0行0列，坐标A1
//            int i = 0;
//            int x = 0;
//            for (Row row : sheet) { // 行
//                data.put(i, new ArrayList<String>());
//                for (Cell cell : row) { // 单元格
//                    switch (cell.getCellType()) { // 不同的数据类型
//                        //// 字符串类型
//                        case STRING:
//                            data.get(i).add(cell.getRichStringCellValue().getString());
//                            break;
//                        //数值类型
//                        case NUMERIC:
//                            if (DateUtil.isCellDateFormatted(cell)) {
//                                data.get(i).add(cell.getDateCellValue().toString());
//                                System.out.println("getDateCellValue------->" + cell.getDateCellValue());
//                            } else {
//                                System.out.println("数值类型 getNumericCellValue------->" + cell.getNumericCellValue());
//                                System.out.println("数值类型 ------->" + new Double(cell.getNumericCellValue()).toString());
//                                data.get(i).add(new Double(cell.getNumericCellValue()).toString());
//                            }
//                            break;
//                        //布尔类型
//                        case BOOLEAN:
//                            System.out.println("布尔类型 getBooleanCellValue------->" + cell.getBooleanCellValue());
//                            System.out.println("布尔类型  ------->" + new Boolean(cell.getBooleanCellValue()).toString());
//                            data.get(i).add(new Boolean(cell.getBooleanCellValue()).toString());
//                            break;
//                        //公式类型
//                        case FORMULA:
//                            data.get(i).add(cell.getCellFormula());
//                            break;
//                        //空白类型
//                        case BLANK:
//                            data.get(i).add("");
//                            break;
//                        default:
//                            //...;
//                            log.debug("进入了 default，行号={}", i);
//                            break;
//                    }
//                }
//                //https://www.cnblogs.com/jyyjava/p/8074322.html
//                //https://blog.csdn.net/holmofy/article/details/82532311
//                //https://www.baidu.com/baidu?wd=poi+excel+解析&tn=54002054_dg&ie=utf-8
//                i++;
//             x++;
//            }
//            log.debug("读取数据的行数={}，map大小=", i, data.size());
//
//
//            return data;
//        } catch (
//                FileNotFoundException e) {
//            e.printStackTrace();
//            return Collections.emptyMap();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Collections.emptyMap();
//        }
//    }

    /**
     * 获取页面中给定坐标的值
     *
     * @param rowsMap
     * @param x
     * @param y
     * @return
     */
    private String getCellValue(Map<Integer, List<String>> rowsMap, int x, int y) {
        try {
            return rowsMap.get(y).get(x);
        } catch (Exception e) {
            log.error("map中取({},{})值时候出错={}", x, y, e.getCause());
            return null;
        }
    }
    private String getCellValue(Map<Integer, List<String>> rowsMap, String x, int y) {
        try {
            return rowsMap.get(y).get(NameUtil.convertStringToNumberAddNegative(x));
        } catch (Exception e) {
            log.error("map中取({},{})值时候出错={}", x, y, e.getCause());
            return null;
        }
    }

    private int getX(String x) {
        return 0;
    }


    public static Map<Integer, List<String>> getRowsMap(String path) {

        // 打开指定位置的Excel文件
        FileInputStream file = null;
        try {
            file = new FileInputStream(new File(path));
            Workbook workbook = new XSSFWorkbook(file);

            // 打开Excel中的第一个Sheet
            Sheet sheet = workbook.getSheetAt(0);

            // 读取Sheet中的数据  <key,value> <行数,<列数，值>>
            Map<Integer, List<String>> data = new HashMap<>();

            //i 表示行数 左上角为0行0列，坐标A1
//            int i = 0;

            //最大列  x
            int xLength = sheet.getRow(0).getLastCellNum();
            System.out.println("getLastCellNum          " + xLength);


            //最小行号  y
            int yLength = sheet.getLastRowNum();
            System.out.println("getLastRowNum         " + yLength);


            System.out.println("getPhysicalNumberOfCells  " + sheet.getRow(0).getPhysicalNumberOfCells());
            System.out.println("getRowNum                " + sheet.getRow(0).getRowNum());
            System.out.println("getSheet                       " + sheet.getRow(0).getSheet());
            System.out.println("getZeroHeight             " + sheet.getRow(0).getZeroHeight());

//            for (Row row : sheet) {  // 行

            for (int y = 0; y <= yLength; y++) {
                //没一行new 一个list 保存数据
                data.put(y, new ArrayList<String>());

                //取y行
                Row row = sheet.getRow(y);
//                System.out.println(row.toString());
//                short height = row.getHeight();
                for (int x = 0; x < xLength; x++) {
                    Cell cell = row.getCell(x);
                    if (cell == null) {
//                        row.createCell(x);
                        System.out.println("当前cell 坐标(" + x + "," + y + ")  " + "  为空即null，已经创见列空单元格补全列");
                        data.get(y).add("");
                    } else {


                        switch (cell.getCellType()) { // 不同的数据类型
                            //// 字符串类型
                            case STRING:
                                data.get(y).add(cell.getRichStringCellValue().getString());
                                System.out.println(cell.getRichStringCellValue().getString());
                                break;
                            //数值类型
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    data.get(y).add(cell.getDateCellValue().toString());
                                    System.out.println("getDateCellValue------->" + cell.getDateCellValue());
                                } else {
                                    System.out.println("数值类型 getNumericCellValue------->" + cell.getNumericCellValue());
                                    System.out.println("数值类型 ------->" + new Double(cell.getNumericCellValue()).toString());
                                    data.get(y).add(new Double(cell.getNumericCellValue()).toString());
                                }
                                break;
                            //布尔类型
                            case BOOLEAN:
                                System.out.println("布尔类型 getBooleanCellValue------->" + cell.getBooleanCellValue());
                                System.out.println("布尔类型  ------->" + new Boolean(cell.getBooleanCellValue()).toString());
                                data.get(y).add(new Boolean(cell.getBooleanCellValue()).toString());
                                break;
                            //公式类型
                            case FORMULA:
                                data.get(y).add(cell.getCellFormula());
                                break;
                            //空白类型
                            case BLANK:
                                data.get(y).add("");
                                break;
                            default:
                                //...;
                                data.get(y).add("");

                                log.debug("进入了 default，行号={}", y);
                                break;
                        }
                    }


                }
            }

//            for (Cell cell : row) { // 单元格
//                System.out.println("##############" + i + "#################");
//                System.out.println(cell.getAddress() + "    =  (" + cell.getColumnIndex() + "," + cell.getRowIndex() + ")    " + cell);
//                System.out.println(cell.getAddress() + "    =  (" + cell.getColumnIndex() + "," + cell.getRowIndex() + ")    " + cell.toString());
//                data.get(i).add(cell.toString());

//
//                    switch (cell.getCellType()) { // 不同的数据类型
//                        //// 字符串类型
//                        case STRING:
//                            data.get(i).add(cell.getRichStringCellValue().getString());
//                            System.out.println(cell.getRichStringCellValue().getString());
//                            break;
//                        //数值类型
//                        case NUMERIC:
//                            if (DateUtil.isCellDateFormatted(cell)) {
//                                data.get(i).add(cell.getDateCellValue().toString());
//                                System.out.println("getDateCellValue------->" + cell.getDateCellValue());
//                            } else {
//                                System.out.println("数值类型 getNumericCellValue------->" + cell.getNumericCellValue());
//                                System.out.println("数值类型 ------->" + new Double(cell.getNumericCellValue()).toString());
//                                data.get(i).add(new Double(cell.getNumericCellValue()).toString());
//                            }
//                            break;
//                        //布尔类型
//                        case BOOLEAN:
//                            System.out.println("布尔类型 getBooleanCellValue------->" + cell.getBooleanCellValue());
//                            System.out.println("布尔类型  ------->" + new Boolean(cell.getBooleanCellValue()).toString());
//                            data.get(i).add(new Boolean(cell.getBooleanCellValue()).toString());
//                            break;
//                        //公式类型
//                        case FORMULA:
//                            data.get(i).add(cell.getCellFormula());
//                            break;
//                        //空白类型
//                        case BLANK:
//                            data.get(i).add("");
//                            break;
//                        default:
//                            //...;
//                            data.get(i).add("");
//
//                            log.debug("进入了 default，行号={}", i);
//                            break;
//                    }
//            }
            //https://www.cnblogs.com/jyyjava/p/8074322.html
            //https://blog.csdn.net/holmofy/article/details/82532311
            //https://www.baidu.com/baidu?wd=poi+excel+解析&tn=54002054_dg&ie=utf-8
//                i++;
//            }
            return data;
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }
    }


    public static void main(String[] args) {
        fun2();


    }

    private static void fun2() {

        for (String s : getColumnLabels(26)) {
            System.out.print(s);
        }

        System.out.println();

        int index = 27;
        String[] columnLabels = getColumnLabels(index);
        for (int i = index = index - 1; i < columnLabels.length; i++) {
            System.out.println(columnLabels[i]);
        }

        index = 117;//DM
        columnLabels = getColumnLabels(index);
        for (int i = index = index - 1; i < columnLabels.length; i++) {
            System.out.println(columnLabels[i]);
        }
        index = 189;//GG
        columnLabels = getColumnLabels(index);
        for (int i = index = index - 1; i < columnLabels.length; i++) {
            System.out.println(columnLabels[i]);
        }
        index = 468;//QZ
        columnLabels = getColumnLabels(index);
        for (int i = index = index - 1; i < columnLabels.length; i++) {
            System.out.println(columnLabels[i]);
        }

        //java swagger3 列编号 数字转字母


//        for (String s :columnLabels ) {
//            System.out.print(s);
//        }

    }

    private static String[] sources = new String[]{
            "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P",
            "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * (256 for *.xls, 16384 for *.xlsx)
     *
     * @param columnNum 列的个数，至少要为1
     * @return 返回[1, columnNum]共columnNum个对应xls列字母的数组
     * @throws IllegalArgumentException 如果 columnNum 超出该范围 [1,16384]
     */
    public static String[] getColumnLabels(int columnNum) {
        if (columnNum < 1 || columnNum > 16384)
            throw new IllegalArgumentException();
        String[] columns = new String[columnNum];
        if (columnNum < 27) {    //小于27列 不用组合
            System.arraycopy(sources, 0, columns, 0, columnNum);
            return columns;
        }
        System.arraycopy(sources, 0, columns, 0, 26);    //前26列不需要进行组合

        //因为基于数组是从0开始，每到新一轮letterIdx 会递增，所以第一轮 在递增前是-1
        int letterIdx = -1;
        int currentLen = 26;//第一轮组合(2个字母的组合)是分别与A-Z进行拼接 所以是26
        int remainder;
        int lastLen = 0;    //用于定位上文提到的i%currentLen实际在数组中的位置
        int totalLen = 26;    //totalLen=currentLen+lastLen
        int currentLoopIdx = 0; //用来记录当前组合所有情形的个数

        for (int i = 26; i < columnNum; i++) { //第27列(对应数组的第26个位置)开始组合

            //currentLen是上个组合所有情形的个数，与它取余找到要与上个组合的哪种情形进行拼接
            remainder = currentLoopIdx % currentLen;

            if (remainder == 0) {
                letterIdx++; //完成一次上个组合的遍历，转到下个字母进行拼接
                int j = letterIdx % 26;

                //A-Z 26个子母都与上个组合所有情形都进行过拼接了，需要进行下个组合的拼接
                if (j == 0 && letterIdx != 0) {
                    lastLen = totalLen; //下个组合的lastLen是上个组合的totalLen

                    /**
                     * 下个组合的currentLen是上个组合的所有组合情形的个数
                     * （等于上个组合的currentLen*26)，26也就是拼接在前面的A-Z的个数
                     */
                    currentLen = 26 * currentLen;

                    totalLen = currentLen + lastLen; //为下一轮的开始做准备
                    currentLoopIdx = 0; //到下一轮了 因此需要重置
                }
            }
            /**
             * sources[letterIdx%26]是该轮要拼接在前面的字母
             * columns[remainder+lastLen]是上个组合被拼接的情形
             */
            columns[i] = sources[letterIdx % 26] + columns[remainder + lastLen];
            currentLoopIdx++;
        }
        return columns;
    }

    private static void fun1() {
        Map<String, List<Object>> domainMap = new HashMap<String, List<Object>>();
        List<Mall> malls1 = new ArrayList<>();
        List<MallArea> areas1 = new ArrayList<>();
//        List<MallQuestion> questions1 = new ArrayList<>();
//        List<MallTask> tasks1 = new ArrayList<>();

        malls1.add(new Mall());
        malls1.add(new Mall());
        malls1.add(new Mall());

        areas1.add(new MallArea());
        areas1.add(new MallArea());
        areas1.add(new MallArea());
        areas1.add(new MallArea());

        System.out.println(malls1.size());
        System.out.println(areas1.size());

        domainMap.put(Mall.class.toString(), Collections.singletonList(malls1));
        domainMap.put(MallArea.class.toString(), Collections.singletonList(areas1));


        List<Object> malls = domainMap.get(Mall.class.toString());
        List<Object> areas = domainMap.get(MallArea.class.toString());
//        List<Object> questions = domainMap.get(MallQuestion.class.toString());
//        List<Object> tasks = domainMap.get(MallTask.class.toString());
//        List<Object> tasks = domainMap.get(MallTask.class.toString());

        System.out.println(malls);
        System.out.println(areas);
    }


}
