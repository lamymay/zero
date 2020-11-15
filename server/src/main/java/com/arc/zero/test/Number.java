package com.arc.zero.test;

/**
 * @author yechao
 * @date 2020/9/11 11:05 上午
 */
public class Number {

    public static void main(String[] args) {
        int a, b, c = 0;
        int d = 10;
        a = d--;
        b = (d--);
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        System.out.println("d=" + d);
    }
}
