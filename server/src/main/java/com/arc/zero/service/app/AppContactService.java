package com.arc.zero.service.app;

import com.arc.core.model.domain.app.AppContact;
import com.arc.core.model.domain.system.SysUserAuth;

import java.util.List;
import java.util.Set;

/**
 * APP联系人表服务
 *
 * @author lamy
 * @since
 */
public interface AppContactService {

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param contact 实体
     * @return 主键 PK
     */
    Long save(AppContact contact);

    /**
     * 批量插入
     *
     * @param contactSet 数据集合
     * @return 影响条数
     */
    int saveBatch(Set<AppContact> contactSet);

    Set<AppContact> saveBatchChildren(SysUserAuth contactId, Set<AppContact> contactSet);


    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    int delete(Long id);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return 影响条数
     */
    int deleteBatch(Set<Long> ids);

    @Deprecated
    int updateBatch(Set<AppContact> contacts);

    int deleteByUserId(Long userId);

    /**
     * 更新一条数据
     *
     * @param contact
     * @return 影响数据条数
     */
    int update(AppContact contact);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    AppContact get(Long id);

    AppContact get(AppContact contact);

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    List<AppContact> list(AppContact contact);

    Set<AppContact> listByUserId(Long userId);


}
