package com.arc.zero.test.tree.tree2;

import org.json.JSONObject;

/**
 * @author yechao
 * @since 2020/8/21 3:41 下午
 */
public class TestJson {

    public static void main(String[] args) {
        //                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("enrollStartTime", "20200821 06:00:00");
        jsonObject.put("enrollEndTime", "20200821 09:00:00");
        System.out.println(jsonObject.toString());
        //"{\"enrollStartTime\":\"20200821 06:00:00\",\"enrollEndTime\":\"20200821 09:00:00\"}"
        //INSERT INTO `activity_template` VALUES (1, '2020-08-21 15:40:08', '2020-08-21 15:40:08', 'yechao', '每日红包塞', 1, 1, 1, 1, 1, '{}', '{}', '{}', '{}', '{}');
        //INSERT INTO `activity_template` VALUES (2, '2020-08-21 15:46:07', '2020-08-21 15:46:07', 'yechao', '每日红包塞', 1, 1, 1, 1, 1, '{}', '{\"enrollStartTime\":\"20200821 06:00:00\",\"enrollEndTime\":\"20200821 09:00:00\"}', '{}', '{}', '{}');
    }
}
