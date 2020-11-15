package com.arc.zero.mapper.app;

import com.arc.core.model.domain.app.AppContact;
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
public interface AppContactMapper  extends BaseMapper<AppContact> {

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param contact 实体
     * @return 主键 PK
     */
    Long save(AppContact contact);

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
     * @param tAppContact
     * @return 影响数据条数
     */
    int update(AppContact tAppContact);

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    AppContact get(Long id);

    AppContact getByQuery(AppContact contact);

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    List<AppContact> list(AppContact contact);

    Set<AppContact> listByUserId(@Param("userId") Long userId);

    Set<Long> listIdsByUserId(@Param("userId")Long userId);

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return 影响条数
     */
    int deleteBatch(@NonNull @Param("ids") Set<Long> ids);

    /**
     * 通过系统用户id删除用户数据
     *
     * @param userId 用户id
     * @return 影响条数
     */
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 批量插入
     *
     * @param contacts 数据集合
     * @return 影响条数
     */
    int saveBatch(@Param("contacts") Set<AppContact> contacts);

    int saveBatchWithUserId(@Param("userId")Long userId,@Param("contacts") Set<AppContact> contacts);



//    int updateBatch(@Param("contacts") Set<AppContact> updateContacts  );

    //void truncate(@Param("tableName") String tableName);
}
