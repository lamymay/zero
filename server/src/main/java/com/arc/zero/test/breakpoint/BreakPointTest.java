package com.arc.zero.test.breakpoint;

/**
 * @author yechao
 * @since 2020/8/14 12:41 下午
 */
public class BreakPointTest {

    //断点调试

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
