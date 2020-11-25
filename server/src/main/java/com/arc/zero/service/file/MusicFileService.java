package com.arc.zero.service.file;

import java.util.Map;

/**
 * 文件扫描初始化相关服务
 *
 * @author 叶超
 * @since 2020/3/4 13:30
 */
public interface MusicFileService {

    Integer init(Map<String, Object> map);

    Boolean deleteByCode(String code);
}
