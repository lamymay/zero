package com.arc.zero.controller.data.test.date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试参数传递时间
 *
 * @author May
 * @since 2020/4/7 9:33
 */
@Slf4j
@RestController
@RequestMapping("/test/date")
public class TestDateController {

    @RequestMapping("/1")
    public ResponseEntity test1(@RequestBody Model1 request) {
        log.debug("结果={}", request);
        return ResponseEntity.ok(request);
    }

    @RequestMapping("/2")
    public ResponseEntity test2(Date date) {
        return ResponseEntity.ok(date + "hello example");
    }

    @RequestMapping("/3")
    public ResponseEntity test3(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(date + "hello example");

    }

    @RequestMapping(value = "/4")
    public ResponseEntity fetchResult(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return ResponseEntity.ok(date + "hello example");
    }


}

