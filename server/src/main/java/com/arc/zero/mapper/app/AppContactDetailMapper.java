package com.arc.zero.mapper.app;

import com.arc.core.model.domain.app.AppContactDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

/**
 * APP联系人表服务
 *
 * @author lamy
 * @since
 */
public interface AppContactDetailMapper extends BaseMapper<AppContactDetail> {

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param contactDetail 实体
     * @return 主键 PK
     */
    Long save(AppContactDetail contactDetail);

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    int delete(Long id);

    int deleteByContactId(Long contactId);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return 影响条数
     */
    int deleteBatch(@NonNull @Param("ids") Set<Long> ids);

    /**
     * 通过联系人ids批量删除
     *
     * @param contactIds contactIds主键集合
     * @return 影响条数
     */
    int deleteBatchByContactIds(@NonNull @Param("contactIds") Set<Long> contactIds);

    /**
     * 更新一条数据
     *
     * @param contactDetail
     * @return 影响数据条数
     */
    int update(AppContactDetail contactDetail);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    AppContactDetail get(Long id);


    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    List<AppContactDetail> list(AppContactDetail contact);

    Set<AppContactDetail> listByContactId(@Param("contactId") Long contactId);

    /**
     * 批量插入
     *
     * @param contactDetails 数据集合
     * @return 影响条数
     */
    int saveBatch(@Param("contactDetails") Set<AppContactDetail> contactDetails);

}
