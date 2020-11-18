package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.KeyValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/2/2 11:25
 */
public interface KeyValueMapper extends BaseMapper<KeyValue> {

    int save(KeyValue keyValue);

    KeyValue get(Long id);

    int deleteByRequest(Map<String, String> map);

    List<KeyValue> listAll();

    KeyValue getById(@Param("id") Long id);

    /**
     * 根据类型来去一个集合
     *
     * @param keyValue 参数
     * @return 集合
     */
    List<KeyValue> list(KeyValue keyValue);
}
