package com.arc.zero.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author yechao
 * @since 2020/8/17 3:07 下午
 */
public class TestList {
    /**
     * 此定时任务是单进程的,所以是安全的
     */
    private static SimpleDateFormat hourMinutesFormat = new SimpleDateFormat("HH:mm");

    public static void main(String[] args) throws ParseException {
//        boolean canActive = canActive("10:46");
//        System.out.println(canActive);

        LocalDate d = LocalDate.now(); // 当前日期
        LocalTime t = LocalTime.now(); // 当前时间
        LocalDateTime dt = LocalDateTime.now(); // 当前日期和时间
        System.out.println(d); // 严格按照ISO 8601格式打印
        System.out.println(t); // 严格按照ISO 8601格式打印
        System.out.println(dt); // 严格按照ISO 8601格式打印

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
        String date1 = format.format(new Date());
        System.out.println(date1);
        System.out.println(LocalDateTime.now().toLocalDate());
    }


    /**
     * 检查配置的时间是否允许
     *
     * @param activeTime 开始时间 格式: 09:00
     * @return
     */
    private static boolean canActive(String activeTime) throws ParseException {
        if (activeTime == null) {
            return true;
        }
        boolean isSameMinutes = false;
        Date now = new Date();
        String tempNowString = hourMinutesFormat.format(now);

        Date nowTime = hourMinutesFormat.parse(tempNowString);
        Date targetTime = hourMinutesFormat.parse(activeTime);

        System.out.println(targetTime);
        System.out.println(targetTime.toLocaleString() + " targetTime 时分 时间戳=" + targetTime.getTime());
        System.out.println(nowTime.toLocaleString() + " nowTime 抹掉年月日 留下时分 时间戳=" + now.getTime());
        System.out.println(now.toLocaleString() + " now时间戳=" + now.getTime());


        long time1 = nowTime.getTime();
        long time2 = targetTime.getTime();
        isSameMinutes = (time1 == time2);
        return isSameMinutes;
    }


    public static void testList() {
        StringBuilder typeAppender = new StringBuilder();

        ArrayList<Integer> gameTypes = new ArrayList<>();
        gameTypes.add(1);
        gameTypes.add(2);
        gameTypes.add(3);
        gameTypes.add(4);

        int size = gameTypes.size();
        System.out.println(size);
        for (int i = 0; i <= size - 1; i++) {
            typeAppender.append(gameTypes.get(i));
            if (i != size - 1) {
                typeAppender.append(",");
            }
        }


        System.out.println(gameTypes);
        System.out.println(typeAppender.toString());


    }


}
