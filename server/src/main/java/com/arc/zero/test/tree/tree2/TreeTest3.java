package com.arc.zero.test.tree.tree2;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yechao
 * @since 2020/8/25 3:46 下午
 */
//3、通过baseDao查出来的List.map转树结构
//data为模拟baseDao.query("...")查出来的数据

public class TreeTest3 {
    public static void main(String[] args) {
        //-----------------------------------
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("id", 1);
        map1.put("pid", 0);
        map1.put("name", "甘肃省");
        data.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("id", 2);
        map2.put("pid", 1);
        map2.put("name", "天水市");
        data.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("id", 3);
        map3.put("pid", 2);
        map3.put("name", "秦州区");
        data.add(map3);
        Map<String, Object> map4 = new HashMap<>();
        map4.put("id", 4);
        map4.put("pid", 0);
        map4.put("name", "北京市");
        data.add(map4);
        Map<String, Object> map5 = new HashMap<>();
        map5.put("id", 5);
        map5.put("pid", 4);
        map5.put("name", "昌平区");
        data.add(map5);
        List<Map<String, Object>> MenuList = new ArrayList<Map<String, Object>>();
        //把从数据库查询出来的数据遍历处理
        for (Map<String, Object> vo : data) {
            Map<String, Object> map = new HashMap<String, Object>();
            if ("0".equals(vo.get("pid").toString())) {//通过判断，0代表没有父级，也就是一级
                map.put("children", getChildren(data, Long.valueOf(vo.get("id").toString())));//继续往一级下面遍历，调用下面的处理方法，放入树状结构
                map.put("id", vo.get("id"));
                map.put("label", vo.get("name"));
                map.put("pid", vo.get("pid"));
                MenuList.add(map);//先把一级存入结果
            }

        }
        System.out.println(JSON.toJSONString(MenuList));
        //----------------------------------------
    }

    //结果集转树状结构
    public static List<Map<String, Object>> getChildren(List<Map<String, Object>> data, Long menuId) {//参数为数据库的（原数据，一级id）
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (data == null || data.size() == 0 || menuId == null) {
            return list;
        }

        for (Map<String, Object> vo : data) {
            Map<String, Object> map = new HashMap<String, Object>();
            if (menuId.toString().equals(vo.get("pid").toString())) {//如果本级id与下面数据的父id相同，就说明是子父级关系
                map.put("children", getChildren(data, Long.valueOf(vo.get("id").toString())));//递归，查询子级下的子级
                map.put("id", vo.get("id"));
                map.put("label", vo.get("name"));
                map.put("pid", vo.get("pid"));
                list.add(map);
            }

        }
        return list;
    }
}
