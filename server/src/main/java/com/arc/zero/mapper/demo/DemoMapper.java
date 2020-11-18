package com.arc.zero.mapper.demo;

import com.arc.core.model.domain.demo.DemoModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface DemoMapper extends BaseMapper<DemoModel> {

//    /**
//     * 保存一条数据并返回数据的主键
//     *
//     * @param shoppingReceipt 实体
//     * @return 主键 PK
//     */
//    Long save(DemoModel shoppingReceipt);
//
//    /**
//    * 通过主键删除一条数据
//    *
//    * @param id 主键
//    * @return 影响数据条数
//    */
//    int delete(Long id);
//
//    /**
//     * 更新一条数据
//     *
//     * @param shoppingReceipt
//     * @return 影响数据条数
//     */
//    int update(DemoModel shoppingReceipt);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    DemoModel get(Long id);

    /**
     * 无条件查询全部数据
     *
     * @return 全部数据
     */
    List<DemoModel> list();

//    /**
//     * 条件查询数据列表
//     *
//     * @return 数据集合
//     */
//    List<TestEnumDomain> list(TestEnumDomain request);
//
//    /**
//     * 分页条件查询数据列表
//     *
//     * @param request
//     * @return 数据集合
//     */
//    List<TestEnumDomain> listPage(TestEnumDomain request);
//
//    /**
//     * 批量插入
//     *
//     * @param shoppingReceipts 数据集合
//     * @return 影响条数
//     */
//    Integer saveBatch(List<TestEnumDomain> shoppingReceipts);
//
//    /**
//     * 批量删除
//     *
//     * @param ids 主键集合
//     * @return 影响条数
//     */
//
//    Integer deleteIdIn(List<Long> ids);
//
//    /**
//     * 批量更新
//     *
//     * @param shoppingReceipts 数据集合
//     * @return 影响条数
//     */
//    Integer updateBatch(List<TestEnumDomain> shoppingReceipts);
//
//    int count();


}
