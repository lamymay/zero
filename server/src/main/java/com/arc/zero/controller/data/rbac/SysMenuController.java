package com.arc.zero.controller.data.rbac;

import com.alibaba.fastjson.JSON;
import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.system.SysMenu;
import com.arc.core.model.request.system.menu.SysMenuRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述：实际上就是表menu 的控制层
 *
 * @author yechao
 * @date 2018-10-18 16:37:18
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/menus")
public class SysMenuController {

    @Autowired
    @Qualifier("menuServiceImpl")
    private SysMenuService menuService;

    /**
     * 描述:创建menu
     *
     * @param request
     */
    @PostMapping(value = "")
    public ResponseVo save(@RequestBody SysMenu request) {
        return ResponseVo.success(menuService.save(request));
    }

    /**
     * 批量保存
     *
     * @param menus
     * @return
     */
    @PostMapping("/batch/save")
    public ResponseVo saveBatch(@RequestBody List<SysMenu> menus) {
        return ResponseVo.success(menuService.saveBatch(menus));
    }

    /**
     * 描述：物理/逻辑 删除menu
     *
     * @param id      必填，主键id
     * @param logical 非必填，布尔值
     * @return
     */
    @DeleteMapping("/delete")
    ResponseVo<Boolean> deletePhysically(@PathVariable Long id, @RequestParam(required = false) Boolean logical) {
        if (logical == null || logical == false) {
            return ResponseVo.success(menuService.deleteLogical(id));
        }
        return ResponseVo.success(menuService.delete(id));
    }


    /**
     * 描述：更新menu
     *
     * @param id
     * @param request
     * @return 返回操作影响行数
     */
    @PutMapping("")
    public ResponseVo<Integer> update(@PathVariable Long id, @RequestBody SysMenu request) {
        if (request == null) {
            throw new BizException(ProjectCodeEnum.NULL);
        }
        return ResponseVo.success(menuService.update(request));
    }

    /**
     * 主键查询
     *
     * @param id 主键id
     * @return 单个菜单数据（一般用于编辑回显）
     */
    @GetMapping("/{id}")
    public ResponseVo getMenuByMenuCode(@PathVariable Long id) {
        return ResponseVo.success(menuService.get(id));
    }




    /**
     * 返回带层级结构的已经启用的菜单数据
     *
     * @param systemId 系统id
     * @param level    级别
     * @return 首页菜单渲染（Available=未删除的）
     */
    //注意url中不要驼峰表示，这里是{取值}，建议随java风格驼峰，其余一律小写
    @GetMapping("/{systemId}/levels/{level}")
    public ResponseVo listAllAvailableMenuWith3Level(@PathVariable Integer systemId, @PathVariable(required = false) Integer level) {
        System.out.println("##################Menu的日志###"+ JSON.toJSONString(log));
        System.out.println("##################### 預期是debug"+log.isDebugEnabled());
        System.out.println("#####################isInfoEnabled="+log.isInfoEnabled());
        System.out.println("#####################isDebugEnabled="+log.isDebugEnabled());
        System.out.println("#####################isErrorEnabled="+log.isErrorEnabled());

        try {
            System.out.println(1 / 0);
        } catch (Exception exception) {

            log.trace("###### trace 系统systemId ={}",systemId);
            log.debug("###### debug 系统systemId ={}",systemId);
            log.info("###### info 系统systemId ={}",systemId);
            log.warn("###### warn 系统systemId ={}",systemId);
            log.error("###### error 系统systemId ={}",systemId);
        }

        return ResponseVo.success(menuService.listMenus(systemId, level));
    }

    /**
     * 分页查询 后台编辑管理中用的
     *
     * @param request
     * @return
     */
    @PostMapping("/page")
    public ResponseVo page(@RequestBody SysMenuRequest request) {
        return ResponseVo.success(menuService.listPage(request));
    }

    /**
     * 获取树形数据
     * @param systemId
     * @return
     */
    @GetMapping("/tree")
    public ResponseVo listAllAvailableMenu(@RequestParam(required = true) Integer systemId) {
        long t1 = System.currentTimeMillis();
        ResponseVo<List<SysMenu>> success = ResponseVo.success(menuService.listTreeMenuWithSystemIdAndLevel(systemId));
        long t2 = System.currentTimeMillis();
        System.out.println((t2-t1));
        return success;
    }

    @PostMapping("/list")
    public ResponseEntity list(@RequestBody SysMenu sysMenu) {
        return ResponseEntity.ok(menuService.listAllMenus());
    }
}
