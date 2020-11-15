package com.arc.zero.controller.web;

import com.arc.core.config.annotations.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * web 包下的 controller仅仅用作页面跳转使用，禁止返回json数据， 返回json数据的操作请移步data包
 *
 * @author 叶超
 * @since 2019/1/2 23:24
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestPageController {

    @RequestMapping(value = "1", method = {RequestMethod.GET, RequestMethod.POST})
    @Note("1")
    public String test1() {
        return "/index";
    }

/*

    @RequestMapping(value = "2", method = {RequestMethod.GET, RequestMethod.POST})
    @Note("2")
    public String test2() {
        return "/index";
    }

    @RequestMapping(value = "3", method = {RequestMethod.GET, RequestMethod.POST})
    @Note("3")
    public String test3() {
        return "/index";
    }


    @RequestMapping(value = "4")
    @Note("4")
    public String test4() {
        return "/index";
    }

    @RequestMapping(value = "5")
    @Note("5")
    public String a5() {
        return "/index";
    }
*/


    @GetMapping("/rt")
    public ResponseEntity testResponseEntity() {
        return ResponseEntity.ok(Arrays.asList("1,2,3,4".split(",")));
    }

    @GetMapping("/get/{code}")
    public ResponseEntity testPathValue(@PathVariable String code) {
        log.debug("结果={}", code);
        return ResponseEntity.ok("GetMapping=" + code);
    }

    @PostMapping("/post/{code}")
    public ResponseEntity testPostPathValue(@PathVariable String code) {
        log.debug("结果={}", code);
        return ResponseEntity.ok("PostMapping=" + code);
    }
}
