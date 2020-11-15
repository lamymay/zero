package com.arc.zero.test.breakpoint;

/**
 * 多线程调试
 * @author yechao
 * @date 2020/08
 */
public class BreakPointTest2 {


    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            execute(i);
        }
    }

    private static void execute(int num) {
        int rs = ((num + 3) * 5 + 9) / num;
        System.out.println(rs);
    }

}
