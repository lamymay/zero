package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.RoleResource;
import com.arc.core.model.domain.system.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author yechao
 * @since 2019/1/24 9:57
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 新建一条数据
     *
     * @param role 角色
     * @return 影响的数据条数
     */
    int save(SysRole role);

    /**
     * @param id 角色id
     * @return 影响的数据条数
     */
    int delete(Long id);

    /**
     * 更新一条数据
     *
     * @param role 角色
     * @return 影响的数据条数
     */
    int update(SysRole role);

    /**
     * 获取一条数据
     *
     * @param id 角色id
     * @return 一条角色
     */
    SysRole get(Long id);

    /**
     * 查询所有的角色
     *
     * @return 角色列表
     */
    List<SysRole> list();

    /**
     * 批量删除
     *
     * @param roles 角色ids
     * @return 影响的数据条数
     */
    int batchSave(@Param("roles") List<SysRole> roles);

    /**
     * 删除全部的角色与资源的关系
     *
     * @param id 角色id
     * @return 影响的数据条数
     */
    int deleteAllRoleResource(Long id);

    /**
     * 关系表批量保存
     *
     * @param roleResources  角色资源中间表数据集合
     * @return 影响的数据条数
     */
    int saveBatchRoleResource(@Param("roleResources") Set<RoleResource> roleResources);

}
