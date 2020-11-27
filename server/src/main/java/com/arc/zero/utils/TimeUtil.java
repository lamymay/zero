package com.arc.zero.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 叶超
 * @since 2019/11/14 下午 12:37
 */
@Slf4j
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
        System.out.println("天数" + dateDiff(LocalDateTime.now(), LocalDateTime.of(2019, 11, 14, 11, 10)));
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

    public static void fun0() {
        System.out.println(LocalDate.now());        //只包含日期信息
        System.out.println(LocalTime.now());        //只包含时间信息
        System.out.println(LocalDateTime.now());        //包含日期、时间信息
    }

    final static int toDay = 1000 * 60 * 60 * 24;

    /**
     * 两个时间相差多少天
     *
     * @param day1 day1
     * @param day2 day2
     * @return 两个时间相差多少天
     */
    private static Integer betweenDays(Date day1, Date day2) {
        String template = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(template);
        //在使用SimpleDateFormat对String类型的日期进行parse的时候，如果传入的日期为：2017-08-60，这种错误的日期，Java默认会按照日期的信息对其进行转换，formatter.parse("2017-08-60");，得到的日期为2017-09-29，而如果不想进行这种转换，而直接将其判定为输入错误，则可以设置formatter.setLenient(false);，这时就会抛出java.text.ParseException异常了
        format.setLenient(false);
        try {
            String format1 = format.format(day1);
            String format2 = format.format(day2);
            Date date1 = format.parse(format1);
            Date date2 = format.parse(format2);
            long time1 = date1.getTime();
            long time2 = date2.getTime();
            long abs = Math.abs(time2 - time1);
            int days = (int) (abs / toDay);
            return days;
        } catch (Exception exception) {
            log.error("计算两个时间相差多少天差异", exception);
        }
        return null;
    }

    /**java 8中的ChronoUnit类
     * 用SimpleDateFormat计算时间差
     *
     * @throws Exception
     */
    public static void calculateTimeDifferenceBySimpleDateFormat() throws Exception {
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        /*天数差*/
        Date fromDate1 = simpleFormat.parse("2018-03-01 12:00");
        Date toDate1 = simpleFormat.parse("2018-03-12 12:00");
        long from1 = fromDate1.getTime();
        long to1 = toDate1.getTime();
        int days = (int) ((to1 - from1) / (1000 * 60 * 60 * 24));
        System.out.println("两个时间之间的天数差为：" + days);

        /*小时差*/
        Date fromDate2 = simpleFormat.parse("2018-03-01 12:00");
        Date toDate2 = simpleFormat.parse("2018-03-12 12:00");
        long from2 = fromDate2.getTime();
        long to2 = toDate2.getTime();
        int hours = (int) ((to2 - from2) / (1000 * 60 * 60));
        System.out.println("两个时间之间的小时差为：" + hours);

        /*分钟差*/
        Date fromDate3 = simpleFormat.parse("2018-03-01 12:00");
        Date toDate3 = simpleFormat.parse("2018-03-12 12:00");
        long from3 = fromDate3.getTime();
        long to3 = toDate3.getTime();
        int minutes = (int) ((to3 - from3) / (1000 * 60));
        System.out.println("两个时间之间的分钟差为：" + minutes);
    }

    /**
     * 使用java 8的ChronoUnit，ChronoUnit可以以多种单位（基本涵盖了所有的，看源码发现竟然还有“FOREVER”这种单位。。）表示两个时间的时间差
     */
    public static void calculateTimeDifferenceByChronoUnit() {
        LocalDate startDate = LocalDate.of(2003, Month.MAY, 9);
        System.out.println("开始时间：" + startDate);

        LocalDate endDate = LocalDate.of(2015, Month.JANUARY, 26);
        System.out.println("结束时间：" + endDate);

        long daysDiff = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println("两个时间之间的天数差为：" + daysDiff);
//  long hoursDiff = ChronoUnit.HOURS.between(startDate, endDate);  //这句会抛出异常，因为LocalDate表示的时间中不包含时分秒等信息
    }

    /**
     * java 8中的Duration类
     * Duration与Period相对应，Period用于处理日期，而Duration计算时间差还可以处理具体的时间，也是通过调用其静态的between方法，该方法的签名是between(Temporal startInclusive, Temporal endExclusive)，因此可以传入两个Instant的实例（Instant实现了Temporal接口），并可以以毫秒（toMillis）、秒（getSeconds）等多种形式表示得到的时间差
     */
    public static void calculateTimeDifferenceByDuration() {
        Instant inst1 = Instant.now();  //当前的时间
        System.out.println("Inst1：" + inst1);
        Instant inst2 = inst1.plus(Duration.ofSeconds(10));     //当前时间+10秒后的时间
        System.out.println("Inst2：" + inst2);
        Instant inst3 = inst1.plus(Duration.ofDays(125));       //当前时间+125天后的时间
        System.out.println("inst3：" + inst3);

        System.out.println("以毫秒计的时间差：" + Duration.between(inst1, inst2).toMillis());

        System.out.println("以秒计的时间差：" + Duration.between(inst1, inst3).getSeconds());
    }/**
     *使用Calendar对象计算时间差，可以按照需求定制自己的计算逻辑
     * @param strDate
     * @throws ParseException
     */
    public static void calculateTimeDifferenceByCalendar(String strDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date date = formatter.parse(strDate);

        Calendar c1 = Calendar.getInstance();   //当前日期
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date);   //设置为另一个时间

        int year = c1.get(Calendar.YEAR);
        int oldYear = c2.get(Calendar.YEAR);

        //这里只是简单的对两个年份数字进行相减，而没有考虑月份的情况
        System.out.println("传入的日期与今年的年份差为：" + (year - oldYear));
    }
}
