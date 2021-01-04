package com.arc.zero.controller.data.bet;

import com.arc.core.model.domain.bet.Award;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * 投注相关测试接口[后台管理相关接口]
 *
 * @author yechao
 * @date 2021/01/03 21:39 下午
 */
@RestController
@RequestMapping("/v1/bet/back")
public class BetController {


    // 1 奖品表
    // 2 夺宝记录表 奖品id-用户Id-兑换码id
    // 3 订单表 奖品id-用户id-地址等

    @PostMapping("/save")
    public ResponseEntity saveOrUpdate(@RequestBody Award award) {
        //文件扫描后入库 返回成功的数量
        HashMap<String, Object> map = new HashMap<>();
        map.put("award", award);
        return ResponseEntity.ok(map);
    }

}


