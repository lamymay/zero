package com.arc.zero.service.wechat.impl;

import com.arc.zero.mapper.wechat.WeChatAccessTokenMapper;
import com.arc.zero.model.domain.wechat.WeChatAccessToken;
import com.arc.zero.service.wechat.WeChatAccessTokenService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
* 系统用户表服务
*
* @author lamy
* @since 2020-01-12 11:40:45
*/
@Slf4j
public class WeChatAccessTokenServiceImpl implements WeChatAccessTokenService {

    @Resource
    private WeChatAccessTokenMapper weChatAccessTokenMapper;

    @Override
    public Long save(WeChatAccessToken weChatAccessToken) {
        return weChatAccessTokenMapper.save(weChatAccessToken) == 1 ? weChatAccessToken.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return weChatAccessTokenMapper.delete(id);
    }

    @Override
    public int update(WeChatAccessToken weChatAccessToken) {
        return weChatAccessTokenMapper.update(weChatAccessToken);
    }

    @Override
    public WeChatAccessToken get(Long id) {
        return weChatAccessTokenMapper.get(id);
    }

    @Override
    public List<WeChatAccessToken> list() {
        return weChatAccessTokenMapper.list();
    }

}
