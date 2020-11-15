package com.arc.zero.controller.data.shop;

import com.arc.core.model.domain.shop.ShoppingCommodity;
import com.arc.core.model.request.shop.ShoppingCommodityRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.shop.ShoppingCommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 购物小票接口
 *
 * @author lamy
 * @since 2019-12-30 22:06:53
 */
@Slf4j
//@RequestMapping("/shopping/commodity")
//@RestController
public class ShoppingCommodityController {

    @Autowired
    private ShoppingCommodityService shoppingCommodityService;

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param shoppingCommodity 实体
     * @return 主键 PK
     */
    public ResponseVo save(ShoppingCommodity shoppingCommodity) {
        return ResponseVo.success(shoppingCommodityService.save(shoppingCommodity));
    }

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    public ResponseVo delete(Long id) {
        return ResponseVo.success(shoppingCommodityService.delete(id));
    }

    /**
     * 更新一条数据
     *
     * @param shoppingCommodity
     * @return 影响数据条数
     */
    public ResponseVo update(ShoppingCommodity shoppingCommodity) {
        return ResponseVo.success(shoppingCommodityService.update(shoppingCommodity));
    }

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    public ResponseVo<ShoppingCommodity> get(Long id) {
        return ResponseVo.success(shoppingCommodityService.get(id));
    }

    /**
     * 无条件查询全部数据
     *
     * @return 全部数据
     */
    public ResponseVo<List<ShoppingCommodity>> list() {
        return ResponseVo.success(shoppingCommodityService.list());
    }

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    public ResponseVo<List<ShoppingCommodity>> list(ShoppingCommodityRequest request) {
        return ResponseVo.success(shoppingCommodityService.list(request));
    }

    /**
     * 分页条件查询数据列表
     *
     * @param request
     * @return 数据集合
     */
    public ResponseVo<Page<ShoppingCommodity>> listPage(ShoppingCommodityRequest request) {
        return ResponseVo.success(shoppingCommodityService.listPage(request));
    }

    /**
     * 批量插入
     *
     * @param shoppingCommoditys 数据集合
     * @return 影响条数
     */
    public ResponseVo saveBatch(List<ShoppingCommodity> shoppingCommoditys) {
        return ResponseVo.success(shoppingCommodityService.saveBatch(shoppingCommoditys));
    }

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return 影响条数
     */
    public ResponseVo deleteIdIn(List<Long> ids) {
        return ResponseVo.success(shoppingCommodityService.deleteIdIn(ids));
    }

    /**
     * 批量更新
     *
     * @param shoppingCommoditys 数据集合
     * @return 影响条数
     */
    public ResponseVo updateBatch(List<ShoppingCommodity> shoppingCommoditys) {
        return ResponseVo.success(shoppingCommodityService.updateBatch(shoppingCommoditys));
    }

}
