package com.arc.zero.service.mall.impl;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: yechao
 * @date: 2019/1/15 10:56
 */
@Slf4j
public class NameUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * The Unix separator character.
     */
    private static final char UNIX_SEPARATOR = '/';


    /**
     * The extension separator character.
     *
     * @since 1.4
     */
    public static final char EXTENSION_SEPARATOR = '.';

    //@todo 改进工具类效率

    /**
     * @param str
     * @return
     */
    public static String getCodeInName(String str) {
        try {
            log.debug("结果={}", str.substring(str.lastIndexOf(UNIX_SEPARATOR) + 1));
            return str.substring(str.lastIndexOf(UNIX_SEPARATOR) + 1);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNameInName(String str) {
        try {
            log.debug("结果={}", str.substring(str.lastIndexOf(UNIX_SEPARATOR) + 1));
            return str.substring(0, str.lastIndexOf(UNIX_SEPARATOR));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * string 1.00  返回 Integer 1
     *
     * @param codeString
     * @return
     */
    public static Integer getIntegerInCodeString(String codeString) {
        try {
            return Integer.valueOf(codeString.substring(0, codeString.lastIndexOf(EXTENSION_SEPARATOR)));
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

    /**
     * 格式化时间
     *
     * @param dateString
     * @return
     */
    public static Date getDateCodeString(String dateString) {
        try {
//            sj:2018-12-31 19:40:33
            return sdf.parse(dateString.replace("sj:", ""));
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

    /**
     * double格式的数据字符串转保留两位小数，四舍五入，后转为Integer
     *
     * @param codeString
     * @param precision  精度 ，即保留几位
     * @return
     */
    public static Double getUpToIntegerInCodeString(String codeString, int precision) {
        return Double.valueOf(String.format("%.2f", codeString));
    }

    public static Integer getUpToIntegerInCodeString1(String codeString) {
        try {
            if (codeString == null) {
                return null;
            } else {

                // 没有小数点        如: ”2267“
                // 小数点在第0位置 如：".2267"
                // 小数点在非第0位置，并且小数点后面只有一个小数      如："22.6"
                // 小数点在非第0位置，并且小数点后面只有两个小数     如："22.66"
                // 小数点在非第0位置，并且小数点后面有两个以上小数  如："22.667"

                int lastIndexOfPoint = codeString.lastIndexOf(EXTENSION_SEPARATOR);

                if (lastIndexOfPoint == -1) {
                    return Integer.valueOf(codeString);
                } else if (lastIndexOfPoint == 0) {

                    //尝试取出三个数字 1整数 2精度 3取舍位
                    Integer integerPart = 0;
                    Integer precisionPart = Integer.valueOf(codeString.substring(lastIndexOfPoint + 1, lastIndexOfPoint + 4 < codeString.length() ? lastIndexOfPoint + 4 : codeString.length()));
                    Integer carryBytePart = 0;


                } else {
                    Integer integerPart = Integer.valueOf(codeString.substring(0, lastIndexOfPoint + 3 < codeString.length() ? lastIndexOfPoint + 3 : codeString.length()));


                }

                if (lastIndexOfPoint > 0) {
                    //有小数点
                    if (codeString.length() > 3) {
//                        Integer test = Integer.valueOf(a.substring(lastIndexOfPoint+1, lastIndexOfPoint+3<a.length()? lastIndexOfPoint+3:a.length()));
                        //小数点后的两位
                        Integer back = Integer.valueOf(codeString.substring(lastIndexOfPoint + 1, lastIndexOfPoint + 3 < codeString.length() ? lastIndexOfPoint + 3 : codeString.length()));
                        Integer tem = Integer.valueOf(codeString.substring(3, 4));
                        if (tem > 4) {
                            return back += 1;
                        } else {
                            return back;
                        }
                    } else {
                        //.12=12
                        return Integer.valueOf(codeString.substring(1, codeString.length()));
                    }

                }
                //   .12    .123  .5567 这样的格式
                else if (lastIndexOfPoint == 0) {
                    if (codeString.length() > 3) {
                        Integer back = Integer.valueOf(codeString.substring(1, 3));
                        Integer tem = Integer.valueOf(codeString.substring(3, 4));
                        if (tem > 4) {
                            return back += 1;
                        } else {
                            return back;
                        }
                    } else {
                        //.12=12
                        return Integer.valueOf(codeString.substring(1, codeString.length()));
                    }
                } else {
                    //无小数点直接返回
                    return Integer.valueOf(codeString);
                }
            }

//            return Integer.valueOf(codeString.substring(0, codeString.lastIndexOf(EXTENSION_SEPARATOR)));
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

    public static String getStringInCodeString(String codeString) {
        try {
            return codeString.substring(0, codeString.lastIndexOf(EXTENSION_SEPARATOR));
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }


    public static Double getDoubleInCodeString(String doubleString) {
        try {
            return Double.valueOf(doubleString);
        } catch (Exception e) {
            log.error("{}", e);
            return null;
        }
    }

    public static void main(String[] args) {
//        test();
//        test3();
//        test4();
        test5();

    }

    private static void test5() {
        System.out.println(convertStringToNumberAddNegative("BR"));
    }

    private static void test4() {
        System.out.println(convertStringToNumber("a"));
        System.out.println(convertStringToNumber("z"));
        System.out.println(convertStringToNumber("aA"));
        System.out.println(convertStringToNumber("az"));

    }

    private static void test3() {

        System.out.println((int) 'A');//65
        System.out.println((int) 'Z');//90

//        System.out.println('Z'-'A');//25
//        System.out.println('a'-'A');//32

        System.out.println((int) 'a');///97  和大写字母之间有6个符号 [\]^_`
        System.out.println((int) 'z');//122

        System.out.println("------------");

        System.out.println(convertCharToNumber('a'));
        System.out.println(convertCharToNumber('a'));
        System.out.println(convertCharToNumber('Z'));
        System.out.println(convertStringToNumber("AZ"));// AA 26    AB 27
        System.out.println(convertStringToNumber("AA"));
        System.out.println(convertStringToNumber("AB"));

        System.out.println("------------");
        System.out.println(excelColStrToNum("Z"));//26
        System.out.println(excelColStrToNum("AA"));//AA=27=26+1
        System.out.println(excelColStrToNum("AB"));//AB=28=26+2
        System.out.println(excelColStrToNum("AZ"));//AZ=52=26+26    1*26+26

        System.out.println(excelColStrToNum("BA"));//BA=26+26+1     2*26+1
        System.out.println(excelColStrToNum("BZ"));//BA=26+26+26
    }

    private static void test() {


//        System.out.println(getNameInName("惠州市/277"));
//        System.out.println(getIntegerInCodeString("111.0"));
//        System.out.println(getIntegerInCodeString("0111.0"));
        //


        System.out.println("12".lastIndexOf(EXTENSION_SEPARATOR));
        System.out.println("12.667".lastIndexOf(EXTENSION_SEPARATOR));
        System.out.println("12.6".lastIndexOf(EXTENSION_SEPARATOR));
        System.out.println(".6".lastIndexOf(EXTENSION_SEPARATOR));

        System.out.println("12".length());
        System.out.println("12.667".length());
        System.out.println("12.6".length());
        System.out.println(".6".length());

        System.out.println("------------");
        String codeString = ".567";
        System.out.println(codeString.length());
        //该方法是从0 开始截取， 注意包含起点但不包含终点
        Integer back = Integer.valueOf(codeString.substring(1, 3));
        Integer tem = Integer.valueOf(codeString.substring(3, 4));

        System.out.println(back);
        System.out.println(tem);
        if (tem > 4) {
            back += 1;
            System.out.println(back);
        }

        System.out.println(".".length());//1
        System.out.println(".1".length());//2
        System.out.println(".12".length());//3
        System.out.println(".123".length());//4
        System.out.println("------------");

        String a = "23.723";
        int lastIndexOfPoint = a.lastIndexOf(EXTENSION_SEPARATOR);
        System.out.println(lastIndexOfPoint);//2
        Integer test = Integer.valueOf(a.substring(lastIndexOfPoint + 1, lastIndexOfPoint + 3 < a.length() ? lastIndexOfPoint + 3 : a.length()));
        System.out.println(test);//66
        System.out.println(a.substring(2, 4));//.6
        System.out.println(a.substring(3, 5));//66
        System.out.println("------------");
        String b = "33.456";
        System.out.println(b.indexOf("."));//2
        System.out.println("12".indexOf("."));//-1
        System.out.println("12.".indexOf("."));//2
        System.out.println(".1213".indexOf("."));//0
        System.out.println("444.12.13".indexOf("."));//3

//        System.out.println(String.format("%.2f",11222));//165.46
        System.out.println(String.format("%.2f", 165.4555413524));//165.46
        System.out.println(String.format("%.2f", 165.415413524));//165.42
        System.out.println(String.format("%.1f", 165.415413524));//165.4
        System.out.println(String.format("%.0f", 165.415413524));//165


        System.out.println("#####################");
        codeString = ".2345";
        System.out.println(codeString.substring(0, 1));//.
        System.out.println(codeString.substring(0, 2));//.2
        System.out.println(codeString.substring(1, 3));//23
        System.out.println(codeString.substring(1, 4));//234
        //0,2
        System.out.println("#####################");

        System.out.println(codeString.substring(1, codeString.lastIndexOf(".") + 3));
        System.out.println(codeString.substring(1, codeString.lastIndexOf(".") + 2 < codeString.length() ? codeString.lastIndexOf(".") : codeString.lastIndexOf(".") + 2));
        Integer precisionPart = Integer.valueOf(codeString.substring(lastIndexOfPoint + 1, lastIndexOfPoint + 4 < codeString.length() ? lastIndexOfPoint + 4 : codeString.length()));

        System.out.println(precisionPart);
    }


    /**
     * 根据string返回数字
     * 从零开始
     *
     * @param str
     * @return
     */
    public static Integer convertStringToNumberAddNegative(final String str) {
        return convertStringToNumber(str) - 1;
    }

    /**
     * 根据string返回
     *
     * @param str
     * @return
     */
    public static Integer convertStringToNumber(final String str) {
        //A-Z 65-90         a-z 97-122       大小写相差32
        int back = 0;
        for (char ch : str.toCharArray()) {
            back += convertCharToNumber(ch);
        }

        int length = str.length();
        if (length == 1) {
            return convertCharToNumber(str.toCharArray()[0]);
        } else if (length == 2) {
            // AA=27=26+1
            // AB=28=26+2
            // AC=29=26+3
            // AD=30=26+4
            // ....
            // AZ=52=26+26
            //从左往右拿

            return convertCharToNumber(str.toCharArray()[0]) * 26 + convertCharToNumber(str.toCharArray()[1]);
        }
        return back;
    }

    public static int convertCharToASCII(final char ch) {
        return (int) ch;
    }

    /**
     * char转换为1开始的数字
     *
     * @param ch
     * @return
     */
    public static int convertCharToNumber(final char ch) {
        int tem = (int) ch;
        return tem < 97 ? tem - 64 : tem - 96;
    }


    /**
     * 该方法用来将Excel中的ABCD列转换成具体的数据
     *
     * @param column:ABCD列名称
     * @return integer：将字母列名称转换成数字
     **/
    public static int excelColStrToNum(String column) {
        int num = 0;
        int result = 0;
        int length = column.length();
        for (int i = 0; i < length; i++) {
            char ch = column.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**
     * 该方法用来将具体的数据转换成Excel中的ABCD列
     *
     * @param columnIndex：需要转换成字母的数字
     * @return column:ABCD列名称
     **/
    public static String excelColIndexToStr(int columnIndex) {
        if (columnIndex <= 0) {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do {
            if (columnStr.length() > 0) {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }


}
