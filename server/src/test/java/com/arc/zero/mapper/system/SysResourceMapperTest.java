//package com.arc.zero.mapper.system;
//
//import com.arc.core.model.domain.system.SysResource;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.*;
//
///**
// * @author may
// * @since 2019/7/21 14:04
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Slf4j
//public class SysResourceMapperTest {
//
//    @Resource
//    private SysResourceMapper resourceMapper;
//
//    @Test
//    public void save() {
//        SysResource resource = new SysResource();
//        resource.setPath("/test");
//        resource.setResourceName("arc");
//        resource.setNote("ceshi");
//
//        int save = resourceMapper.save(resource);
//        log.debug("数据={}", save);
//        Assert.assertNotNull(save);
//
//    }
//
//    @Test
//    public void deleteIdIn() {
//        String[] split = "60,61,62,63,64,2,3".split(",");
//        ArrayList<Long> ids = new ArrayList<>();
//        for (String s : split) {
//            ids.add(Long.valueOf(s));
//        }
//
//        int result = resourceMapper.deleteIdIn(new HashSet<>(Arrays.asList()));
//        log.debug("数据={}", result);
//    }
//
//
//    @Test
//    public void list() {
//        List<SysResource> list = resourceMapper.list();
//        log.debug("数据={}", list.size());
//
//    }
//
//    @Test
//    public void update() {
//        SysResource resource = new SysResource();
//        resource.setId(54L);
//        resource.setResourceName("name11111");
//
//        resource.setPath("/123");
//        resource.setPriority(1);
//
//        int temp = resourceMapper.update(resource);
//        log.debug("数据={}", temp);
//        log.debug("数据={}", temp);
//        log.debug("数据={}", temp);
//        log.debug("数据={}", temp);
//
//    }
//
//    @Test
//    public void updateBatch() {
//        Set<SysResource> set = new HashSet<>(60);
//
//        int i = 0;
//        for (SysResource resource : resourceMapper.list()) {
//            resource.setResourceName("" + (++i));
//            set.add(resource);
//        }
//        try {
//            int update = 0;
//            for (SysResource resource : set) {
//                int temp = resourceMapper.update(resource);
//                update = update + temp;
//            }
//
//            log.debug("数据={}", update);
//        } catch (Exception e) {
//            System.out.println("-------------------------------------------------------------");
//            e.printStackTrace();
//            System.out.println("-------------------------------------------------------------");
//        }
//
//
//    }
//}
