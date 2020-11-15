package com.arc.zero.controller.data.test.strategy;

import com.arc.zero.service.test.impl.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试策略模式
 *
 * @author yechao
 * @date 2020/8/28 3:06 下午
 */
@RestController
@RequestMapping("/strategy")
public class PayStrategyController {

    /**
     * 支付策略模式
     */
    @Autowired
    private PayStrategy payStrategy;

    @RequestMapping("pay/{type}/{everyOneNeedPay}")
    public Object pay(@PathVariable Integer type, @PathVariable Integer everyOneNeedPay) {
        return payStrategy.pay(type, everyOneNeedPay, null);
    }

    @RequestMapping("calculate/{type}/{userId}")
    public Object calculateEveryNoeNeedPay(@PathVariable Integer type, @PathVariable Long userId) {
        return payStrategy.calculateEveryNoeNeedPay(type, userId, null);
    }
}
