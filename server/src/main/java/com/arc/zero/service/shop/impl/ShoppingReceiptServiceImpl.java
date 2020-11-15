package com.arc.zero.service.shop.impl;

import com.arc.core.model.domain.shop.ShoppingReceipt;
import com.arc.core.model.request.shop.ShoppingReceiptRequest;
import com.arc.zero.mapper.shop.ShoppingReceiptMapper;
import com.arc.zero.service.shop.ShoppingReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物小票服务
 *
 * @author lamy
 * @since 2019-12-30 22:24:13
 */
@Slf4j
@Service
public class ShoppingReceiptServiceImpl implements ShoppingReceiptService {

    @Resource
    private ShoppingReceiptMapper shoppingReceiptMapper;

    @Override
    public Long save(ShoppingReceipt shoppingReceipt) {
        //todo 是否需要验证参数的一些问题？
        log.debug("ShoppingReceipt for save={}", shoppingReceipt);
        return shoppingReceiptMapper.save(shoppingReceipt) == 1 ? shoppingReceipt.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return shoppingReceiptMapper.deleteById(id);
    }

    @Override
    public int update(ShoppingReceipt shoppingReceipt) {
        return shoppingReceiptMapper.update(shoppingReceipt);
    }

    @Override
    public ShoppingReceipt get(Long id) {
//        return shoppingReceiptMapper.get(id);
        ShoppingReceipt shoppingReceipt = shoppingReceiptMapper.get(id);
        if (shoppingReceipt == null) {
            return shoppingReceipt;
        }
        return changeNumber(shoppingReceipt);
    }

    /**
     * 转换价格倍率
     *
     * @param shoppingReceipt
     * @return
     */
    private ShoppingReceipt changeNumber(ShoppingReceipt shoppingReceipt) {
        if (shoppingReceipt == null) {
            return shoppingReceipt;
        } else {
            Integer totalFinalPrice = shoppingReceipt.getTotalFinalPrice();
            Integer totalDiscount = shoppingReceipt.getTotalDiscount();
            if (totalFinalPrice != null) {
                shoppingReceipt.setTotalFinalPrice(totalFinalPrice / 10000);
            }
            if (totalFinalPrice != null) {
                shoppingReceipt.setTotalDiscount(totalDiscount / 10000);
            }
        }
        return null;
    }

    //todo 价格处理 扩大了10000X
    private int changeNumber(Integer price) {
        if (price == null) {
            return 0;
        } else {
            return price / 10000;
        }
    }

    @Override
    public List<ShoppingReceipt> list() {
        List<ShoppingReceipt> list = shoppingReceiptMapper.list();
        if (list == null) {
            return null;
        }
        return changeNumberInList(list);
    }

    private List<ShoppingReceipt> changeNumberInList(List<ShoppingReceipt> list) {
        if (list == null) {
            return null;
        } else if (list.size() == 0) {
            return list;
        } else {
            for (ShoppingReceipt shoppingReceipt : list) {
                shoppingReceipt.setTotalFinalPrice(changeNumber(shoppingReceipt.getTotalFinalPrice()));
                shoppingReceipt.setTotalDiscount(changeNumber(shoppingReceipt.getTotalDiscount()));
            }
        }
        return list;
    }

    @Override
    public List<ShoppingReceipt> list(ShoppingReceiptRequest shoppingReceiptRequest) {
        return shoppingReceiptMapper.list();
    }

    @Override
    public Page<ShoppingReceipt> listPage(ShoppingReceiptRequest request) {
        int total = shoppingReceiptMapper.count();
        List<ShoppingReceipt> list = shoppingReceiptMapper.list();
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getPageSize());

        PageImpl<ShoppingReceipt> page = new PageImpl<>(changeNumberInList(list), pageable, total);
        return page;
    }

    @Override
    public Integer saveBatch(List<ShoppingReceipt> shoppingReceipts) {
        return shoppingReceiptMapper.saveBatch(shoppingReceipts);
    }

    @Override
    public Integer deleteIdIn(List<Long> ids) {
        return shoppingReceiptMapper.deleteIdIn(ids);
    }

    @Override
    public Integer updateBatch(List<ShoppingReceipt> shoppingReceipts) {
        return shoppingReceiptMapper.updateBatch(shoppingReceipts);
    }
}
