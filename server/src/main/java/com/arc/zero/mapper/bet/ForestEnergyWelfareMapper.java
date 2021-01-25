package com.arc.zero.mapper.bet;

import com.arc.core.model.domain.bet.Award;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 能量森林公益表(定义捐赠什么)服务
 *
 * @author lamy
 * @since
 */
public interface ForestEnergyWelfareMapper {

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param forestEnergyWelfare 实体
     * @return 主键 PK
     */
    Long save(Award forestEnergyWelfare);

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    int delete(@Param("id") Long id);

    /**
     * 更新一条数据
     *
     * @param forestEnergyWelfare
     * @return 影响数据条数
     */
    int update(Award forestEnergyWelfare);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    Award get(@Param("id") Long id);

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    List<Award> list(Award forestEnergyWelfareRequest);

//    /**
//     * 分页条件查询数据列表
//     *
//     * @param forestEnergyWelfareRequest
//     * @return 数据集合
//     */
//    List<ForestEnergyWelfare> listPage(ForestEnergyWelfare forestEnergyWelfareRequest);

//    /**
//     * 批量删除
//     *
//     * @param ids 主键集合
//     * @return 影响条数
//     */
//
//    Integer deleteIdIn(List<Long> ids);

}
