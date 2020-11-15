package com.arc.zero.service.system;

import com.arc.core.model.domain.system.SysDataDictionary;

import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/9/29 23:22
 */
public interface SysDataDictionaryService {

    Long save(SysDataDictionary dictionary);

    int batchSave(List<SysDataDictionary> dictionary);

    int update(SysDataDictionary dictionary);

    int delete(Long id);

    SysDataDictionary get(Long id);

    List<SysDataDictionary> list();

    Object page();

    List<Map<String, String>> listByType(String type);
}
