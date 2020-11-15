package com.arc.zero.controller.data.test.download;

import com.arc.core.model.vo.ResponseVo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author May
 * @since 2020/1/8 15:09
 */
@Slf4j
@RequestMapping("/test2")
@RestController
public class RestTemplateTestController {


    @PostMapping("/save")
    public ResponseVo<String> save(
            @RequestBody TestRestTemplateDto model,
            HttpServletRequest request) {
        log.debug("参数 model  ={}", model);
        log.debug("参数 model  ={}", model);
        log.debug("参数 model  ={}", model);
        log.debug("结果={}", request);

        Enumeration<String> headerNames = request.getHeaderNames();
        HttpHeaders headers = new HttpHeaders();
        ArrayList<String> keys = new ArrayList<>();

        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            keys.add(key);
            String value = request.getHeader(key);
            System.out.println(key + "--" + value);
            headers.add(key, value);
        }

        System.out.println("----------------");
        System.out.println(headers);

        return ResponseVo.success("OK");
    }


    @PostMapping("/update")
    public ResponseVo update(
            @RequestBody TestRestTemplateDto model,
            HttpServletRequest request) {
        log.debug("参数 model  ={}", model);
        log.debug("参数 model  ={}", request);
        return ResponseVo.success(model);
    }

    @PostMapping("/delete")
    public ResponseVo<Boolean> delete(
            @RequestBody TestRestTemplateDto model,
            HttpServletRequest request) {
        log.debug("参数 model  ={}", request);
        log.debug("参数 model  ={}", model);

        Enumeration<String> headerNames = request.getHeaderNames();
        HttpHeaders headers = new HttpHeaders();

        System.out.println("---- 查看 header --------");
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + "：" + value);
            headers.add(key, value);
        }

        System.out.println("--------- END ------");

        return ResponseVo.success(true);
//        return ResponseVo.success(null);//会报错
    }


    /**
     * 测试
     *
     * @param args
     */

    public static void main(String[] args) {
//        test1();
//        test12();
        test2();
    }

    private static void test1() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", "application/json");//        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "1111111111");
        headers.set("username", "admin");

        TestRestTemplateDto oldRoleDto = new TestRestTemplateDto();
        oldRoleDto.setCode("167");
        oldRoleDto.setDescription("描述444");
        oldRoleDto.setName("名称22");
        oldRoleDto.setUserCompanyId("b9901ab5-9095-435a-8628-db7683aeb3c7");

        //HttpEntity
        HttpEntity<TestRestTemplateDto> objectHttpEntity = new HttpEntity<>(oldRoleDto, headers);
        String url = "http://127.0.0.1:8001/zero/test2/save";
        try {
            ResponseEntity<Map> response1 = restTemplate.postForEntity(url, objectHttpEntity, Map.class);
            System.out.println("----------------------------------");
            System.out.println(response1);//<200,{code=1, msg=成功, data=OK},[Content-Type:"application/json;charset=UTF-8", Transfer-Encoding:"chunked", Date:"Thu, 09 Jan 2020 06:53:54 GMT"]>
            Map map = response1.getBody();
            System.out.println(map);//  map ={code=1, msg=成功, data=OK}

        } catch (Exception e) {
            log.error("e={}", e);
        }

        try {
            ResponseEntity<String> response2 = restTemplate.postForEntity(url, objectHttpEntity, String.class);
            System.out.println("----------------------------------");
            System.out.println(response2);
            System.out.println(response2.getBody());
            String body = response2.getBody();//string ="{code=1, msg=成功, data=OK}"
            System.out.println(body);

        } catch (Exception e) {
            log.error("e={}", e);
        }

    }

    //测试不同返回值 处理
    private static void test12() {
        String url = "http://127.0.0.1:8001/zero/test2/delete";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        //        ResponseEntity<Boolean> response1 = new RestTemplate().postForEntity(url, new HttpEntity<>(new TestRestTemplateDto(), headers), Boolean.class);//错误的
        //        System.out.println(response1);
        //        ResponseEntity<Map> response3 = new RestTemplate().postForEntity(url, new HttpEntity<>(new TestRestTemplateDto(), headers), Map.class);// 对的
        //        System.out.println(response3);

        ResponseEntity<TestResponse> response = new RestTemplate().postForEntity(url, new HttpEntity<>(new TestRestTemplateDto(), headers), TestResponse.class);
        System.out.println("----------------------------------");
        System.out.println(response);
        TestResponse body = response.getBody();
        Boolean data = body.getData();
        System.out.println(data);
        System.out.println(data);
        System.out.println(data);

    }

    private static void test2() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "111111111111111111");
        headers.set("content-type", "application/json");

//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.set("code", "名称22");
//        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
//
        Map<String, String> map = new HashMap<>();
        map.put("code", "名称22");
        map.put("name", "name999");
        map.put("description", "description");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(map, headers);

        try {

            String url = "http://127.0.0.1:8001/zero/test2/update";
            ResponseEntity<String> response = new RestTemplate().postForEntity(url, request, String.class);
            System.out.println("----------------------------------");
            System.out.println(response.getBody());
            System.out.println(response.getBody());

        } catch (Exception e) {
            log.error("e={}", e);
        }

    }

}

/**
 * dto for test save
 *
 * @author X
 */
@Data
@NoArgsConstructor
class TestRestTemplateDto {

    private String code = "code-01";//
    private String name = "default";// 名称
    private String description = "lllllllll";// 描述
    private String userCompanyId = "12345678";


}

@Data
@NoArgsConstructor
class TestResponse {
    private int code;
    private String msg;
    private Boolean data;

}


//        ResponseEntity<Map> response3 = new RestTemplate().postForEntity(url, new HttpEntity<>(new TestRestTemplateDto(), headers), Map.class);// 对的
//总结 responseEntity 是包含 1、http的 status状态 2、headers 3、body==》可以对应一个model
//注意：
// 1、如果的数据就是null 会报异常 org.springframework.web.client.HttpServerErrorException$InternalServerError: 500 null
// 2、请求参数 可以是普通Map，而MultiValueMap会报异常
