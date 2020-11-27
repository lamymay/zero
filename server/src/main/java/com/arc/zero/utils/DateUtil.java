package com.arc.zero.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * @author yechao
 * @date 2020/11/27 2:34 下午
 */
public class DateUtil {


    public static void main(String[] args) {
        Date date1 = null;
        Date date2 = null;
        try {
            //1.	设置两个时间
            date1 = DateUtils.parseDate("2018-07-09 13:08:15", "yyyy-MM-dd HH:mm:ss");
            date2 = DateUtils.parseDate("2018-07-02 18:35:53", "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Date.getTime()可以得到1970年01月1日0点零分以来的毫秒数,
        //2.用来获取两个时间相差的毫秒数
        long l = date1.getTime() - date2.getTime();
        //3.分别计算相差的天、小时、分、秒
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        System.out.println(day + "天");
        System.out.println(day + "天" + hour + "小时");
        System.out.println(+day + "天" + hour + "小时" + min + "分");
        System.out.println(+day + "天" + hour + "小时" + min + "分" + s + "秒");

    }
}
