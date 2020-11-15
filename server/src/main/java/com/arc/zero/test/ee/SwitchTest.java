package com.arc.zero.test.ee;

/**
 * @author 叶超
 * @since 2020/3/15 17:58
 */
public class SwitchTest {
    public static void main(String[] args) {
        method(null);
    }

    public static void method(String param) {
        switch (param) {
            // 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
        }
    }

}
