package com.arc.zero.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;

/**
 * @author 叶超
 * @since 2019/11/14 下午 12:37
 */
public class TimeUtil {
    public static void main(String[] args) {
        fun1();
        fun2();
        fun3();
    }

    private static void fun3() {
        System.out.println("**** test3");

        LocalDateTime of1 = LocalDateTime.of(2018, 9, 25, 1, 1);//2018-9-25 01:01
        LocalDateTime of2 = LocalDateTime.of(2019, 9, 25, 23, 16); //2019-9-25 23:16
        System.out.println(dateDiff(of1, of2));//365

        of1 = LocalDateTime.of(2018, 9, 25, 1, 1);//2018-9-25 01:01
        of2 = LocalDateTime.of(2019, 9, 25, 23, 16); //2019-9-25 23:16
        System.out.println(monthDiff(of1, of2));//12
        System.out.println("**** test3");
        System.out.println("天数"+ dateDiff(LocalDateTime.now(),LocalDateTime.of(2019,11,14,11,10)));
    }

    /**
     * 计算两个时间点的天数差
     *
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即要计算的天数差
     */
    public static int dateDiff(LocalDateTime dt1, LocalDateTime dt2) {
        //获取第一个时间点的时间戳对应的秒数
        long t1 = dt1.toEpochSecond(ZoneOffset.ofHours(0));
        //获取第一个时间点在是1970年1月1日后的第几天
        long day1 = t1 / (60 * 60 * 24);
        //获取第二个时间点的时间戳对应的秒数
        long t2 = dt2.toEpochSecond(ZoneOffset.ofHours(0));
        //获取第二个时间点在是1970年1月1日后的第几天
        long day2 = t2 / (60 * 60 * 24);
        //返回两个时间点的天数差
        return (int) (day2 - day1);
    }

    /**
     * 获取两个时间点的月份差
     *
     * @param dt1 第一个时间点
     * @param dt2 第二个时间点
     * @return int，即需求的月数差
     */
    public static int monthDiff(LocalDateTime dt1, LocalDateTime dt2) {
        //获取第一个时间点的月份
        int month1 = dt1.getMonthValue();
        //获取第一个时间点的年份
        int year1 = dt1.getYear();
        //获取第一个时间点的月份
        int month2 = dt2.getMonthValue();
        //获取第一个时间点的年份
        int year2 = dt2.getYear();
        //返回两个时间点的月数差
        return (year2 - year1) * 12 + (month2 - month1);
    }


    private static void fun2() {
        /** The date at the end of the last century */
        LocalDate now = LocalDate.now();
        LocalDate oldDate = LocalDate.of(2019, 11, 10);


        Period diff = Period.between(oldDate, now);

        System.out.printf("The 21st century (up to %s) is %s old%n", now, diff);
        System.out.printf("The 21st century is %d years, %d months and %d days old",
                diff.getYears(), diff.getMonths(), diff.getDays());


        System.out.println("###########");

        System.out.println(oldDate);
        System.out.println(now);
        LocalDate init = LocalDate.of(2019, 11, 14);
        System.out.println("init=" + init.toEpochDay());
        System.out.println(now.toEpochDay());
        System.out.println(oldDate.toEpochDay());
        System.out.println("old=" + oldDate);
        System.out.println("now=" + now);
        System.out.println("days=" + (now.toEpochDay() - oldDate.toEpochDay()));
        System.out.println(oldDate.toEpochDay());
        System.out.println(oldDate.getMonthValue());

        System.out.println(now.getYear());
        System.out.println(oldDate.lengthOfMonth());
//        System.out.println(oldDate.withDayOfYear()));
        System.out.println("###########");


        System.out.println("-------------");
        LocalDate startDate = LocalDate.of(2019, 1, 1);
        LocalDate newDate = startDate.plusDays(3);
        System.out.println(startDate);
        System.out.println(newDate);
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime newTime = startTime.plusHours(5);
        System.out.println(startTime);
        System.out.println(newTime);
        startTime = startTime.plusHours(5);
        System.out.println(startTime);
    }


    public static void fun1() {
        LocalDate old = LocalDate.of(2019, 11, 14);
        LocalDate now = LocalDate.now();
        System.out.println(now.toEpochDay() - old.toEpochDay());
    }

}
