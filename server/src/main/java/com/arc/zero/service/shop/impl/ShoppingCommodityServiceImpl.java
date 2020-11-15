package com.arc.zero.service.shop.impl;

import com.arc.core.model.domain.shop.ShoppingCommodity;
import com.arc.core.model.request.shop.ShoppingCommodityRequest;
import com.arc.zero.mapper.shop.ShoppingCommodityMapper;
import com.arc.zero.service.shop.ShoppingCommodityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物小票服务
 *
 * @author lamy
 * @since 2019-12-30 22:06:53
 */
@Slf4j
@Service
public class ShoppingCommodityServiceImpl implements ShoppingCommodityService {

    @Resource
    private ShoppingCommodityMapper shoppingCommodityMapper;

    @Override
    public Long save(ShoppingCommodity shoppingCommodity) {
        return shoppingCommodityMapper.save(shoppingCommodity) == 1 ? shoppingCommodity.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return shoppingCommodityMapper.delete(id);
    }

    @Override
    public int update(ShoppingCommodity shoppingCommodity) {
        return shoppingCommodityMapper.update(shoppingCommodity);
    }

    @Override
    public ShoppingCommodity get(Long id) {
        return shoppingCommodityMapper.get(id);
    }

    @Override
    public List<ShoppingCommodity> list() {
        return shoppingCommodityMapper.list();
    }

    @Override
    public List<ShoppingCommodity> list(ShoppingCommodityRequest shoppingCommodityRequest) {
        return shoppingCommodityMapper.list();
    }

    @Override
    public Page<ShoppingCommodity> listPage(ShoppingCommodityRequest shoppingCommodityRequest) {
        //return listPage(shoppingCommodityRequest);
        return null;
    }

    @Override
    public Integer saveBatch(List<ShoppingCommodity> shoppingCommoditys) {
        return shoppingCommodityMapper.saveBatch(shoppingCommoditys);
    }

    @Override
    public Integer deleteIdIn(List<Long> ids) {
        return shoppingCommodityMapper.deleteIdIn(ids);
    }

    @Override
    public Integer updateBatch(List<ShoppingCommodity> shoppingCommoditys) {
        return shoppingCommodityMapper.updateBatch(shoppingCommoditys);
    }
}
