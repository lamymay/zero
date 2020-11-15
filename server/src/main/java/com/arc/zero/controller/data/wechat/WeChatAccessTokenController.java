package com.arc.zero.controller.data.wechat;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.model.domain.wechat.WeChatAccessToken;
import com.arc.zero.service.wechat.AccessTokenService;
import com.arc.zero.service.wechat.impl.TokenUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 获取微信access Token的接口
 *
 * @author yechao
 * @description 客户相关服务的接口
 * @since 2019/9/25
 */
@Slf4j
@Api(tags = "获取微信accessToken的接口")
@RestController
@RequestMapping("/tokens")
public class WeChatAccessTokenController {

    @Autowired
    private AccessTokenService accessTokenService;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * 数据库中配置的数据直接测试
     * 注意：
     * 1、测试用，发布后将被移除：
     * 2、请使用 新方法： getAccessTokenByCopIdAndCorpSecret
     *
     * @param id 数据库中配置的相应数据的id（一个企业的一个模块有一个密钥）
     * @return accessToken的字符串为body的有效数据
     */
    @Deprecated
    @GetMapping("/{id}")
    public ResponseVo<String> getAccessTokenById(@PathVariable Long id) {
        log.debug("测试中用数据库中配置的数据直接测试 /api/tokens/{id}={}", id);
        WeChatAccessToken weChatAccessToken = accessTokenService.get(id);
        if (weChatAccessToken == null) {
            throw new BizException(ProjectCodeEnum.SECURITY_ERROR);
        }
        return getAccessTokenByCopIdAndCorpSecret(weChatAccessToken.getCorpId(), weChatAccessToken.getCorpSecret());
    }

    /**
     * 注意token获取失败可能原因：
     * 1、corpSecret 应用的密钥被更新了，数据库中的配置未能及时做出修正，请手动修复数据库中的配置数据即可
     *
     * @param copId      企业微信中的企业id
     * @param corpSecret 企业微信中的该企业的指定应用的密钥，注意这个密钥是可能变更的，
     * @return accessToken的字符串为body的有效数据
     */
    @GetMapping("/{copId}/{corpSecret}")
    public ResponseVo<String> getAccessTokenByCopIdAndCorpSecret(@PathVariable String copId, @PathVariable String corpSecret) {
        return ResponseVo.success(tokenUtil.getToken(copId, corpSecret));
    }


}
