package com.arc.zero.controller.data.test.forward;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author May
 * @since 2020/1/14 11:06
 */
@Slf4j
@RequestMapping("/test2")
@Controller
public class ForwardTestController {

    String redirectedUrl = "http://127.0.0.1:8001/zero/test2/info";


    @RequestMapping("/test/getTemplate/1")
    public Object objec() {
        String url = "http://114.141.150.17/api/usercenter/sc/excel/user/template";
        String jwt = "bearereyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Nzg5NzI4NjcsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsid3JpdGUiLCJyZWFkIl0sImp0aSI6ImRkYzczMTVlLTdkOWItNDAwMi1iNzIxLTQ0N2ExZDJkMmFiYiIsImNsaWVudF9pZCI6Iko4S3ExNjloNTZ0bSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.HgN2tt1MkLQ3gjS2wnC7jHTt5R9UeEVsCVdcf5Ovd8w";

        String headerKey = "username";
        String headerValue = "admin";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        headers.set(headerKey, headerValue);
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Map.class);
        log.debug("结果={}", response);
        System.out.println(response);
        return response.getBody();
    }

    /*
     * forward 示例: 以字符串的形式构建目标url, url 需要加上 forward: 前缀
     * */
    @RequestMapping("/forward1")
    public String forwardTest1() {
        String path = "redirect:" + redirectedUrl;
        log.debug("重定向url={}", path);
        return path;
    }

    @RequestMapping("/forward2")
    public String forwardTest2() {
        return "forward:/test2/info";
    }


    //仅仅返回的是status=302 并未完成重定向
    @GetMapping("/forward3")
    @ResponseBody
    public ResponseEntity<String> forwardTest3() {
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(302);
        ResponseEntity<String> body = bodyBuilder.body(redirectedUrl);
        return body;
    }


    /**
     * 完成重定向  success
     *
     * @param attributes
     * @return
     */
    @GetMapping("/forward4")
    public RedirectView forward4(RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        log.debug("结果={}", attributes);
        log.debug("发起重定向,时刻={}", System.currentTimeMillis());
        return new RedirectView(redirectedUrl);
    }


    /**
     * 完成重定向  success
     *
     * @param model
     * @return
     */
    @GetMapping("/forward5")
    public ModelAndView forward5(ModelMap model) {
        model.addAttribute("attribute", "forward5");
        log.debug("forward5发起重定向,时刻={}", System.currentTimeMillis());
        return new ModelAndView("redirect:" + redirectedUrl, model);
    }

    /**
     * 完成重定向  success
     *
     * @param model
     * @return
     */
    @GetMapping("/forward6")
    public ModelAndView forward6(ModelMap model) {
        model.addAttribute("attribute", "forward6");
        log.debug("forward6 发起重定向,时刻={}", System.currentTimeMillis());
        return new ModelAndView("redirect:" + redirectedUrl, model);
    }


    /**
     * 完成重定向  success
     *
     * @return
     */
    @GetMapping("/forward7")
    public ModelAndView forward7() {
        log.debug("forward7 发起重定向,时刻={}", System.currentTimeMillis());
        return new ModelAndView("redirect:" + redirectedUrl);
    }


    /**
     * 资源接口=重定向到该接口
     *
     * @param request
     * @return
     */
    @GetMapping("/info")
    @ResponseBody
    public Object info(HttpServletRequest request) {
        log.debug("结果={}", request);
        Map<Object, Object> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            log.debug("请求头重的key/value={}/{}", key, value);
            map.put(key, value);
        }
        map.put("time", System.currentTimeMillis());
        log.debug("重定向到该接口,时刻={}", System.currentTimeMillis());
        return map;
    }

    public static void main(String[] args) {
        System.out.println(1578974329045L - 1578974329059L);
    }
}
