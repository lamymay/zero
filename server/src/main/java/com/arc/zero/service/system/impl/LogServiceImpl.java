package com.arc.zero.service.system.impl;

import com.arc.core.model.domain.system.Log;
import com.arc.zero.mapper.system.LogMapper;
import com.arc.zero.service.system.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 叶超
 * @since 2019/2/2 11:42
 */
@Slf4j
@Service("sysLogServiceImpl")
public class LogServiceImpl implements SysLogService {

    @Resource
    private LogMapper logMapper;

    @Override
    public Long save(Log sysLog) {
        return logMapper.save(sysLog) == 1 ? sysLog.getId() : null;
    }

    @Override
    public Integer delete(Long id) {
        return logMapper.delete(id);
    }

    @Override
    public Integer update(Log sysLog) {
        return logMapper.update(sysLog);
    }

    @Override
    public Log get(Long id) {
        return logMapper.get(id);
    }

    @Override
    public List<Log> page() {
        return logMapper.list();
    }
}
