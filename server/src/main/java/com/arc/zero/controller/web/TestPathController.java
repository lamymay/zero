package com.arc.zero.controller.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;


/**
 * @author 叶超
 * @since 2019/10/28 23:28
 */
@Slf4j
@RequestMapping({"/test", "/"})
@Controller
public class TestPathController {


    @RequestMapping(value = {"", "/", "/index", "/index.html"})
    @ResponseBody
    public Object index1() {
        log.debug("index1");
        return LocalDateTime.now();
    }

    @GetMapping(value = {"/arc_login", "/arc_login.html"})
    @ResponseBody
    public Object index2() {
        log.debug("index2");
        return LocalDateTime.now();
    }
}
