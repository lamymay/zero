package com.arc.zero.service.system;

import com.arc.core.model.domain.system.SysUser;
import com.arc.core.model.domain.system.SysUserAuth;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是服务层，目的是处理业务，
 *
 * @author yechao
 * @date 2018/12/21
 */
public interface SysUserService {

    Long save(SysUser user);

    int delete(Long id);

    int update(SysUser user);

    int updateBatch(List<SysUser> users);

    SysUser get(Long id);

    List<SysUser> list();


    /**
     * 登录简化接口
     * 采用username/password 模式
     *
     * @param username username
     * @param password password
     * @return SysUser
     */
    SysUser login(String username, String password);

    /**
     * 登录标准接口
     *
     * @param identifier   标识（账号）
     * @param credential   凭证
     * @param identityType 认证类型
     * @return SysUser
     */
    SysUser login(String identifier, String credential, Integer identityType);

    /**
     * 登录
     *
     * @param ua SysUserAuth
     * @return SysUser
     */
    SysUser login(SysUserAuth ua);
}
