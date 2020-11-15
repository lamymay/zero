package com.arc.zero.config.properties.file;

/**
 * 文件上传相关依赖信息约束用接口
 *
 * @author 叶超
 * @since 2020/3/8 12:43
 */
public interface FileProperties {

    /**
     * 文件被写入目录
     *
     * @return
     */
    String getFilePersistenceDirectory();
}
