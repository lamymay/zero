package com.arc.zero.service.wechat;

import com.arc.zero.model.domain.wechat.WeChatAccessToken;

/**
 * AccessToken相关服务的接口
 */
public interface AccessTokenService {

    /**
     * 创建一条数据
     *
     * @param weChatAccessToken
     * @return
     */
    Long save(WeChatAccessToken weChatAccessToken);

    /**
     * 更新一条数据
     *
     * @param weChatAccessToken
     * @return
     */
    Integer update(WeChatAccessToken weChatAccessToken);

    /**
     * select access_token from  qy_wechat_access_token where  corp_id=corp_id AND corp_secret=corp_secret
     *
     * @param corpId     每一个企业有唯一的一个企业id
     * @param corpSecret 每一个应用有唯一的一个应用的id以及应用的密钥
     * @return
     */
    WeChatAccessToken getByCorpIdAndCorpSecret(String corpId, String corpSecret);

    /**
     * 获取并校验
     * @param corpId
     * @param corpSecret
     * @return
     */
    WeChatAccessToken getAndVerifyByCorpIdAndCorpSecret(String corpId, String corpSecret);

    /**
     * 获取token
     *
     * @param corpId
     * @param corpSecret
     * @return
     */
    String getAccessTokenByCorpIdAndCorpSecret(String corpId, String corpSecret);

    WeChatAccessToken get(Long id);
}
