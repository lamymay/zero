package com.arc.zero.controller.data.test.project;

import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.mapper.test.CicadaActivityRuleDO;
import com.arc.zero.mapper.test.CicadaActivityRuleMapper;
import com.arc.zero.mapper.test.CicadaActivityRuleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yechao
 * @date 2020/11/12 5:18 下午
 */

@Slf4j
@RestController
@RequestMapping("/v1/rule")
public class CicadaActivityRuleMapperTestController {

    @Resource
    private CicadaActivityRuleMapper cicadaActivityRuleMapper;

    @PostMapping(value = "/save")
    public ResponseVo save(@RequestBody CicadaActivityRuleDO request) {
        Long row = cicadaActivityRuleMapper.insert(request);
        if(row!=null&&row==1L){
            return ResponseVo.success(request.getId());
        }
        return ResponseVo.failure();
    }

    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody CicadaActivityRuleDO request) {
        return ResponseVo.success(cicadaActivityRuleMapper.update(request));
    }

    @RequestMapping(value = "/delete/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        return ResponseVo.success(cicadaActivityRuleMapper.deleteById(id));
    }

    @PostMapping(value = "/get")
    public ResponseVo get(@RequestBody CicadaActivityRuleRequest request) {
        return ResponseVo.success(cicadaActivityRuleMapper.selectOne(request));
    }

    @PostMapping(value = "/count")
    public ResponseVo count(@RequestBody CicadaActivityRuleRequest request) {
        return ResponseVo.success(cicadaActivityRuleMapper.selectPageCount(request));
    }

    @PostMapping(value = "/page")
    public ResponseVo page(@RequestBody CicadaActivityRuleRequest request) {
        return ResponseVo.success(cicadaActivityRuleMapper.selectPageList(request));
    }

}

