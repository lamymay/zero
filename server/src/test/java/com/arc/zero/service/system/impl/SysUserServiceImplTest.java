//package com.arc.zero.service.system.impl;
//
//import com.arc.core.model.domain.system.SysUser;
//import com.arc.zero.service.system.SysUserService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * @author 叶超
// * @since 2019/3/3 23:18
// */
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("local")
//public class SysUserServiceImplTest {
//
//
//    @Autowired
//    private SysUserService userService;
//
//    //    @Test
////    public void save() {
////    }
////
////    @Test
////    public void delete() {
////    }
////
////    @Test
////    public void update() {
////    }
////
////    @Test
////    public void get() {
////    }
////
//
//    //    @Before
////    public void beforeList() {
////        SysUser user=new SysUser(1000L,"测试");
////        Long save = sysUserService.save(user);
////        Assert.assertNotNull(save);
////    }
//    @Test
//    public void list() {
//        List<SysUser> test = userService.list();
//        Assert.assertNotNull(test);
//    }
//
//
////    @Test
////    public void getByAndIdentifierIdentityType() {
////        SysUser user = userService.getUserByIdentityTypeAndIdentifier(1, "admin");
////        log.debug("结果={}", user);
////        Assert.assertNotNull(user);
////    }
//
////    @After
////    public void afterList() {
////        int delete = sysUserService.delete(1000L);
////        Assert.assertNotNull(delete);
////    }
////
////    @Test
////    public void getByIdentifierAndIdentityType() {
////    }
////
////    @Test
////    public void login() {
////    }
//}
