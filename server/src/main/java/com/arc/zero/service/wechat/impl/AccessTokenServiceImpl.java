package com.arc.zero.service.wechat.impl;


import com.arc.utils.Assert;
import com.arc.zero.mapper.wechat.WeChatAccessTokenMapper;
import com.arc.zero.model.domain.wechat.WeChatAccessToken;
import com.arc.zero.service.wechat.AccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author 叶超
 * @since 2019/10/8 14:05
 */
@Slf4j
@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    private static final int AVAILABLE = 1;

    @Resource
    private WeChatAccessTokenMapper weChatAccessTokenMapper;

    @Override
    public Long save(WeChatAccessToken weChatAccessToken) {
        Assert.notNull(weChatAccessToken);
        return weChatAccessTokenMapper.save(weChatAccessToken);
    }

    @Override
    public Integer update(WeChatAccessToken weChatAccessToken) {
        Assert.notNull(weChatAccessToken);
        Assert.notNull(weChatAccessToken.getId());
        return weChatAccessTokenMapper.update(weChatAccessToken);
    }

    @Override
    public WeChatAccessToken getByCorpIdAndCorpSecret(String corpId, String corpSecret) {
//        return weChatAccessTokenMapper.getByCorpIdAndCorpSecretAndStatus(corpId, corpSecret, AVAILABLE);
        return null;
    }

    //带校验的获取
    @Override
    public WeChatAccessToken getAndVerifyByCorpIdAndCorpSecret(String corpId, String corpSecret) {

        return null;

        //        WeChatAccessToken weChatAccessTokenInDb = weChatAccessTokenMapper.findByCorpIdAndCorpSecretAndStatus(corpId, corpSecret, AVAILABLE);
//        if (weChatAccessTokenInDb == null) {
//            throw new BizException(ProjectCodeEnum.ACCESS_TOKEN_ERROR_NULL);
//        }
//        Long expiresIn = weChatAccessTokenInDb.getExpiresIn();
//        long now = System.currentTimeMillis();
//        log.debug("expiresIn ={},now={}", expiresIn, now);
//        long flag = expiresIn - now;
//        log.debug("结果={}", flag);
//        if (flag < 0) {
//            log.debug("过期");
//            throw new BizException(ProjectCodeEnum.ACCESS_TOKEN_ERROR_EXPIRED);
//        } else if (flag < EXPIRED_MILLISECOND) {
//            //todo 时间上的校验
//            log.info("即将过期");
//        } else {
//            log.debug("有效");
//        }
//        return weChatAccessTokenInDb;
    }

    @Override
    public String getAccessTokenByCorpIdAndCorpSecret(String corpId, String corpSecret) {
        return getAndVerifyByCorpIdAndCorpSecret(corpId, corpSecret).getAccessToken();
    }

    @Override
    public WeChatAccessToken get(Long id) {
        return null;
    }
}


//    /**
//     * 除了可以获取access_token 还应该具有缓存与延期功能
//     * //1、select access_token from  qy_wechat_access_token where  corp_id=corp_id AND corp_secret=corp_secret
//     * //2、判断过期时间 未过期的则直接返回token
//     * //3、是否去获取新的token然后返回并刷新数据库，注意并非控制     * @param corpid
//     *
//     * @param corpsecret
//     * @return
//     */
//    public ResponseVo<String> getTokenOverRedis(String corpid, String corpsecret) {
//        System.out.println("corPid:" + corpid);
//        System.out.println("corpsecret:" + corpsecret);
//        String access_token = null;
//        try {
//            if (redisUtils.exists(REDIS_QYWX_ACCESS_TOKEN + corpid + ":" + corpsecret)) {
//                System.out.println("getToken from redis");
//                return ResponseVo.success((String) redisUtils.get(REDIS_QYWX_ACCESS_TOKEN + corpid + ":" + corpsecret));
//            }
//            //获取新的  ，并插入一条记录
//            Map<String, Object> resultMap = getWeChatAccessToken(corpid, corpsecret);
//            int errcode = (Integer) resultMap.get("errcode");
//            if (errcode != 0) {
//                throw new BizException(ProjectCodeEnum.INVALID_TOKEN);
//            }
//            access_token = (String) resultMap.get("access_token");
//            redisUtils.set(REDIS_QYWX_ACCESS_TOKEN + corpid + ":" + corpsecret, access_token, 7000L);
//            return ResponseVo.success(access_token);
//        } catch (BizException e) {
//            log.error("getToken error:", e);
//            return ResponseVo.failure(ProjectCodeEnum.FAILURE.getCode(), "getToken error:" + e.getMsg());
//        }
//    }
//
//    private Map<String, Object> getWeChatAccessToken(String corpid, String corpsecret) {
//        StringBuffer stringBuffer = new StringBuffer(getTokenUri);
//        String tokenUri = stringBuffer.append("?corpid=").append(corpid).append("&corpsecret=").append(corpsecret).toString();
//
//        System.out.println("tokenUri:" + tokenUri);
//        ResponseEntity<Map> response = getOverHttps(tokenUri, HttpMethod.GET, Map.class);
//        System.out.println("token response:" + response);
//        //返回值是泛型，此处是相应数据这里制定响应为String
//        log.debug("getStatusCode:", response.getStatusCode());
//        log.debug("getStatusCodeValue:", response.getStatusCodeValue());
//        log.debug("headers:", response.getHeaders());
//        log.debug("getBody:", response.getBody());
//        Map body = response.getBody();
//        return body;
//    }
