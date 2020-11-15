package com.arc.zero.test.date;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yechao
 * @date 2020/9/16 5:18 下午
 */
public class TestDate {

    public static void main(String[] args) {
        long nowUnix = System.currentTimeMillis();
        long zeroTodayUnix = DateUtils.truncate(new Date(), Calendar.DATE).getTime();
        long defineGetupActivityEndUnixTime = 32459000L;

        long noneUnix = zeroTodayUnix + defineGetupActivityEndUnixTime;

        long lateUnix1 = noneUnix + (38975422L / 1000);
//        long lateUnix2 = noneUnix + 38975422L;
        long lateUnix2 = noneUnix + 38975422L/1000;

//        Date lateDate = new Date(lateUnix1);
        System.out.println(System.currentTimeMillis());
        int min = 1000 * 60;
        int hour = min * 60;
        int day = hour * 24;
        System.out.println("####################");
        System.out.println(lateUnix2 / day);
        System.out.println(lateUnix2 / hour);
        System.out.println(lateUnix2 / min);
        System.out.println("####################");
//        DateUtils.get(lateDate,)

        System.out.println("自然时间-早起打卡打卡截止时间:" + new Date(noneUnix).toString());
        System.out.println("自然时间-早起打卡打卡迟到时间:" + new Date(lateUnix1).toString());
        System.out.println("自然时间-早起打卡打卡迟到时间:" + new Date(lateUnix2).toString());
        System.out.println("自然时间-          此时时间:" + new Date(nowUnix).toString());


        //当日打卡迟到时间计算 = 此时的毫秒数-打卡结束自然时间的毫秒数, 又因为 当日打卡最迟自然时间= 当日第一秒+当日打卡时间(9:00AM)
        //        Date nineDate = new Date(noneUnix);
        //        Date errorDate = new Date(lateUnix2);
        //long lateTimeMillis = nowUnix - (zeroTodayUnix + defineGetupActivityEndUnixTime);

        fun2();
    }

    private static void fun2() {


        long oneMinutes = 1000 * 60;//60 000ms
        long oneHours = oneMinutes * 60;//3600 000
        long oneDay = oneHours * 24;

        System.out.println(oneMinutes);
        System.out.println(oneHours);
        System.out.println(oneDay);

        float lateUnix = 38975F;

        System.out.println((lateUnix / oneDay) + " 天");
        System.out.println((lateUnix / oneHours) + " 小时");
        System.out.println((lateUnix / oneMinutes) + " 分钟");

        //时间减小1000 单位转为秒

        long zeroTodayUnix = DateUtils.truncate(new Date(), Calendar.DATE).getTime();
        long defineGetupActivityEndUnixTime = 32459000L;

        long noneUnix = zeroTodayUnix + defineGetupActivityEndUnixTime;
        long lateUnix2 = noneUnix + 38975422L / 1000;


        System.out.println((lateUnix2 / oneDay));
        System.out.println((lateUnix2 / oneHours));
        System.out.println((lateUnix2 / oneMinutes));

        System.out.println("----- 迟到时间 ---");
        long unix = 38975422L;
//        double day = Math.floor(unix / oneDay);
//        double hour = Math.floor((lateUnix % oneDay) / oneHours);
//        double min = Math.floor((unix % oneHours) / oneMinutes);
        double day = unix / oneDay;
        double hour = (lateUnix % oneDay) / oneHours;
        double min = (unix % oneHours) / oneMinutes;

        System.out.println(day + " 天");
        System.out.println(hour + " 小时");
        System.out.println(min + " 分钟");
//        System.out.println(" 秒");
//        System.out.println();
    }

}
