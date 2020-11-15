package com.arc.zero.test.async;

import com.arc.core.model.domain.system.KeyValue;
import com.arc.core.model.domain.system.SysUser;
import com.arc.zero.service.system.KeyValueService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * @author yechao
 * @since 2020/6/2 11:34 上午
 */
@Slf4j
@Service
public class AsyncWriteService {

    /**
     * KV存储服务
     */
    @Autowired
    private KeyValueService keyValueService;

    public Long save(KeyValue keyValue) {
        // 1 insert keyValue as parent
        // 2 insert data async  异步批量处理子list的update
        // 3 return success
        long t1 = System.currentTimeMillis();
        Long save = keyValueService.save(keyValue);
        List<KeyValue> kvList = builtMockKVList(save);
        keyValueService.saveBatchAsync(kvList);
        long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);
        log.debug("clz={},耗时={}ms", this.getClass(), (t2 - t1));
        return save;
    }
    //耗时=2258ms
    //耗时=2235ms
    //耗时=3101ms
    //耗时=4740ms
    //耗时=4608ms
    //耗时=4697ms
    ///平均一次insert 耗时 40ms

    /**
     * 构建临时资源数据list
     *
     * @param userId
     * @return
     */
    private List<KeyValue> builtMockKVList(Long userId) {
        List<KeyValue> keyValueList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            keyValueList.add(new KeyValue(String.valueOf(i), "value" + i, i, "测试异步写入"));
        }
        return keyValueList;

    }

    public Long update(SysUser user) {
        // 1 update user
        // 2 insert data async
        // 3 return success

        return 1L;
    }

    public static void main(String[] args) {


        testGetBoolean();
        String avatar = "qqq";
        String nickname = null;

        if (StringUtils.isBlank(avatar) || StringUtils.isBlank(nickname) || "**".equals(nickname)) {
            System.out.println("avatar=" + avatar);
            System.out.println("nickname=" + nickname);
        }
        int maxValue = Integer.MAX_VALUE;
        System.out.println(maxValue);
        System.out.println(2147483647);

        System.out.println("---------");
        Object testObject = new ArrayList();
        displayObjectClass(testObject);

    }


    public static void displayObjectClass(Object o) {
        if (o instanceof Vector)
            System.out.println("对象是 java.util.Vector 类的实例");
        else if (o instanceof ArrayList)
            System.out.println("对象是 java.util.ArrayList 类的实例");
        else
            System.out.println("对象是 " + o.getClass() + " 类的实例");
    }


    private static void testGetBoolean() {

        HashMap<String, String> data = new HashMap<>();
        data.put("compareTime", "false");
        String ignoreTimeString = data.get("compareTime");
        System.out.println(ignoreTimeString);

        boolean temp = Boolean.valueOf(ignoreTimeString);
        System.out.println("ignoreTimeString=" + temp);

        temp = Boolean.valueOf(null);
        System.out.println("null=" + temp);

    }



//    public static void main(String[] args) {
//        JSONObject map = new JSONObject();
//        String  userId="ABSSJD";
//        String instanceCode = CodeUtil.createCode();
//        ArrayList<String> redemptionCodes = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            redemptionCodes.add(CodeUtil.initRedemptionCode() + "");
//        }
//        map.put(DuoBaoMQConsumer.USER_ID, userId);
//        map.put(DuoBaoMQConsumer.INSTANCE_CODE, instanceCode);
//        map.put(DuoBaoMQConsumer.TICKET_LIST, redemptionCodes);
//        testJsonObject(map);
//
//    }
//
//    private static void testJsonObject(JSONObject map) {
//        String userId = (String) map.get(DuoBaoMQConsumer.USER_ID);
//        String instanceCode = (String) map.get(DuoBaoMQConsumer.INSTANCE_CODE);
//        List<String> redemptionCodes = (List<String>) map.get(DuoBaoMQConsumer.TICKET_LIST);
//        System.out.println(userId);
//        System.out.println(instanceCode);
//        System.out.println(redemptionCodes);
//    }
}
