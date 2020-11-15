package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 *
 * @author X
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    int save(SysUser user);

    int delete(Long id);

    int update(SysUser user);

    SysUser get(Long id);

    /**
     * 根据用户id获取用户的
     *
     * @param id
     * @return
     */
    SysUser getUserWithAuth(Long id);

    List<SysUser> list();

}
