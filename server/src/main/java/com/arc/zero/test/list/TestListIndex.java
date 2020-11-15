package com.arc.zero.test.list;

import java.util.ArrayList;

/**
 * @author yechao
 * @date 2020/9/9 4:31 下午
 */
public class TestListIndex {

    public static void main(String[] args) {
        ArrayList<Object> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add("3");

        if (list != null && list.size() > 0) {
            System.out.println(list.get(0));
            System.out.println(list.get(1));
        }

    }
}
