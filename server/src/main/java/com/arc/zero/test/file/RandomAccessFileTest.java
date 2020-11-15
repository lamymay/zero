package com.arc.zero.test.file;

import java.io.RandomAccessFile;

/**
 * @author May
 * @since 2020/1/9 17:17
 */
public class RandomAccessFileTest {
    public static void main(String[] args) throws Exception {
        Employee e1 = new Employee("zhangsan", 23);
        Employee e2 = new Employee("lisi", 24);
        Employee e3 = new Employee("wangwu", 25);
        RandomAccessFile ra = new RandomAccessFile("d:\\employee.txt", "rw");
        ra.write(e1.name.getBytes());
        ra.writeInt(e1.age);
        ra.write(e2.name.getBytes());
        ra.writeInt(e2.age);
        ra.write(e3.name.getBytes());
        ra.writeInt(e3.age);
        ra.close();
        RandomAccessFile raf = new RandomAccessFile("d:\\employee.txt", "r");
        int len = 8;
        raf.skipBytes(12);//跳过第一个员工的信息，其姓名8字节，年龄4字节
        System.out.println("第二个员工信息：");
        String str = "";
        for (int i = 0; i < len; i++) {
            str = str + (char) raf.readByte();
        }
        System.out.println("name:" + str);
        System.out.println("age:" + raf.readInt());
        System.out.println("第一个员工信息：");
        raf.seek(0);//将文件指针移动到文件开始位置
        str = "";
        for (int i = 0; i < len; i++) {
            str = str + (char) raf.readByte();
        }
        System.out.println("name:" + str);
        System.out.println("age:" + raf.readInt());
        System.out.println("第三个员工信息：");
        raf.skipBytes(12);//跳过第二个员工的信息
        str = "";
        for (int i = 0; i < len; i++) {
            str = str + (char) raf.readByte();
        }
        System.out.println("name:" + str);
        System.out.println("age:" + raf.readInt());
        raf.close();
    }
}

class Employee {
    String name;
    int age;
    final static int LEN = 8;

    public Employee(String name, int age) {
        if (name.length() > LEN) {
            name = name.substring(0, 8);
        } else {
            while (name.length() < LEN) {
                name = name + "\u0000";
            }
            this.name = name;
            this.age = age;
        }
    }


}


//Java除了File类之外，还提供了专门处理文件的类，即RandomAccessFile（随机访问文件）类。该类是Java语言中功能最为丰富的文件访问类，它提供了众多的文件访问方法。RandomAccessFile类支持“随机访问”方式，这里“随机”是指可以跳转到文件的任意位置处读写数据。在访问一个文件的时候，不必把文件从头读到尾，而是希望像访问一个数据库一样“随心所欲”地访问一个文件的某个部分，这时使用RandomAccessFile类就是最佳选择。
//————————————————
//版权声明：本文为CSDN博主「奋斗青年一族」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/dimudan2015/article/details/81910690


//2

//https://segmentfault.com/a/1190000008197990
