package com.arc.zero.service.system;


import com.arc.core.model.domain.system.SysUserAuth;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface SysUserAuthService {

    int save(SysUserAuth auth);

    int delete(Long id);

    int update(SysUserAuth auth);

    SysUserAuth get(Long id);

    /**
     * 根据类型&身份来查找用户
     *
     * @param identifier
     * @param identifierType
     * @return
     */
    SysUserAuth getByIdentifierAndIdentifierType(String identifier, int identifierType);
}
