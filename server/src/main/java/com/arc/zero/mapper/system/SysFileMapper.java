package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.SysFile;
import com.arc.core.model.request.system.file.SysFileRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 */
public interface SysFileMapper extends BaseMapper<SysFile> {

    int save(SysFile sysFile);

    int delete(Long id);

    int update(SysFile sysFile);

    SysFile get(Long id);

    List<SysFile> list();

    SysFile getByCode(String code);

    SysFile getByRequest(SysFileRequest request);

    List<SysFile> listByRequest(SysFileRequest request);

    int deleteByRequest(Map<String, Object> map);

    int count();

}
