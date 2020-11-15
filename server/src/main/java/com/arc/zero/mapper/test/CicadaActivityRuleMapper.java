package com.arc.zero.mapper.test;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yechao
 * @date 2020/11/11 6:10 下午
 */
@Mapper
public interface CicadaActivityRuleMapper {

    /**
     * 保存一条数据
     *
     * @param activityRuleDO 数据模型
     * @return 主键
     */
    Long insert(CicadaActivityRuleDO activityRuleDO);

    /**
     * 更新一条数据
     *
     * @param activityRuleDO 数据模型
     * @return 影响条数
     */
    Long update(CicadaActivityRuleDO activityRuleDO);

    /**
     * 删除一条数据
     *
     * @param id 主键
     * @return 影响条数
     */
    Long deleteById(@Param("id") Long id);

    /**
     * 分页条件查询数据总条数
     *
     * @param request 数据模型
     * @return 筛选出数据总条数
     */
    Long selectPageCount(@Param("request") CicadaActivityRuleRequest request);

    /**
     * select list
     *
     * @param request ActivityRoleRequest
     * @return 数据集合
     */
    List<CicadaActivityRuleDO> selectPageList(@Param("request") CicadaActivityRuleRequest request);

    /**
     * 通过主键查询一条数据
     *
     * @param request 数据模型
     * @return 一条数据
     */
    CicadaActivityRuleDO selectOne(CicadaActivityRuleRequest request);


}
