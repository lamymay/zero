package com.arc.zero.service.system;

import com.arc.core.model.domain.system.KeyValue;
import com.arc.core.model.request.system.file.KeyValueRequest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author 叶超
 * @since 2020/4/16 23:31
 */
public interface KeyValueService {


    Long save(KeyValue keyValue);

    boolean update(Object request);

    int delete(Long id);

    KeyValue get(Long id);

    Page<KeyValue> listPage(KeyValueRequest query);

    /**
     * 批量保存异步化--线程池
     *
     * @param kvList data
     */
    void saveBatchAsync(List<KeyValue> kvList);

    /**
     * 批量查询
     *
     * @param keyValue 查询一个列表的查询参数
     * @return kv的列表
     */
    List<KeyValue> list(KeyValue keyValue);
}
