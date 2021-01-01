//package com.arc.zero.service.system.impl;
//
//import com.arc.core.model.domain.system.SysMenu;
//import com.arc.zero.service.system.SysMenuService;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
///**
// * @author yechao
// * @since 2020/8/20 11:08 上午
// */
//@Slf4j
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class SysMenuServiceImplTest {
//
//    /**
//     *
//     */
//    @Autowired
//    private SysMenuService sysMenuService;
//
//    @Test
//    void treeSysMenu2() {
//        List<SysMenu> sysMenus = sysMenuService.listTreeMenuWithSystemIdAndLevel(4);
//        Assert.assertNotNull(sysMenus);
//
//    }
//}