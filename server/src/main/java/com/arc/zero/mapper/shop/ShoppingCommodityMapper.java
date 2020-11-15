package com.arc.zero.mapper.shop;

import com.arc.core.model.domain.shop.ShoppingCommodity;
import com.arc.core.model.request.shop.ShoppingCommodityRequest;

import java.util.List;

/**
* 购物小票服务
*
* @author lamy
* @since 2019-12-30 22:06:53
*/
public interface ShoppingCommodityMapper {

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param shoppingCommodity 实体
    * @return 主键 PK
    */
    Long save(ShoppingCommodity shoppingCommodity);

    /**
    * 通过主键删除一条数据
    *
    * @param id 主键
    * @return 影响数据条数
    */
    int delete(Long id);

    /**
    * 更新一条数据
    *
    * @param shoppingCommodity
    * @return 影响数据条数
    */
    int update(ShoppingCommodity shoppingCommodity);

    /**
    * 通过主键查询一条数据
    *
    * @param id 主键
    * @return 返回一条数据
    */
    ShoppingCommodity get(Long id);

    /**
    * 无条件查询全部数据
    *
    * @return 全部数据
    */
    List<ShoppingCommodity> list();

    /**
    * 条件查询数据列表
    *
    * @return 数据集合
    */
    List<ShoppingCommodity> list(ShoppingCommodityRequest shoppingCommodityRequest);

    /**
    * 分页条件查询数据列表
    *
    * @param shoppingCommodityRequest
    * @return 数据集合
    */
    List<ShoppingCommodity> listPage(ShoppingCommodityRequest shoppingCommodityRequest);

    /**
    * 批量插入
    *
    * @param shoppingCommoditys 数据集合
    * @return 影响条数
    */
    Integer saveBatch(List<ShoppingCommodity> shoppingCommoditys);

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
    * @param shoppingCommoditys 数据集合
    * @return 影响条数
    */
    Integer updateBatch(List<ShoppingCommodity> shoppingCommoditys);
}
