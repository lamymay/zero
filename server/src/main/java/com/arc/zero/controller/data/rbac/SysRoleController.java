package com.arc.zero.controller.data.rbac;

import com.arc.core.model.domain.system.SysRole;
import com.arc.core.model.request.system.role.RoleResourceRequest;
import com.arc.core.model.request.system.role.SysRoleRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysRoleService;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统角色相关接口
 * 接口提供的功能：
 * 单表：查询、新增、批量新增、删除、批量删除、更新、批量更新、分页查询
 *
 * @author 叶超
 * @since 2019/4/27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/roles")
public class SysRoleController {

    @Resource
    private SysRoleService roleService;

    /**
     * 新建角色
     * 注意
     * 1请求类型为Content-Type:application/json
     * 2方法： POST
     *
     * @param role
     * @return
     */
    @PostMapping(value = {"","/save"})
    public ResponseVo save(@Validated @RequestBody SysRole role) {
        log.debug("新建角色，参数 sysLog={}, ", role.toString());
        return ResponseVo.success(roleService.save(role));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Map> delete(@PathVariable Long id) {
        log.debug("参数删除角色，参数id={}", id);
        return ok(roleService.delete(id));
    }

    /**
     * 构造返回数据
     *
     * @param response
     * @return
     */
    private static ResponseEntity<Map> ok(Object response) {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("data", response);
        return ResponseEntity.ok(map);
    }

    /**
     * 更新角色
     * 注意 1请求类型为Content-Type:application/json
     * 方法： PUT
     * 对于必要参数没有传则判断了一下会返回错误代码
     * http://ip:port/url
     *
     * @param role
     * @return ResponseVo
     */
    @PutMapping(value = "/{id}")
    public ResponseVo update(@PathVariable Long id, @RequestBody SysRole role) {
        log.debug("role是null吗{}", role == null);
        if (role.getId() == null || !role.getId().equals(id)) {
            role.setId(id);
        }
        log.debug("更新角色,参数 roles={}, ", role.toString());
        return ResponseVo.success(roleService.update(role));
    }


    /**
     * 获取单个角色
     * ApiImplicitParam这个注解不只是注解，还会影响运行期的程序，例子如下：
     *
     * @param id 主键
     * @return ResponseVo
     */
    @GetMapping(value = "/{id}")
    //@ApiImplicitParams({@ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", dataType = "long", required = true)})
    public ResponseVo get(@PathVariable("id") Long id) {
        log.debug("获取单个角色,参数 id={}", id);
        return ResponseVo.success(roleService.get(id));
    }

    /**
     * 分页查询
     *
     * @return ResponseVo
     */
    @PostMapping(value = "/page")
//    public ResponseEntity page(@RequestBody SysRoleRequest request) {
    public ResponseEntity<Map<String, Object>> page(@RequestBody SysRoleRequest request) {
        HashMap<String, Object> map = new HashMap<>(16);
        map.put("data", roleService.listPage(request));
        return ResponseEntity.ok(map);
//        return ResponseEntity.ok(roleService.listPage(request));
    }

    @PostMapping(value = "/map")
    public Object testMap(@RequestBody Map<String, Object> map) {
        //获取map中的数据
        Long userId = (Long)map.get("userId");
        String phone = (String)map.get("phone");
        //业务代码...
        return "aaa";
    }




    //*************************** 批量操作 *************************************

    /**
     * 批量操作--save
     *
     * @param roles
     * @return
     */
    @PostMapping("/save/batch")
    public ResponseVo batchSave(@RequestBody List<SysRole> roles) {
        log.debug("batchSave 角色,参数 roles={}, ", roles.size());
        return ResponseVo.success(roleService.saveBatch(roles));
    }

    /**
     * 批量操作--更新
     *
     * @param roles
     * @return
     */
    @PutMapping("/update/batch")
    public ResponseVo batchUpdateWithResources(@RequestBody List<SysRole> roles) {
        log.debug("更新角色,参数 roles={}, ", roles.size());
        return ResponseVo.success(roleService.updateBatch(roles));
    }
    //操作子资源的接口

    /**
     * 新增角色的同时添加资源
     *
     * @param id
     * @param roleResourceRequest
     * @return
     */
    @PutMapping(value = "/{id}/resources")
    public ResponseVo<Integer> saveRoleWithResources(@PathVariable("id") Long id, @RequestBody RoleResourceRequest roleResourceRequest) {
        roleResourceRequest.setRoleId(id);
        log.debug("新增角色的同时添加资源,参数 roleResourceRequest ={}", roleResourceRequest);
        return ResponseVo.success(roleService.updateRoleResource(roleResourceRequest));
    }

    /**
     * 向角色新增资源
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/{id}/resources")
    public ResponseVo<Integer> addResourcesToRole(@PathVariable("id") Long id, @RequestBody RoleResourceRequest roleResourceRequest) {
        roleResourceRequest.setRoleId(id);
        log.debug("向角色新增资源,参数 ={}", roleResourceRequest);
        return ResponseVo.success(roleService.saveRoleResource(roleResourceRequest));
    }

}

