package com.arc.zero.controller.data.test.header;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/9/30 10:56
 */
@Slf4j
@RestController
public class TestDataController {

    @PostMapping(value = "/test/http",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String demoTest2(@RequestBody User user, HttpServletRequest request, @RequestHeader String phone) {

        Enumeration<String> headerNames = request.getHeaderNames();

        System.out.println(request.getHeader("token"));
        System.out.println(request.getHeader("phone"));

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        System.out.println(phone);
        return "success";
    }

    public static void main(String[] args) {
//        String response = restTemplate.getForObject("http://www.baidu.com", String.class);
//        System.out.println(response);
        String url = "http://127.0.0.1:8001/zero/test/http";

        //Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("phone", "18674192462");
        headers.set("token", "Bear 18674192462");

        //@RequestBody 获取body参数
        Map<String, Object> params = new HashMap<>();
        params.put("name", "yyc");
        params.put("email", "12306");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity httpEntity = new HttpEntity(params, headers);
        ResponseEntity<String> request = restTemplate.postForEntity(url, httpEntity, String.class);
        System.out.println(request.getBody());

    }

    class User {

        private String name;

        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}

