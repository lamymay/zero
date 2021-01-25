package com.arc.zero.controller.data.shop;

import com.arc.core.model.domain.shop.Order;
import com.arc.zero.mapper.shop.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 订单相关接口
 *
 * @author may
 * @date 2020/01/13
 */
@Slf4j
@RestController
@RequestMapping("/v1/energy/order")
public class OrderController {


    /**
     * 订单相关mapper
     */
    @Resource
    private OrderMapper orderMapper;

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param model 实体
     * @return 主键 PK
     */
    @PostMapping("/save")
    public Long save(@RequestBody Order model) {
        return orderMapper.save(model) == 1 ? model.getId() : null;
    }

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.DELETE})
    public int delete(Long id) {
        return orderMapper.delete(id);
    }

    /**
     * 更新一条数据
     *
     * @param model
     * @return 影响数据条数
     */
    @PostMapping("/update")
    public int update(@RequestBody Order model) {
        return orderMapper.update(model);
    }

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    @GetMapping("/get/{id}")
    public Order get(@PathVariable("id") Long id) {
        return orderMapper.get(id);
    }

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    @PostMapping("/list")
    public List<Order> list(@RequestBody Order request) {
        return orderMapper.list(request);
    }

    /**
     * 分页条件查询数据列表
     *
     * @param request request
     * @return 数据集合
     */
    @PostMapping("/page")
    public List<Order> listPage(@RequestBody Order request) {
        return orderMapper.list(request);
    }

}
