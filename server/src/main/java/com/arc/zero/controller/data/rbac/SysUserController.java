package com.arc.zero.controller.data.rbac;

import com.alibaba.fastjson.JSON;
import com.arc.core.model.domain.system.SysUser;
import com.arc.core.model.request.system.user.SysUserRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.utils.bean.BeanUtil;
import com.arc.zero.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * data 包下的 controller仅仅用作  返回json数据 ，禁止页面跳转使用，页面跳转使用的操作请移步web包
 * 用户相关的的接口
 * 后台管理端功能:
 * 1 查询支持多条件&分页
 * 2 新增/批量新增数据
 * 3 删除/批量删除数据
 * 4 更新/批量更新
 * 5
 *
 * @author 叶超
 * @since 2018/12/25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/users")
public class SysUserController {

    @Resource
    private SysUserService userService;


    //增删改查

    /**
     * 新建用户
     * 注意
     * 1请求类型为Content-Type:application/json
     * 2方法： POST
     *
     * @param request request
     * @return ResponseVo
     */
    @PostMapping({"/save", ""})
    public ResponseVo save(@Validated @RequestBody SysUserRequest request) {
        log.debug("新建用户，参数 user={}, ", request);
        return ResponseVo.success(userService.save(BeanUtil.copyBean(request, SysUser.class)));
    }


    /**
     * 删除
     * 表示删除id为1的数据
     * 方法： DELETE
     * http://lip:port/user/1
     *
     * @param id Long id
     * @return ResponseVo
     */
    @DeleteMapping(value = "/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        log.debug("参数删除用户，参数id={}", id);
        return ResponseVo.success(userService.delete(id));

    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("followAlipayLifeAccountTaskTrueType", "FOLLOW_CAI_NIAO_GUO_GUO");
        System.out.println(JSON.toJSONString(map));
        String msg = "{\"followAlipayLifeAccountTaskTrueType\":\"FOLLOW_CAI_NIAO_GUO_GUO\"}";
    }
}
