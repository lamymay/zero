package com.arc.zero.test.date;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author yechao
 * @since 2020/6/23 9:54 上午
 */
public class SecondTest {

    /**
     * 灰度方案,白名单,by user id
     */
    private List<String> allowUserList;

    /**
     * 百分比灰度方案,百分之几 , 1=1%  20表示20%
     */
    private Integer allowPercentage;

    /**
     * 灰度方案,计数,发送几个
     */
    private Integer allowCount;


    public static void main(String[] args) throws ParseException {



      /*  day();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date zero = simpleDateFormat.parse("2020-09-18 00:00:00");
        Date createDate = simpleDateFormat.parse("2020-09-18 12:01:00");

        //Date date = DateUtils.addDays(createDate, 1);
        if (DateUtils.isSameDay(zero, createDate)) {
            System.out.println("预期是true="+(DateUtils.isSameDay(zero, createDate)));
        }

        System.out.println("------------------------------------");


        Date now = new Date();
        Date fiveDaysAgo = DateUtils.truncate(DateUtils.addDays(now, -5), Calendar.DATE);
        Date oneDayAfter = DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(now, 2), Calendar.DATE), -1);

        System.out.println("now=" + now);
        System.out.println("5天前=" + fiveDaysAgo);
        System.out.println("1天后=" + oneDayAfter);


        System.out.println("------------------------------------");
        System.out.println();
        System.out.println();
        System.out.println();

        //Date now = new Date();
        Date zeroToday = DateUtils.truncate(now, Calendar.DATE);
        Date fiftyNineToday = DateUtils.addSeconds(DateUtils.addDays(zeroToday, 1), -1);

        System.out.println("最后一秒=" + fiftyNineToday.toString());


//        long t1 = 3 * 24 * 60 * 60;
//        long t2 = 259200;
//
//        System.out.println("3*24=" + 3 * 24 + " h");
//        System.out.println(t1);
//        System.out.println(t2);

        // 相对时间处理

        fun1();
        // fun3();
        //fun2();

*/

        //fun6();
        fun7();
    }

    private static void fun7() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date zero = format.parse("2020-09-21 00:00:00");
        Date eight = format.parse("2020-09-21 08:00:59");
        Date nine = format.parse("2020-09-21 09:00:59");
        long nineUnix = nine.getTime() - zero.getTime();
        long eightUnix = eight.getTime() - zero.getTime();

        System.out.println("当日第一秒:" + zero + " 时间戳: " + zero.getTime());
        System.out.println("当日09点:" + nine + " 时间戳: " + nine.getTime());
        System.out.println("当日08点:" + eight + " 时间戳: " + eight.getTime());
        System.out.println(eightUnix);//28859000 八点又59s
        System.out.println(nineUnix);//32459000  九点又59秒
    }

    private static void fun6() {
        System.out.println("预期是周四 0917," + new Date(1600272000000L));
        System.out.println("预期是周六 0919," + new Date(1600444800000L));
        System.out.println("db create 预期是周五 0918," + new Date(1600437819000L));
        System.out.println("db update 预期是周六 0919," + new Date(1600437819000L));

        //918的活动  "gmtCreate":1600324336000,
        //        "gmtModified":1600416011000,
        System.out.println("db create 预期是91 ," + new Date(1600324336000L));
        System.out.println("db update 预期是918 ," + new Date(1600416011000L));

        //917的活动:{
        //        "activityId":29957,
        //        "aliuid":"8l1VIUIhxrn8ec3_jLP9vg",
        //        "bonus":0,
        //        "bonusStatus":10,
        //        "gameType":10009,
        //        "gmtCreate":1600238728000,
        //        "gmtModified":1600374715000,
        //        "gmtReachTarget":1600324299000,
        //        "groupId":1000029957000000001,
        //        "id":1620952002,
        //        "numStep":0,
        //        "orderId":1642087003,
        //        "platformType":1,
        //        "rank":4,
        //        "reachTarget":20,
        //        "userType":1
        //    },

        System.out.println("db create 预期是916 ," + new Date(1600238728000L));
        System.out.println("db update 预期是917 ," + new Date(1600374715000L));
//        System.out.println(String.valueOf(null));
//        System.out.println(String.valueOf(null));
//        System.out.println(String.valueOf(null));

        System.out.println(new Date(1600498859000L));
        System.out.println(new Date(1600498859000L));
        System.out.println(new Date(1600585259000L));
        System.out.println(new Date(1600585259000L));
    }

    private static void day() {
        Date now = new Date();

        Date beforeToday7Days = DateUtils.truncate(DateUtils.addDays(now, -7), Calendar.DATE);
        Date yesterdayLastSecond = DateUtils.addSeconds(DateUtils.truncate(now, Calendar.DATE), -1);
        System.out.println(beforeToday7Days);
        System.out.println(yesterdayLastSecond);

//        Date now = new Date();

        Date beforeToday6Days = DateUtils.truncate(DateUtils.addDays(now, -6), Calendar.DATE);
        Date todayLastSecond = DateUtils.addSeconds(DateUtils.truncate(DateUtils.addDays(now, 1), Calendar.DATE), -1);
        System.out.println(beforeToday6Days);
        System.out.println(todayLastSecond);

    }

    private static void fun3() {
        Date now = new Date();
        Date zeroToday = DateUtils.truncate(now, Calendar.DATE);
        System.out.println("当日第一秒:" + zeroToday.toLocaleString() + " 时间戳: " + zeroToday.getTime());

        System.out.println(1599580800000L == zeroToday.getTime());
    }

    private static void fun2() {
        //当日的第一秒时刻 当日的最后一秒时刻
        Date now = new Date();
        Date zeroToday = DateUtils.truncate(now, Calendar.DATE);
        Date fiftyNineToday = DateUtils.addSeconds(DateUtils.addDays(zeroToday, 1), -1);
        System.out.println("当日第一秒:" + zeroToday.toLocaleString() + " 时间戳: " + zeroToday.getTime());
        System.out.println("当日最后秒:" + fiftyNineToday.toLocaleString() + " 时间戳: " + fiftyNineToday.getTime());

    }

    private static void fun1() throws ParseException {

        //当日的第一秒时刻 当日的最后一秒时刻

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date zero = format.parse("2020-09-09 00:00:00");
        Date nine = format.parse("2020-09-09 09:00:59");
        Date fifteen = format.parse("2020-09-09 15:00:59");
        Date last = format.parse("2020-09-09 23:59:59");


        System.out.println("当日第一秒:" + zero + " 时间戳: " + zero.getTime());
        System.out.println("当日09点:" + nine + " 时间戳: " + nine.getTime());
        System.out.println("当日15点:" + fifteen + " 时间戳: " + fifteen.getTime());
        System.out.println("当日最后秒:" + last + " 时间戳: " + last.getTime());

        long zeroUnix = zero.getTime();
        long nineUnix = nine.getTime();
        long fifteenUnix = fifteen.getTime();
        System.out.println("09点距离当日第一秒的毫秒差=" + (nineUnix - zeroUnix));
        System.out.println("15点距离当日第一秒的毫秒差=" + (fifteenUnix - zeroUnix));


        long time = format.parse("2020-09-09 17:07:00").getTime();

        long currentTimeMillis = System.currentTimeMillis();
        System.out.println("now距离" + new Date(currentTimeMillis) + "的毫秒差=" + (currentTimeMillis - time) + " 秒级别" + (currentTimeMillis - time) / 1000);

        System.out.println();
//        SimpleDateFormat hourAndMinuteFormat = new SimpleDateFormat("HH:mm");
//        String nineFormatToHourAndMinuteString = hourAndMinuteFormat.format(zeroToday);
//        Date nineFormatToHourAndMinuteDate = hourAndMinuteFormat.parse(nineFormatToHourAndMinuteString);
//        System.out.println("9点字符串:" + nineFormatToHourAndMinuteString + " 时间戳:" + nineFormatToHourAndMinuteDate.getTime());


    }


}
