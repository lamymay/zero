package com.arc.zero.test.date;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yechao
 * @date 2020/9/21 9:37 上午
 */
@Slf4j
public class TestCreateAt {

    public volatile Integer flag;

    public static void main(String[] args) throws ParseException {
//        fun1();
//        fun2();
//        fun3();
//        fun4();
//        fun5();

        fun6();
        fun7();

    }

    private static void fun7() {
        //   "id":1623188001,
        //            "gmt_create":1600435907000,
        //            "gmt_modified":1600502441000,
        System.out.println(new Date(1600435907000L));
        System.out.println(new Date(1600502441000L));
    }

    private static void fun6() throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date1 = format.parse("2020-09-21 10:20");
        Date date2 = format.parse("2020-09-21 12:20");
        boolean sameDate = isSameDate(date1, date2);
        System.out.println(sameDate);//true
        System.out.println(isSameDate(date1, new Date()));//true
    }


    public static boolean isSameDate(Date date1, Date date2) {
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
            boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
            boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
            return isSameDate;
        } catch (Exception e) {
            log.error("[RatingEngine] error occurred: ERROR ", e);
        }
        return false;
    }

    private static void fun5() {

        List<Integer> userIds = new ArrayList<>();
        userIds.add(121);
        userIds.add(122);
        userIds.add(123);
        userIds.add(134);
        userIds.add(456);
        userIds.add(457);
        userIds.add(128);
        userIds.add(129);
        userIds.add(100);

        for (Integer hashCode : userIds) {
            System.out.print(hashCode + "   ");
            int mod = hashCode % 10;
            System.out.println(mod);
            if (mod <= 2) {
                System.out.println(hashCode);
            }
        }
    }

    private static void fun4() {
        List<String> userIds = Arrays.asList("AAAA", "BBBB", "CCCC", "DDDD", "RCAA", "BEAR", "COPY", "DAYS");
        List<String> resultList = new ArrayList<>();
        for (String userId : userIds) {
            String tempUserId = grayscale(userId);
            if (tempUserId != null) {
                resultList.add(tempUserId);
            }
        }

        System.out.println(resultList.toString());

    }

    private static String grayscale(String userId) {
        int hashCode = userId.hashCode();
        System.out.print(hashCode + "--" + userId + "--");
        int mod = hashCode % 10;
        System.out.println(mod);
        if (mod <= 5) {
            return userId;
        }
        return null;
    }

    private static void fun3() {
        Date zeroToday = DateUtils.truncate(new Date(), Calendar.DATE);
        Date lastMilliSecondToday = DateUtils.addSeconds(DateUtils.addDays(zeroToday, 1), -1);
        int nowToTodayLastMilliSecond = (int) (lastMilliSecondToday.getTime() - System.currentTimeMillis());

        System.out.println(nowToTodayLastMilliSecond);
    }

    private static void fun2() {
        //{
        //                "activityId":29868,
        //                "sure":null,
        //                "groupId":"1008629868000000001",
        //                "activityStatus":null,
        //                "dateString":"1600099200000",
        //                "enroll":true,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":true
        //            },
        //            {
        //                "activityId":29914,
        //                "sure":null,
        //                "groupId":"1000029914000000001",
        //                "activityStatus":null,
        //                "dateString":"1600185600000",
        //                "enroll":true,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":true
        //            },
        //            {
        //                "activityId":29957,
        //                "sure":null,
        //                "groupId":"1000029957000000001",
        //                "activityStatus":null,
        //                "dateString":"1600272000000",
        //                "enroll":true,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":true
        //            },
        //            {
        //                "activityId":30000,
        //                "sure":null,
        //                "groupId":"1000030000000000001",
        //                "activityStatus":null,
        //                "dateString":"1600358400000",
        //                "enroll":true,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":true
        //            },
        //            {
        //                "activityId":30043,
        //                "sure":null,
        //                "groupId":"1000030043000000001",
        //                "activityStatus":null,
        //                "dateString":"1600444800000",
        //                "enroll":true,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":true
        //            },
        //            {
        //                "activityId":null,
        //                "sure":null,
        //                "groupId":null,
        //                "activityStatus":null,
        //                "dateString":"1600531200000",
        //                "enroll":false,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":false
        //            },
        //            {
        //                "activityId":30129,
        //                "sure":null,
        //                "groupId":"1000030129000000001",
        //                "activityStatus":null,
        //                "dateString":"1600617600000",
        //                "enroll":true,
        //                "class":"com.alibaba.alisports.activity.client.biz.dto.activity.UserDateInfoVO",
        //                "target":true
        //            }

        System.out.println("一周数据");
        System.out.println("15=" + new Date(1600099200000L));//15
        System.out.println("16=" + new Date(1600185600000L));//16
        System.out.println("17=" + new Date(1600272000000L));//17
        System.out.println("18=" + new Date(1600358400000L));//18
        System.out.println("19=" + new Date(1600444800000L));//19
        System.out.println("20=" + new Date(1600531200000L));//20
        System.out.println("21=" + new Date(1600617600000L));//21

        System.out.println("-------------------------");
    }

    private static void fun1() {
        // k49BfJI1NHfpQl4n_nti1Q
        //    "id": 1624952002,
        //      "gmt_create": 1600577181000,
        //      "gmt_modified": 1600577181000,
        System.out.println(new Date(1600577181000L));
        System.out.println(new Date(1600577181000L));

        //      "id": 1623188001,
        //      "gmt_create": 1600435907000,
        //      "gmt_modified": 1600502441000,
        System.out.println("crateAt" + new Date(1600435907000L));
        System.out.println("updateAt" + new Date(1600502441000L));

    }
}
