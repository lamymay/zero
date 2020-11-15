package com.arc.zero.test.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author yechao
 * @date 2020/9/9 2:55 下午
 */
public class TestMap {

    public static void main(String[] args) {
        Bean bean = new Bean();
        JSONObject map1 = bean.getExtMap();
        map1.put("key1", 123);
        map1.put("key2", 234);
        bean.setExtMap(map1);
        System.out.println("直接 map1 的hashCode" + map1.hashCode());
        System.out.println("bean中map的hashCode" + bean.getExtMap().hashCode());


        JSONObject map2 = new JSONObject();
        map2.put("k1", "avr1");
        map2.put("k2", "avr2");

        bean.setExtMap(map2);
        System.out.println("直接 map2 的hashCode" + map2.hashCode());
        System.out.println("bean中map的hashCode" + bean.getExtMap().hashCode());
        System.out.println(JSON.toJSONString(bean));





    }
}

class Bean {

    /**
     * 扩展字map
     */
    private JSONObject extMap;

    public JSONObject getExtMap() {
        if (this.extMap == null) {
            return new JSONObject();
        }
        return extMap;
    }

    /**
     * 注意是追加的
     *
     * @param extMap 参数map
     */
    public void setExtMap(JSONObject extMap) {
        if (this.extMap == null) {
            this.extMap = extMap;
        }
        JSONObject hadMap = this.extMap;
        hadMap.putAll(extMap);
        this.extMap = hadMap;
    }

}



