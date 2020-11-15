//package com.arc.zero.mapper.system;
//
//
//import com.arc.core.model.domain.system.SysRole;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author 叶超
// * @since 2019/6/12 23:29
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ActiveProfiles("dev")
//@Slf4j
//public class SysRoleMapperTest {
//
//    @Resource
//    private SysRoleMapper roleMapper;
//
//    @Test
//    public void testBatchSave() {
//
//        List<SysRole> roles = getTestList();
//
//
//        int result = roleMapper.batchSave(roles);
//        Assert.assertTrue(result > 0);
//
//    }
//
//    private List<SysRole> getTestList() {
//        List<SysRole> roles = new ArrayList<>();
//
//
//        return roles;
//
//    }
//}
