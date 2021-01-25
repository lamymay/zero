package com.arc.zero.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author yechao
 * @since 2020/5/10 2:54 下午
 */
public class CodeUtil {

    /**
     * 夺宝码的取值范围
     */
    public static String STR = "123456789";

    /**
     * 夺宝码的长度
     */
    public static int INIT_REDEMPTION_CODE_LENGTH = 8;

    public static final String ONE_ONE = "11";

    /**
     * 创建一个随机的字符串的code length=16
     *
     * @return 创建一个随机的字符串的code
     */
    public static String createCode() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16).toUpperCase();
    }

    /**
     * 创建一个随机的字符串的code length=32
     *
     * @return 创建一个随机的字符串的code
     */
    public static String createCodeLength32() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String createAliUid() {
        return "U" + getUUID(15);
    }

    public static String getUUID(int maxLength) {
        if (maxLength < 1) {
            maxLength = 16;
        }
        if (maxLength > 64) {
            maxLength = 64;
        }
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, maxLength).toUpperCase();
    }

    public static final String mosaic = "*";

    public static String addMosaicNickname(String nickname) {
        int userNicknameTruncateLength = 20;
        if (nickname == null || StringUtils.isBlank(nickname)) {
            return mosaic + mosaic;
        } else if (nickname.length() == 1) {
            return nickname + mosaic;
        } else if (nickname.length() == 2) {
            return nickname.substring(0, 1) + mosaic;
        } else {
            int length = nickname.length();
            int tempLength = length - 2;
            if (tempLength > userNicknameTruncateLength) {
                tempLength = userNicknameTruncateLength;
            }
            int i = 0;
            StringBuffer buffer = new StringBuffer();
            while (i < tempLength) {
                buffer.append(mosaic);
                ++i;
            }
            return nickname.substring(0, 1) + buffer.toString() + nickname.substring(length - 1, length);
        }
    }


    /**
     * 从一个范围内获取一个随机数
     *
     * @param start 下限 (必须为非负数,包含在返回中中)
     * @param end   上限  ( 上限不包含在返回值中)
     * @return 随机数 范围是[start,end] 闭区间
     */
    public static long createRandomId(final long start, final long end) {
        if (start < 0) {
            throw new RuntimeException("下限值必须为非负数");
        }
        return org.apache.commons.lang3.RandomUtils.nextLong(start, end);
    }


    /**
     * @return 获取初始兑奖码 8位 有概率重复
     */
    public static int initRedemptionCode() {
        return Integer.valueOf(RandomStringUtils.random(INIT_REDEMPTION_CODE_LENGTH, STR));
    }

    /**
     * @return 获取初始兑奖码 8位 有概率重复
     */
    public static int initRedemptionCodeStartWithOne() {
        return Integer.valueOf(ONE_ONE + RandomStringUtils.random(INIT_REDEMPTION_CODE_LENGTH, STR).substring(2));
    }

    /**
     * 从一个范围内获取一个随机数
     *
     * @param start 下限 (必须为非负数,包含在返回中中)
     * @param end   上限  ( 上限不包含在返回值中)
     * @return 随机数 范围是[start,end) 区间
     */
    public static long createRandomNumber(final long start, final long end) {
        if (start < 0) {
            throw new RuntimeException("下限值必须为非负数");
        }
        return org.apache.commons.lang3.RandomUtils.nextLong(start, end);
    }


    public static void main(String[] args) {
        //testCreateRandomId();
        //testAddMosaicNickname();
        testGetRandom();

    }

    private static void testGetRandom() {
        // 测试活动 1589  7274274746FD4587   duob_IT_7274274746FD4587   _NDkI0333kIqLEJCoeEj0w

        //************************************************************************************************************************************
        //"extend": "{\"WINNING_USER_RECORD_COUNT\":1,\"REDEMPTION_CODE_LOW_LIMIT\":11986230,\"TOTAL_TIME_OF_CUMULATIVE_PARTICIPANTS\":2,\"TOTAL_NUMBER_OF_CUMULATIVE_PARTICIPANTS\":2}",
        //{"extend":"{"REDEMPTION_CODE_LOW_LIMIT":11627757}"}
        //1533 AAD301720C324412 "extend": "{\"WINNING_USER_RECORD_COUNT\":1,\"REDEMPTION_CODE_LOW_LIMIT\":11986230,\"TOTAL_TIME_OF_CUMULATIVE_PARTICIPANTS\":2,\"TOTAL_NUMBER_OF_CUMULATIVE_PARTICIPANTS\":2}",

        // duob_ITPS_C5A97A538E6A43FA       奖池缓存key
        // duob_IT_C5A97A538E6A43FA         夺宝码incr的key
        // duob_CGIBC_29E89815D0344C3B      测试取出实例的缓存 for cache selectByInstanceCode


        // 1 更新extend字段  id=1533  value= {"id":1533,"extend":"{\"REDEMPTION_CODE_LOW_LIMIT\":12323465}"}
        // 2 更新缓存 key=duob_IT_AAD301720C324412  value=12323466  [12323465 12323466 12323467]  当前最大值=12479792
        // 3 之后需还原数据 update db
        // 4 update cache


        //duob_IT_18DF34E81AC2442F
        System.out.println("############################################################# ?11");
        System.out.println(CodeUtil.createRandomNumber(12323465, 12323465 + 1)); //12323466

        HashMap<String, Object> map = new HashMap<>();
        map.put("REDEMPTION_CODE_LOW_LIMIT", 12323465);
        System.out.println(JSON.toJSON(map));

        String temp = "{\"REDEMPTION_CODE_LOW_LIMIT\":12323465}";
        JSONObject jsonObject = JSON.parseObject(temp);
        System.out.println(jsonObject);

        System.out.println("############################################################# SW");
        System.out.println(CodeUtil.createRandomNumber(13141195, 13141195 + 1)); //13141195
        System.out.println(CodeUtil.createRandomNumber(13141195, 13141195 + 1)); //13141195
        System.out.println(CodeUtil.createRandomNumber(13141195, 13141195 + 1)); //13141195
        System.out.println(CodeUtil.createRandomNumber(13141195, 13141195 + 1)); //13141195
        System.out.println(CodeUtil.createRandomNumber(13141195, 13141195 + 1)); //13141195


        System.out.println("############################################################# ?");


        //************************************************************************************************************************************

    }

    private static void testAddMosaicNickname() {

        System.out.println(createAliUid());
        System.out.println(createAliUid());
        System.out.println(createAliUid());
        System.out.println(createAliUid());

        System.out.println(DateUtils.addHours(new Date(), -1));
        System.out.println(DateUtils.addHours(new Date(), -10));

        String nickname = "A";
        System.out.println(nickname.substring(0, 1));
        System.out.println("首字母=" + nickname.substring(0, 1));
        System.out.println("尾字母=" + nickname.substring(nickname.length() - 1, nickname.length()));

        nickname = "AB";
        System.out.println(nickname.substring(0, 1));
        System.out.println("首字母=" + nickname.substring(0, 1));
        System.out.println("尾字母=" + nickname.substring(nickname.length() - 1, nickname.length()));


        nickname = "ABC";
        System.out.println(nickname.substring(0, 1));

        System.out.println("首字母=" + nickname.substring(0, 1));
        System.out.println("尾字母=" + nickname.substring(nickname.length() - 1, nickname.length()));

        nickname = "ABCD";
        System.out.println(nickname.substring(0, 1));
        System.out.println("首字母=" + nickname.substring(0, 1));
        System.out.println("尾字母=" + nickname.substring(nickname.length() - 1, nickname.length()));


        System.out.println(addMosaicNickname(null));
        System.out.println(addMosaicNickname(""));
        System.out.println(addMosaicNickname("   "));
        System.out.println(addMosaicNickname("张"));
        System.out.println(addMosaicNickname("张三"));
        System.out.println(addMosaicNickname("张三哈"));
        System.out.println(addMosaicNickname("赵子龙"));
        System.out.println(addMosaicNickname("撒豆腐阿斯达"));
        System.out.println(addMosaicNickname("撒豆腐阿大师傅法撒旦是非得失而又特容易斯达"));

    }

    public static void testCreateRandomId() {
        int max = 10000;
        int current = 0;

        int zero = 0;
        int one = 0;
        while (current < max) {
            current = current + 1;
            long randomId = createRandomId(0, 2);
            if (randomId == 0) {
                zero = zero + 1;
            }
            if (randomId == 1) {
                one = one + 1;
            }
        }
        System.out.println("测试一,zero=" + zero + "one=" + one);
        System.out.println("测试二:" + createRandomId(85547829, 95547829));

    }


}


