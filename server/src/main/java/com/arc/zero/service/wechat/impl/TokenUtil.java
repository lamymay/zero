package com.arc.zero.service.wechat.impl;

import com.arc.zero.model.domain.wechat.WeChatAccessToken;
import com.arc.zero.service.wechat.AccessTokenService;
import com.arc.zero.utils.https.HttpsClientRequestFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 获取AccessToken的工具类
 *
 * @author 叶超
 * @since 2019/9/27 15:03
 */
@Slf4j
@Component
public class TokenUtil {

    @Autowired
    private AccessTokenService accessTokenService;

    @Value("${msf.zss.qyWechat.getTokenUrl:https://qyapi.weixin.qq.com/cgi-bin/gettoken}")
    private String getTokenUri;


    //企业微信缓存常量系统标识前缀
    public static final String QYWX_REDIS_SYSTEM = "qywx:";

    //企业微信缓存常量员工模块标识前缀
    public static final String QYWX_REDIS_EMPLOYEE = "emoloyee:";

    //企业微信access_token缓存常量标识
    public static final String REDIS_QYWX_ACCESS_TOKEN = QYWX_REDIS_SYSTEM + "qywx_access_token:";

    //获取员工信息缓存常量标识(查询数据库)
    public static final String REDIS_QYWX_EMPLOYEE_QUERYORGINFO = QYWX_REDIS_SYSTEM + QYWX_REDIS_EMPLOYEE + "queryOrgInfoByEmpId:";

    //获取员工信息缓存常量标识(调用企业微信接口)
    public static final String REDIS_QYWX_EMPLOYEE_GETEMPLOYEE = QYWX_REDIS_SYSTEM + QYWX_REDIS_EMPLOYEE + "getEmployee:";

//    @Autowired
//    RedisUtils redisUtils;


    //======================= db 方式去缓存 ============================


    /**
     * 除了可以获取access_token 还应该具有缓存与延期功能
     * //1、select access_token from  qy_wechat_access_token where  corp_id=corp_id AND corp_secret=corp_secret
     * //2、判断过期时间 未过期的则直接返回token
     * //3、是否去获取新的token然后返回并刷新数据库，注意并非控制     * @param corpid
     *
     * @param corpsecret
     * @return
     */
    public String getToken(String corpid, String corpsecret) {

        //获取一条
        WeChatAccessToken weChatAccessToken = getAndVerifyToken(corpid, corpsecret);

        if (weChatAccessToken == null) {
            //获取新的  ，并插入一条记录
            weChatAccessToken = createNewTokenAndCache(corpid, corpsecret);
        }
        return weChatAccessToken.getAccessToken();
    }

    private WeChatAccessToken getAndVerifyToken(String corpid, String corpsecret) {
        //            Long expiresIn = weChatAccessTokenInDb.getExpiresIn();
        //            long now = System.currentTimeMillis();
        //            log.debug("expiresIn ={},now={}", expiresIn, now);
        //            long flag = expiresIn - now;
        //            log.debug("结果={}", flag);
        //            if (flag < 0 || flag < EXPIRED_MILLISECOND) {
        //                log.debug("过期 或者 即将过期");
        //                //获取新的 并更新
        //                Map<String, Object> resultMap = getWeChatAccessToken(corpid, corpsecret);
        //                int errcode = (int) resultMap.get("errcode");
        //                String errmsg = (String) resultMap.get("errmsg");
        //                String access_token = (String) resultMap.get("access_token");
        //                if (errcode != 0) {
        //                    throw new BizException(ProjectCodeEnum.ACCESS_TOKEN_ERROR);
        //                }
        //                //腾讯返回的 expires_in正常情况下为7200秒（2小时），
        //                long expires_in = (Integer) resultMap.get("expires_in");
        //                expires_in = System.currentTimeMillis() + (expires_in * 1000L);
        //                weChatAccessTokenInDb.setExpiresIn(expires_in);
        //
        //                weChatAccessTokenInDb.setResponse(errcode + errmsg + expires_in);
        //                weChatAccessTokenInDb.setStatus(StatusConstant.AVAILABLE);
        //                weChatAccessTokenInDb.setCorpId(corpid);
        //                weChatAccessTokenInDb.setCorpSecret(corpsecret);
        //                weChatAccessTokenInDb.setUpdateDate(new Date());
        //                weChatAccessTokenInDb.setVersion(1);
        //                weChatAccessTokenInDb.setAccessToken(access_token);
        //                Long update = accessTokenService.update(weChatAccessTokenInDb);
        return accessTokenService.getByCorpIdAndCorpSecret(corpid, corpsecret);
    }

    private WeChatAccessToken createNewTokenAndCache(String corpid, String corpsecret) {
        return null;

/*        Map<String, Object> resultMap = getWeChatAccessToken(corpid, corpsecret);
        int errcode = (int) resultMap.get("errcode");
        if (errcode != 0) {
            throw new BizException(ProjectCodeEnum.SECURITY_ERROR);
        }
        String errmsg = (String) resultMap.get("errmsg");
        String access_token = (String) resultMap.get("access_token");

        //	凭证的有效时间（秒）
        long expires_in = (Integer) resultMap.get("expires_in");

        weChatAccessTokenInDb = new WeChatAccessToken();
        weChatAccessTokenInDb.setResponse(errcode + errmsg + expires_in);
        weChatAccessTokenInDb.setStatus(StatusConstant.AVAILABLE);
        weChatAccessTokenInDb.setCorpId(corpid);
        weChatAccessTokenInDb.setCorpSecret(corpsecret);
        weChatAccessTokenInDb.setExpiresIn(System.currentTimeMillis() + (expires_in * 1000));
        weChatAccessTokenInDb.setVersion(1);
        weChatAccessTokenInDb.setAccessToken(access_token);
        Long save = accessTokenService.save(weChatAccessTokenInDb);
        if (save == null) {
            log.info("记录数据库失败");
            //                throw new BizException("缓存token失败");
        }
        return weChatAccessTokenInDb.getAccessToken();*/
    }






    public <T> ResponseEntity<T> getOverHttps(String url, HttpMethod method, Class<T> responseType) {
        log.info("HTTPS协议访问的url为={}", url);
        //获取 rest实例
        RestTemplate restTemplateAcceptTls = new RestTemplate(new HttpsClientRequestFactory());
        List<HttpMessageConverter<?>> messageConverters = restTemplateAcceptTls.getMessageConverters();
        //移除原来的转换器
        messageConverters.remove(1);
        //添加新的转换器
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        messageConverters.add(1, stringHttpMessageConverter);

        //header 设置
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Accept", "*/*");

        //GET请求中httpEntry为null
        String httpBody = null;
        HttpEntity<String> httpEntity = new HttpEntity<>(httpBody, httpHeaders);

        return restTemplateAcceptTls.exchange(url, method, httpEntity, responseType);
    }

    /**
     * 测试
     *
     * @param args
     */
    public static void main(String[] args) {
        //客户 成员可用企业微信添加客户的微信，并在这里与他们联系，企业可统一管理这些客户。
        String corpid = "ww8c2b159c66182a71";
        String addressBookSynchronizationSecret = "3HI4X5mu4IZ9jImISA8qUEVx1yZ79ktQvXPQdL_lWqw";
        StringBuffer stringBuffer = new StringBuffer("https://qyapi.weixin.qq.com/cgi-bin/gettoken");
        String tokenUri = stringBuffer.append("?corpid=").append(corpid).append("&corpsecret=").append(addressBookSynchronizationSecret).toString();

        ResponseEntity<Map> response = new TokenUtil().getOverHttps(tokenUri, HttpMethod.GET, Map.class);
        log.debug("getStatusCode:", response.getStatusCode());
        log.debug("getStatusCodeValue:", response.getStatusCodeValue());
        log.debug("headers:", response.getHeaders());
        log.debug("getBody:", response.getBody());
        Map map = response.getBody();
        System.out.println(response);
        System.out.println(map);
    }

}
