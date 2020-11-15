package com.arc.zero.mapper.test;


import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* 激活码记录表服务
*
* @author lamy
* @since 
*/
public interface CicadaCdKeyRecordMapper {

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param zlxCdkeyRecord 实体
    * @return 主键 PK
    */
    Long insert(CicadaCdKeyRecordDO zlxCdkeyRecord);

    /**
    * 更新一条数据
    *
    * @param zlxCdkeyRecord
    * @return 影响数据条数
    */
    int update(CicadaCdKeyRecordDO zlxCdkeyRecord);

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    int deleteById(Long id);


    /**
     * 分页条件查询数据总条数
     *
     * @param request 数据模型
     * @return 筛选出数据总条数
     */
    Long selectPageCount(@Param("request") CicadaCdKeyRecordRequest request);

    /**
     * select list
     *
     * @param request ActivityRoleRequest
     * @return 数据集合
     */
    List<CicadaCdKeyRecordDO> selectPageList(@Param("request") CicadaCdKeyRecordRequest request);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    CicadaCdKeyRecordDO selectOne(Long id);


}
