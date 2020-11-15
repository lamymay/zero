package com.arc.zero.controller.data.test.ua;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yechao
 * @date 2020/9/11 2:56 下午
 */
@Slf4j
@RestController
@RequestMapping("/test/ua")
public class TestUAController {


    @GetMapping("")
    public Object info(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        System.out.println("map to json string:" + JSON.toJSONString(map));
        System.out.println("alipay 的 user-agent:\n" + map.get("user-agent"));
        return map;
    }

}

//alipay 的 user-agent ->
// Mozilla/5.0 (Linux; U; Android 10; zh-CN; H9493 Build/52.1.A.3.49) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/69.0.3497.100 UWS/3.21.0.169 Mobile Safari/537.36 AlipayChannelId/5136 UCBS/3.21.0.169_200731162109 NebulaSDK/1.8.100112 Nebula AlipayDefined(nt:WIFI,ws:411|0|3.5,ac:sp) AliApp(AP/10.2.0.8026) AlipayClient/10.2.0.8026 Language/zh-Hans useStatusBar/true isConcaveScreen/false Region/CN NebulaX/1.0.0 Ariver/1.0.0

//String userAgent = request.getHeader("user-agent");
//map.put("user-agent", userAgent);