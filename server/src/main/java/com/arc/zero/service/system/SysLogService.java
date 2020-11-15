package com.arc.zero.service.system;

import com.arc.core.model.domain.system.Log;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface SysLogService {

    Long save(Log sysLog);

    Integer delete(Long id);

    Integer update(Log sysLog);

    Log get(Long id);

    List<Log> page();


}
