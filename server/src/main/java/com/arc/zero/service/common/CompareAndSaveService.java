package com.arc.zero.service.common;

import com.arc.core.model.domain.system.SysResource;

import java.util.Set;

/**
 * @author 叶超
 * @since 2020/2/29 11:37
 */
@Deprecated
public interface CompareAndSaveService {

    int deleteBatch(Set<Long> deleteIdSet);

    int updateBatch(Set<SysResource> resources);

    int saveBatch(Set<SysResource> resources);


}
