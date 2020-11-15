package com.arc.zero.test.breakpoint;

import com.arc.zero.controller.data.rbac.SysUserController;

import java.lang.reflect.Method;

/**
 * @author may
 * @since 2020/1/25 18:22
 */
public class AnnotationUtilsTest {
    public static void main(String[] args) {
        Class<SysUserController> clz = SysUserController.class;
        Method[] methods = clz.getDeclaredMethods();
        for (Method method : methods) {
           // AnnotationUtils.findAnnotation(method, SysUserController.class);
            System.out.println(method);
        }




    }
}
