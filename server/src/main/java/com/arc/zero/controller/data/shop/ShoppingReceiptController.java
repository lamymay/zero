package com.arc.zero.controller.data.shop;

import com.arc.core.model.domain.shop.ShoppingReceipt;
import com.arc.core.model.request.shop.ShoppingReceiptRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.shop.ShoppingReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物小票接口
 *
 * @author lamy
 * @since 2019-12-30 22:24:13
 */
@Slf4j
@Validated
@RequestMapping("/shopping/receipt")
@RestController
public class ShoppingReceiptController {

    @Autowired
    private ShoppingReceiptService shoppingReceiptService;

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param request 实体
     * @return 主键 PK
     */
    @PostMapping("/save")
    public ResponseVo save(@Validated @RequestBody ShoppingReceipt   request) {
        log.debug("{}参数={}",this.getClass(), request);
        //价格数据的处理
//        return ResponseVo.success(shoppingReceiptService.save(BeanUtil.copyBean(request, ShoppingReceipt.class)));
        return ResponseVo.success(shoppingReceiptService.save(request));
    }

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    @GetMapping("/delete/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        return ResponseVo.success(shoppingReceiptService.delete(id));
    }

    /**
     * 更新一条数据
     *
     * @param shoppingReceipt
     * @return 影响数据条数
     */
    public ResponseVo update(ShoppingReceipt shoppingReceipt) {
        return ResponseVo.success(shoppingReceiptService.update(shoppingReceipt));
    }

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    @GetMapping("/{id}")
    public ResponseVo<ShoppingReceipt> get(@PathVariable Long id) {
        log.debug("通过主键查询一条数据，参数={}", id);
        return ResponseVo.success(shoppingReceiptService.get(id));
    }

    @GetMapping("/test")
    public ResponseVo test() {
        return ResponseVo.success("test");
    }

    /**
     * 无条件查询全部数据
     *
     * @return 全部数据
     */
    public ResponseVo<List<ShoppingReceipt>> list() {
        return ResponseVo.success(shoppingReceiptService.list());
    }

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    public ResponseVo<List<ShoppingReceipt>> list(ShoppingReceiptRequest request) {
        return ResponseVo.success(shoppingReceiptService.list(request));
    }

    /**
     * 分页条件查询数据列表
     *
     * @param request
     * @return 数据集合
     */
    @PostMapping("/page")
    public ResponseVo<Page<ShoppingReceipt>> listPage(ShoppingReceiptRequest request) {
        return ResponseVo.success(shoppingReceiptService.listPage(request));
    }

    /**
     * 批量插入
     *
     * @param shoppingReceipts 数据集合
     * @return 影响条数
     */
    public ResponseVo<Integer> saveBatch(List<ShoppingReceipt> shoppingReceipts) {
        return ResponseVo.success(shoppingReceiptService.saveBatch(shoppingReceipts));
    }

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return 影响条数
     */
    public ResponseVo<Integer> deleteIdIn(List<Long> ids) {
        return ResponseVo.success(shoppingReceiptService.deleteIdIn(ids));
    }

    /**
     * 批量更新
     *
     * @param shoppingReceipts 数据集合
     * @return 影响条数
     */
    public ResponseVo<Integer> updateBatch(List<ShoppingReceipt> shoppingReceipts) {
        return ResponseVo.success(shoppingReceiptService.updateBatch(shoppingReceipts));
    }

}
