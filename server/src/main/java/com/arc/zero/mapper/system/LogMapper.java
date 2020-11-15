package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/2/2 11:25
 */
public interface LogMapper extends BaseMapper<Log> {

    int save(Log sysLog);

    int delete(Long id);

    int update(Log sysLog);

    Log get(Long id);

    List<Log> list();
}
