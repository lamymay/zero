package com.arc.zero.controller.data.test.date;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Model1 {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin;
    private Date end;

    public static void main(String[] args) {
        long time = 1586224307000L;
        Date date0 = new Date(time);
        Date date1 = new Date(time);

        Date date2 = new Date();
        long now = System.currentTimeMillis();
        Date date3 = new Date(now);
        System.out.println(now + " - " + time + " = " + (now - time));
        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
        System.out.println("---------------");
        System.out.println(date0);
        System.out.println(date1);

        //begin<end    等于的情况 也是 false
        boolean before = date0.before(date1);
        boolean after = date0.after(date1);
        System.out.println(before);
        System.out.println(after);

    }
}
