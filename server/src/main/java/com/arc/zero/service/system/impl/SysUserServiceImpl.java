package com.arc.zero.service.system.impl;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.system.SysUser;
import com.arc.core.model.domain.system.SysUserAuth;
import com.arc.zero.mapper.system.SysUserAuthMapper;
import com.arc.zero.mapper.system.SysUserMapper;
import com.arc.zero.service.system.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 叶超
 * @since 2018/12/26 11:28
 */
@Slf4j
@Transactional
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    /**
     * 登陆校验真正在该该表
     */
    @Resource
    private SysUserAuthMapper authMapper;


    @Override
    public Long save(SysUser user) {
        return userMapper.save(user) == 1 ? user.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return userMapper.delete(id);
    }

    @Override
    public int update(SysUser user) {
        return userMapper.update(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    @Override
    public int updateBatch(List<SysUser> users) {
        return 0;
    }

    @Override
    public SysUser get(Long id) {
        return userMapper.get(id);
    }

    @Override
    public List<SysUser> list() {
        return userMapper.list();
    }

    //================================= 其他查询的业务方法

    /**
     * 登陆
     *
     * @param ua SysUserAuth
     * @return SysUser
     */
    @Override
    public SysUser login(SysUserAuth ua) {
        if (ua == null || ua.getIdentifier() == null || ua.getIdentifier().trim().length() == 0 || ua.getCredential() == null) {
            throw new BizException(ProjectCodeEnum.LOGIN_ERROR);
        }
        //todo 改进登录的查询效率， 需要测试 一次联表查询 两次单表查询，谁的消耗更大


        String identifier = ua.getIdentifier();
        String credential = ua.getCredential();
        int identityType = ua.getIdentityType();
        SysUserAuth auth = authMapper.getByIdentifierWithCredentialAndIdentityType(identifier, credential,identityType );
//        SysUserAuth auth = authMapper.getByIdentifierWithCredentialAndIdentityType("admin", "admin", 1);
        if (null == auth) {
            throw new BizException(ProjectCodeEnum.LOGIN_ERROR);
        }
        return userMapper.get(auth.getUserId());
    }

    @Override
    public SysUser login(String username, String password) {
        return login(new SysUserAuth(username, password, 1));
    }

    @Override
    public SysUser login(String identifier, String credential, Integer identityType) {
        return login(new SysUserAuth(identifier, credential, identityType));
    }

}
