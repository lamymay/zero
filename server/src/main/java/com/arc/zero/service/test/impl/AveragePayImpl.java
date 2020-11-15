package com.arc.zero.service.test.impl;

import com.arc.zero.service.test.PayInterface;
import org.springframework.stereotype.Component;

/**
 * @author yechao
 * @date 2020/8/28 2:55 下午
 */
@Component("averagePayImpl")
public class AveragePayImpl implements PayInterface {

    /**
     * 计算需要支付多少
     *
     * @param userId
     * @param map
     */
    @Override
    public int calculateEveryNoeNeedPay(Long userId, Object map) {
        System.out.println(this.getClass() + "计算 success");
        return 100;
    }

    /**
     * 支付
     *
     * @param everyOneNeedPay
     * @param map
     * @return 成功否 or exception
     */
    @Override
    public boolean pay(int everyOneNeedPay, Object map) {
        System.out.println(this.getClass() + "pay success");
        return true;
    }
}
