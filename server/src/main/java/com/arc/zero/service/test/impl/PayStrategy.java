package com.arc.zero.service.test.impl;

import com.arc.zero.service.test.PayInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yechao
 * @date 2020/8/28 3:00 下午
 */
@Component
public class PayStrategy {


    /*
        //构造注入 -pass
        public PayStrategy(Map<String, PayInterface> strategyMap) {
            this.strategyMap = strategyMap;
        }

        // set 注入 不可以
        public void setStrategyMap(Map<String, PayInterface> strategyMap) {
            this.strategyMap = strategyMap;
        }

    */

    @Autowired
    private Map<String, PayInterface> strategyMap;

    public int calculateEveryNoeNeedPay(int type, Long userId, Object map) {
        PayInterface payInstance = getInstance(type);
        int needPay = payInstance.calculateEveryNoeNeedPay(null, null);
        return needPay;
    }

    /**
     * 支付
     *
     * @param type
     * @param everyOneNeedPay
     * @param map
     * @return 成功否 or exception
     */
    public boolean pay(int type, int everyOneNeedPay, Object map) {
        PayInterface payInstance = getInstance(type);
        return payInstance.pay(100, null);
    }

    private PayInterface getInstance(int type) {
        switch (type) {
            case 1:
                return strategyMap.get("averagePayImpl");
            case 2:
                return strategyMap.get("fixedPayImpl");
            default:
                return null;
        }
    }
}
