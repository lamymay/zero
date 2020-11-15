package com.arc.zero.test.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author yechao
 * @date 2020/10/12 7:38 下午
 */
public class LocalDateTimeTest {


    public static void main(String[] args) {


        test1();
        test2();
        test3();
        test4();

    }

    private static void test4() {
        //字符串解析
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");

        LocalDateTime date = LocalDateTime.from(df.parse("2017-10-11 22:22:22"));
    }

    //时间格式化
    private static void test3() {
   //关于时间加减LocalDateTime提供了plus和minus的各种方法，需要的时候根据提示找对应的API即可。
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:ss:mm");

        String format = df.format(now);

    }
    private static void test2() {
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();

        //获取当前系统所在的时区
        ZoneId zoneId = ZoneId.systemDefault();

        //获取该时间点在该时区上的时间日期信息
        ZonedDateTime zonedDateTime = now.atZone(zoneId);

        Date nowDate = Date.from(zonedDateTime.toInstant()); }

    private static void test1() {
        Date date = new Date();

        Instant instant = date.toInstant();

        ZoneId zoneId = ZoneId.systemDefault();
    }

}

//SpringMVC
//Spring4版本之后默认对JSR310提供支持，只需要在日期字段上加注解
//
//@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//Mybatis
//mybatis中数据库字段对应的实体类时间也是默认使用Date类型不做改变，而是提供了针对jsr310的一个插件
//
//
//<dependency>
//<groupId>org.mybatis</groupId>
//<artifactId>mybatis-typehandlers-jsr310</artifactId>
//<version>xxx</version>
//</dependency>

//引入包之后不需要做任何改变，实体类为LocalDateTime等的字段就可以识别
//
//SpringDataJPA
//Spring的做法跟Mybatis的类似，使用一个Jsr310JpaConverters类来做转换，SpringBoot中只要在启动类上加注解即可：
//
//
//@EntityScan(basePackageClasses = {Application.class,Jsr310JpaConverters.class})
//
//链接：https://www.jianshu.com/p/56a119395fcd
