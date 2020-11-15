package com.arc.zero.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.arc.core.model.domain.system.SysDataDictionary;
import com.arc.zero.service.system.SysDataDictionaryService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/9/29 23:27
 */

@Slf4j
@Service
public class SysDataDictionaryServiceImpl implements SysDataDictionaryService {

    @Override
    public Long save(SysDataDictionary dictionary) {
        return null;
    }

    @Override
    public int batchSave(List<SysDataDictionary> dictionary) {
        return 0;
    }

    @Override
    public int update(SysDataDictionary dictionary) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public SysDataDictionary get(Long id) {
        return null;
    }

    @Override
    public List<SysDataDictionary> list() {
        return null;
    }

    @Override
    public Object page() {
        return null;
    }

    @Override
    public List<Map<String, String>> listByType(String type) {
        return null;
    }


    public static void main(String[] args) {


        //        user1.put("displayName", "方小军");
        //        user1.put("avatar", "https://icon.com");

        //#pwd
        //#/data/project/java/zero/server/src/main/resources/config

        //连续早起冠 连续早起冠军获得金额 单位:分 7820分=78.20元军
        HashMap<String, Object> user1 = new HashMap<>();
        // taobaoIndexGeUpEarlyChampionTotalGet
        user1.put("totalGet", "7820");
        //连续早起冠军的早起次数  taobaoIndexGeUpEarlyChampionGetUpEarlyTimes
        user1.put("getUpEarlyTimes", "28");
        //连续早起冠军的平均早起时间 taobaoIndexGeUpEarlyChampion GetUpAtAverage
        user1.put("getUpAtAverage", "6:16");

        //ailiuid
        user1.put("aliuid", "IDXXXXXX");
        user1.put("taobaoId", "IDXXXXXX(如有)");
        user1.put("alipayId", "IDXXXXXX(如有)");


        // 每个冠军都有的属性 对应列: value
        // totalGet= 获得金额,单位:分,例:7820分=78.20元
        // getUpEarlyTimes=早起次数
        // getUpAtAverage=平均早起时间(要计算得到)


        //早起盈利冠军
        HashMap<String, Object> user2 = new HashMap<>();
        user2.put("totalGet", "2920");
        user2.put("getUpEarlyTimes", "20");
        user2.put("getUpAtAverage", "8:20");

        user2.put("aliuid", "IDXXXXXX");
        user2.put("taobaoId", "IDXXXXXX(如有)");
        user2.put("alipayId", "IDXXXXXX(如有)");


        //早起时间冠军
        HashMap<String, Object> user3 = new HashMap<>();
        user3.put("totalGet", "6220");
        user3.put("getUpEarlyTimes", "18");
        user3.put("getUpAtAverage", "5:01");

        user3.put("aliuid", "IDXXXXXX");
        user3.put("taobaoId", "IDXXXXXX(如有)");
        user3.put("alipayId", "IDXXXXXX(如有)");


        // 数据库表所在日常环境的开发库: alisports_activity_0000@11.164.113.228:3306【kuka_a1c2ed8cd5fb4b3d9c801a77c7048a0e】
        // 三个冠军 对应列:biz_key
        // taobaoIndexRegularChampion=淘宝端首页连续早起冠军
        // taobaoIndexMakeMoneyChampion=淘宝端首页早起盈利冠军
        // taobaoIndexGeUpEarlyChampion=淘宝端首页早起打卡冠军
        HashMap<String, Object> map = new HashMap<>();
        map.put("taobaoIndexGeUpEarlyChampion", user1);
        map.put("taobaoIndexRegularChampion", user2);
        map.put("taobaoIndexMakeMoneyChampion", user3);

        System.out.println(JSON.toJSONString(map));
        System.out.println(JSON.toJSONString(user1));
        System.out.println(JSON.toJSONString(user2));
        System.out.println(JSON.toJSONString(user3));

        String c = "{\"getUpAtAverage\":\"5:01\",\"totalGet\":\"6220\",\"taobaoId\":\"IDXXXXXX(如有)\",\"getUpEarlyTimes\":\"18\",\"aliuid\":\"IDXXXXXX\",\"alipayId\":\"IDXXXXXX(如有)\"}";
    }



}

