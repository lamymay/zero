package com.arc.zero.controller.data.security;

//import com.arc.core.model.request.system.UserRequest;
//import com.arc.utils.Assert;

import com.arc.core.model.domain.system.SysUserAuth;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户相关的的接口by RESTful
 *
 * @author yehcao
 * @date 2018/12/25
 */
@Slf4j
@RestController
@RequestMapping("/v2")
public class LoginProcessController {

    @Resource
    private SysUserService userService;

    //   todo 待删除 测试登陆
    @PostMapping(value = "/login/form/process")
    public ResponseEntity loginV1(SysUserAuth ua) {
        log.debug("LOGIN");
        log.info("LOGIN");
        log.warn("LOGIN");
        return ResponseEntity.ok(ResponseVo.success(userService.login(ua)));
    }

    @PostMapping(value = "/login/json/process")
    public ResponseEntity loginV2(@RequestBody SysUserAuth ua) {
        log.debug("结果={}", ua);
        return ResponseEntity.ok(ResponseVo.success(userService.login(ua)));
    }

    //   todo 待删除 测试登陆
    @GetMapping("/2/{u}/{p}/{identityType}")
    public ResponseEntity test1(@PathVariable String u, @PathVariable String p, @PathVariable Integer identityType) {
        Assert.notNull(u, "username 不能为空");
        Assert.notNull(p, "password 不能为空");
        Assert.notNull(identityType, "认证类型不能为空");
        return ResponseEntity.ok(userService.login(u, p, identityType));
    }


    // 测试登陆1
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Map user) {
        //        return ResponseEntity.ok(userService.login(user.getIdentifier(), user.getCredential()));
        return ResponseEntity.ok(true);
    }


}

