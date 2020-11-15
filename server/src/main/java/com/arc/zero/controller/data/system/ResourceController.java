package com.arc.zero.controller.data.system;


import com.arc.core.model.domain.system.SysResource;
import com.arc.core.model.vo.ResponseVo;
import com.arc.core.model.vo.ResponseVo2;
import com.arc.zero.model.request.rbac.ResourceRequest;
import com.arc.zero.service.system.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 系统资源相关接口
 *
 * @author 叶超
 * @since 2019/4/27 17:02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sys/resources")
public class ResourceController {

    @Autowired
    private SysResourceService resourceService;

    /**
     * 新增一条数据
     *
     * @param resource
     * @return
     */
    @PostMapping(value = "")
    public ResponseVo save(@RequestBody SysResource resource) {
        log.debug("新建角色，参数 sysLog={}, ", resource.toString());
        return ResponseVo.success(resourceService.save(resource));
    }

    /**
     * 删除一条数据
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        log.debug("参数删除角色，参数id={}", id);
        return ResponseVo.success(resourceService.delete(id));
    }


    /**
     * 更新
     *
     * @param sysLog
     * @return
     */
    @PutMapping("")
    public ResponseVo update(@RequestBody SysResource sysLog) {
        log.debug("更新角色,参数sysLog={}, ", sysLog.toString());
        return ResponseVo.success(resourceService.update(sysLog));
    }

    /**
     * 获取一条数据
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResponseVo get(@PathVariable("id") Long id) {
        log.debug("获取单个角色,参数 id={}", id);
        SysResource sysLog = resourceService.get(id);
        ResponseVo<SysResource> success = ResponseVo.success(sysLog);
        log.debug("------------------------------------");
        log.debug("get结果={}", success);
        log.debug("------------------------------------");
        return success;
    }

    /**
     * 分页查询
     *
     * @return
     */
    @PostMapping(value = "/page")
    public ResponseEntity listPage(@RequestBody ResourceRequest resourceRequest) {
        Page<SysResource> page = resourceService.page();
        return ResponseEntity.ok(page);
    }

    /**
     * 条件查询
     *
     * @return
     */
    @PostMapping(value = "/list")
    public ResponseEntity list() {
        //return ResponseVo.success(resourceService.list());
        List<SysResource> list = resourceService.list();
        ResponseEntity<List<SysResource>> responseEntity = ResponseVo2.ok(list);
        log.debug("ResponseEntity结果={}", responseEntity);
        return responseEntity;
    }


    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/delete/batch")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        log.debug("参数删除角色，参数id={}", ids);
        if (ids == null || ids.size() == 0) {
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(resourceService.deleteIdIn(ids));
    }

    /**
     * 扫面所有controller资源
     *
     * @return
     */
    @GetMapping("/scan/controller")
    public ResponseVo scanSysResourceByControllerAnnotation() {
        return ResponseVo.success(resourceService.scanController());
    }
}
