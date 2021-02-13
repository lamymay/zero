package com.arc.zero.controller.data.test.file;

import org.apache.commons.lang3.SystemUtils;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @author yechao
 * @date 2020/12/8 2:36 下午
 */
public class TestCommand {

    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        System.out.println(locale);
        System.out.println(locale.getDisplayCountry());
        System.out.println(locale.getDisplayLanguage());
        System.out.println(locale.toLanguageTag());
        printAllFields(locale);
        System.out.println("getHostName--" + SystemUtils.getHostName());
        System.out.println("getUserDir--" + SystemUtils.getUserDir());
        System.out.println("getUserHome--" + SystemUtils.getUserHome());
        System.out.println("getJavaHome--" + SystemUtils.getJavaHome());
        System.out.println("-----");

        printAllFields(new SystemUtils());
    }

    /**
     * 反射打印全部属性
     *
     * @param obj Object
     */
    public static void printAllFields(Object obj) {
        Class cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        System.out.println("共有" + fields.length + "个属性");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                System.out.println(field.getName() + ":" + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
