package com.arc.zero.controller.data.system;
//
//import com.arc.core.model.domain.system.SysLog;
//import com.arc.core.model.vo.ResponseVo;
//import com.arc.zero.service.system.SysLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//
///**
// * data 包下的 controller仅仅用作  返回json数据 ，禁止页面跳转使用，页面跳转使用的操作请移步web包
// * 用户相关的的接口by RESTful
// *
// * @author 叶超
// * @date 2018/12/25
// */

import com.arc.core.model.domain.system.Log;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.mapper.system.LogMapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RestController
@RequestMapping("/sys/log")
public class LogController {

    /**
     *
     */
    @Resource
    private LogMapper logMapper;

    //    @Resource(name = "logServiceImpl")  private SysLogService logService;
    //
    ////    @Resource(name = "logServiceImpl2")
    ////    private SysLogService logService;
    @PostMapping(value = "/save")
    public ResponseVo save(@RequestBody Log log) {
        int insert = logMapper.insert(log);
        if (insert == 1) {
            return ResponseVo.success(log.getId());
        }
        return ResponseVo.failure();
    }

    @GetMapping(value = "/delete/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        int delete = logMapper.deleteById(id);
        return delete == 0 ? ResponseVo.failure() : ResponseVo.success(delete);
    }


    @PostMapping("/update")
    public ResponseVo update(@RequestBody Log log) {
        System.out.println("log="+log.toString());
        return ResponseVo.success(logMapper.updateById(log));
    }

    @GetMapping(value = "/{id}")
    public ResponseVo get(@PathVariable("id") Long id) {
        return ResponseVo.success(logMapper.selectById(id));
    }

    @PostMapping(value = "/page")
    public ResponseVo list() {
        Wrapper<Log> queryWrapper = new LambdaQueryWrapper();
        IPage<Log> pageable = new Page(1, 10);
        IPage<Log> page = logMapper.selectPage(pageable, queryWrapper);
        return ResponseVo.success(page);
    }


}

