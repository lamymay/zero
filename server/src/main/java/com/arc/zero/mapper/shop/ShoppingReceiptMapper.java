package com.arc.zero.mapper.shop;

import com.arc.core.model.domain.shop.ShoppingReceipt;
import com.arc.core.model.request.shop.ShoppingReceiptRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* 购物小票服务
*
* @author lamy
* @since 2019-12-30 22:24:13
*/
public interface ShoppingReceiptMapper  extends BaseMapper<ShoppingReceipt> {

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param shoppingReceipt 实体
    * @return 主键 PK
    */
    Long save(ShoppingReceipt shoppingReceipt);

//    /**
//    * 通过主键删除一条数据
//    *
//    * @param id 主键
//    * @return 影响数据条数
//    */
//    int delete(Long id);

    /**
    * 更新一条数据
    *
    * @param shoppingReceipt
    * @return 影响数据条数
    */
    int update(ShoppingReceipt shoppingReceipt);

    /**
    * 通过主键查询一条数据
    *
    * @param id 主键
    * @return 返回一条数据
    */
    ShoppingReceipt get(Long id);

    /**
    * 无条件查询全部数据
    *
    * @return 全部数据
    */
    List<ShoppingReceipt> list();

    /**
    * 条件查询数据列表
    *
    * @return 数据集合
    */
    List<ShoppingReceipt> list(ShoppingReceiptRequest shoppingReceiptRequest);

    /**
    * 分页条件查询数据列表
    *
    * @param shoppingReceiptRequest
    * @return 数据集合
    */
    List<ShoppingReceipt> listPage(ShoppingReceiptRequest shoppingReceiptRequest);

    /**
    * 批量插入
    *
    * @param shoppingReceipts 数据集合
    * @return 影响条数
    */
    Integer saveBatch(List<ShoppingReceipt> shoppingReceipts);

    /**
    * 批量删除
    *
    * @param ids 主键集合
    * @return 影响条数
    */

    Integer deleteIdIn(List<Long> ids);

    /**
    * 批量更新
    *
    * @param shoppingReceipts 数据集合
    * @return 影响条数
    */
    Integer updateBatch(List<ShoppingReceipt> shoppingReceipts);

    int count();


}
