package com.arc.zero.service.test;

/**
 * 支付接口
 *
 * @author yechao
 * @date 2020/8/28 2:42 下午
 */
public interface PayInterface {


    /**
     * 计算需要支付多少
     */
    int calculateEveryNoeNeedPay(Long userId, Object map);

    /**
     * 支付
     *
     * @return 成功否 or exception
     */
    boolean pay(int everyOneNeedPay, Object map);

}
