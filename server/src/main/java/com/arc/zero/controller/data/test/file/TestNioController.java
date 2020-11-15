package com.arc.zero.controller.data.test.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestNioController {


    /**
     * 用NIO读取大文本（G以上）
     *
     * @author may
     */

    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream("d:\\temp\\outlineA.log");
        FileChannel fcin = fin.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 50);

        while (true) {
            buffer.clear();

            int flag = fcin.read(buffer);

            if (flag == -1) {
                break;
            }

            buffer.flip();

            FileOutputStream fout = new FileOutputStream("d:\\temp\\" + Math.random() + ".log");
            FileChannel fcout = fout.getChannel();

            fcout.write(buffer);
        }
    }
}


//替换数字为空字符
//
//var obj  =  document.getElementById('_id')
//
//obj.value.replace(\[0-9]\g, '');


//正则   https://www.w3cschool.cn/regexp/hz7v1pqe.html


//https://www.jb51.net/article/97732.htm
