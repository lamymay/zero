//package com.arc.zero.controller.web;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//
///**
// * @author 叶超
// * @since 2019/10/28 23:28
// */
//@Slf4j
//@Controller
//public class IndexController {
//
//    private String index = "index";
//    private String arc_login = "user";
//    private String login = "login";
//    private String test = "test";
//
//    private String user = "sys/user";
//    private String role = "/sys/role";
//    private String back = "/back/back";
//
//
//    @RequestMapping(value = {"", "/", "/index", "/index.html"})
//    public String index() {
//        return index;
//    }
//
//    @GetMapping(value = {"/arc_login", "/arc_login.html"})
//    public String arcLogin() {
//        return arc_login;
//    }
//
//    @GetMapping("/login")
//    public String testLogin() {
//        log.debug("--------------------->/login");
//        return login;
//    }
//
//    @GetMapping(value = "/test")
//    public String configLoginHtml() {
//        return test;
//    }
//
//
//
//    @GetMapping(value = "/sys/user")
//    public String user() {
//        log.debug("--------------------->/user");
//        return user;
//    }
//
//    @GetMapping(value = "/sys/role")
//    public String role() {
//        return role;
//    }
//
//    @GetMapping(value = "/back/back")
//    public String back() {
//        return back;
//    }
//
//}
