package com.arc.zero.service.mall;

import com.arc.core.model.domain.mall.MallTask;

import java.util.List;

/**
 * @author yechao
 * @date 2019/1/17 22:37
 */
public interface MallTaskService {

    Long save(MallTask task);

    Integer delete(Long id);

    Integer update(MallTask task);

    MallTask get(Long id);

    List<MallTask> list();
}
