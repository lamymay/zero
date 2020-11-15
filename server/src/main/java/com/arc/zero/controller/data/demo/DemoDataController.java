package com.arc.zero.controller.data.demo;

import com.arc.core.model.domain.demo.DemoDomain;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.mapper.demo.DemoMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yechao
 * @date 2020/9/8 10:27 上午
 */
@RequestMapping("/test/enum")
@RestController
public class DemoDataController {

    /**
     * 测试用mapper
     */
    @Resource
    private DemoMapper demoMapper;

    @PostMapping(value = "")
    public ResponseVo save(@RequestBody DemoDomain model) {
        return ResponseVo.success(demoMapper.insert(model));
    }

    @GetMapping(value = "{id}")
    public ResponseVo get(@PathVariable Long id) {
        return ResponseVo.success(demoMapper.get(id));
    }


    @PostMapping(value = "/query")
    public ResponseVo list() {
        return ResponseVo.success(demoMapper.list());
    }

    public static void main(String[] args) {
        int hashCode = "9".hashCode();
        int hash = Math.abs(9 % 10);
        System.out.println(hashCode);
        System.out.println(hash);

    }
}
