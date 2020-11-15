//package com.arc.zero.mapper.excel;
//
//import com.arc.zero.model.excel.ExcelEntity;
//import com.arc.zero.model.excel.ExcelRequest;
//import junit.framework.TestCase;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author 叶超
// * @since 2019/11/1 16:35
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("self")
//public class ExcelMapperTest extends TestCase {
//
//    @Resource
//    ExcelMapper excelMapper;
//
//    @Test
//    public void testListPage() {
//
//        try {
//            ExcelRequest excelRequest = new ExcelRequest();
//            excelRequest.setCurrentPage(1);
//            excelRequest.setPageSize(50000);
//
//            List<ExcelEntity> records = excelMapper.list();
//            System.out.println(records.size());
//            records = excelMapper.listPage(excelRequest);
//            System.out.println(records.size());
//            System.out.println(records.size());
//            Integer integer = excelMapper.countByJoinId(1L);
//            System.out.println(integer);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testCountByJoinId() {
//    }
//}
