package com.arc.zero.mapper.shop;

import com.arc.core.model.domain.shop.Order;

import java.util.List;

/**
* 公益订单表服务
*
* @author yechao
* @since 
*/
public interface OrderMapper {

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param forestEnergyWelfareOrder 实体
    * @return 主键 PK
    */
    Long save(Order forestEnergyWelfareOrder);

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
    * @param forestEnergyWelfareOrder
    * @return 影响数据条数
    */
    int update(Order forestEnergyWelfareOrder);

    /**
    * 通过主键查询一条数据
    *
    * @param id 主键
    * @return 返回一条数据
    */
    Order get(Long id);

    /**
    * 条件查询数据列表
    *
    * @return 数据集合
    */
    List<Order> list(Order forestEnergyWelfareOrderRequest);

//    /**
//    * 分页条件查询数据列表
//    *
//    * @param forestEnergyWelfareOrderRequest
//    * @return 数据集合
//    */
//    List<ForestEnergyWelfareOrder> listPage(ForestEnergyWelfareOrder forestEnergyWelfareOrderRequest);

}
