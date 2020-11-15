package com.arc.zero.service.system.impl;

import com.arc.core.model.domain.system.KeyValue;
import com.arc.core.model.request.system.file.KeyValueRequest;
import com.arc.zero.mapper.system.KeyValueMapper;
import com.arc.zero.service.system.KeyValueService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 叶超
 * @since 2020/4/16 23:36
 */
@Service
@Slf4j
public class KeyValueServiceImpl implements KeyValueService {


    @Resource
    private KeyValueMapper keyValueMapper;

    @Override
    public Long save(KeyValue keyValue) {
        int insert = keyValueMapper.save(keyValue);
        return insert == 1 ? keyValue.getId() : null;
    }

    @Override
    public boolean update(Object request) {
        return false;
    }

    @Override
    public int delete(Long id) {
//        QueryWrapper queryWrapper = new QueryWrapper();
        return keyValueMapper.deleteById(id);
    }

    @Override
    public KeyValue get(Long id) {
        return keyValueMapper.get(id);
    }

    @Override
    public Page<KeyValue> listPage(KeyValueRequest query) {
        //count
        //list
        //数据组装

        Wrapper<KeyValue> queryWrapper = null;
        IPage<KeyValue> mpPage = keyValueMapper.selectPage(query, queryWrapper);
        System.out.println("query=" + query);

        List<KeyValue> records = mpPage.getRecords();
        long total = mpPage.getTotal();
        Pageable pageable = PageRequest.of((int) query.getCurrent(), 10);
        Page<KeyValue> page = new PageImpl<>(records, pageable, total);
        return page;
    }

    @Override
    public void saveBatchAsync(List<KeyValue> kvList) {
        // todo batch insert
        for (KeyValue keyValue : kvList) {
            save(keyValue);
        }
    }

    /**
     * 批量查询
     *
     * @param keyValue 查询一个列表的查询参数
     * @return kv的列表
     */
    @Override
    public List<KeyValue> list(KeyValue keyValue) {
        return keyValueMapper.list(keyValue);
    }
}
