package com.arc.zero.controller.data.bet;

import com.arc.core.model.domain.bet.Award;
import com.arc.zero.mapper.bet.ForestEnergyWelfareMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 投注相关测试接口[后台管理相关接口]
 *
 * @author yechao
 * @date 2021/01/03 21:39 下午
 */
@RestController
@RequestMapping("/v1/bet/back")
public class BetController {


    // 1 奖品表
    // 2 夺宝记录表 奖品id-用户Id-兑换码id
    // 3 订单表 奖品id-用户id-地址等

    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(@RequestBody Award award) {
        //文件扫描后入库 返回成功的数量
        HashMap<String, Object> map = new HashMap<>();
        map.put("award", award);
        return ResponseEntity.ok(map);
    }

    /**
     * 能量森林相关mapper
     */
    @Resource
    private ForestEnergyWelfareMapper modelMapper;

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param model 实体
     * @return 主键 PK
     */
    @PostMapping("/save")
    public Long save(@RequestBody Award model) {
        return modelMapper.save(model) == 1 ? model.getId() : null;
    }

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.DELETE})
    public int delete(Long id) {
        return modelMapper.delete(id);
    }

    /**
     * 更新一条数据
     *
     * @param model
     * @return 影响数据条数
     */
    @PostMapping("/update")
    public int update(@RequestBody Award model) {
        return modelMapper.update(model);
    }

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    @GetMapping("/get/{id}")
    public Award get(@PathVariable("id") Long id) {
        return modelMapper.get(id);
    }

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    @PostMapping("/list")
    public List<Award> list(@RequestBody Award request) {
        return modelMapper.list(request);
    }

    /**
     * 分页条件查询数据列表
     *
     * @param request request
     * @return 数据集合
     */
    @PostMapping("/page")
    public List<Award> listPage(@RequestBody Award request) {
        return modelMapper.list(request);
    }
}


