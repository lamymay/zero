package com.arc.zero.controller.data.poi;

import com.arc.core.model.domain.mall.Mall;
import com.arc.core.model.domain.mall.MallTask;
import com.arc.zero.mapper.mall.MallTaskMapper;
import com.arc.zero.service.mall.MallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * mall相关的的接口by RESTful
 *
 * @author May/lamymay/yehcao
 * @date 2018/12/25
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/mall")
public class MallController {

    @Resource
    private MallService mallService;

    @Resource
    private MallTaskMapper mallTaskMapper;


    /**
     * 新建mall
     * 注意
     * 1请求类型为Content-Type:application/json
     * 2方法： POST
     *
     * @param mall
     * @return
     */
    @PostMapping(value = "/save")
    public Long create(@RequestBody Mall mall) {
        return mallService.save(mall);
    }


    /**
     * 删除
     * 表示删除id为1的数据
     * 方法： DELETE
     * http://lip:port/mall/1
     *
     * @return String
     */
    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable Long id) {
        log.debug("参数删除mall id={}", id);
        int i = mallService.delete(id);
        if (i > 0) {
            return "编号  " + id + " 的 mall删除成功！";
        } else {
            return "编号  " + id + " 的 mall删除失败！";
        }
    }


    /**
     * 更新mall
     * 注意 1请求类型为Content-Type:application/json
     * 方法： PUT
     * 对于必要参数没有传则判断了一下会返回错误代码
     * http://ip:port/mall/
     *
     * @return
     */
    @PostMapping("/update")
    public Integer update(@RequestBody Mall mall) {
        return mallService.update(mall);
    }

    /**
     * 获取单个mal
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Mall mall(@PathVariable Long id) {
        return mallService.get(id);
    }

    /**
     * 获取mall列表
     *
     * @return
     */
    @GetMapping(value = "")
    public List<Mall> list() {
        return mallService.list();
    }


    @GetMapping(value = "/task")
    public List<MallTask> mallTaskMapperList() {
        return mallTaskMapper.list();
    }



}

