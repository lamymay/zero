package com.arc.zero.controller.data.test.file;

import java.io.UnsupportedEncodingException;

/**
 * @author yechao
 * @date 2020/11/10 2:47 下午
 */
public class TestUrlEncode {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String input = "http://www.altavista.com/cgi-bin/" + "qerry?pg=q&kl=XX&stype=stext&q=%2B%22Java+I%2FO%22&search.x=38&search.y=3";
        String output = java.net.URLDecoder.decode(input, "UTF-8");
        System.out.println(output);
    }
}
