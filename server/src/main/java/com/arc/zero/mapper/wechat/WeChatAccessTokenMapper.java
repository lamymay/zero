package com.arc.zero.mapper.wechat;

import com.arc.zero.model.domain.wechat.WeChatAccessToken;

import java.util.List;

/**
* 系统用户表服务
*
* @author lamy
* @since 2020-01-12 11:40:45
*/
public interface WeChatAccessTokenMapper {

    /**
    * 保存一条数据并返回数据的主键
    *
    * @param weChatAccessToken 实体
    * @return 主键 PK
    */
    Long save(WeChatAccessToken weChatAccessToken);

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
    * @param weChatAccessToken
    * @return 影响数据条数
    */
    int update(WeChatAccessToken weChatAccessToken);

    /**
    * 通过主键查询一条数据
    *
    * @param id 主键
    * @return 返回一条数据
    */
    WeChatAccessToken get(Long id);

    /**
    * 无条件查询全部数据
    *
    * @return 全部数据
    */
    List<WeChatAccessToken> list();

}
