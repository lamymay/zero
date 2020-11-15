package com.arc.zero.config.properties.file.impl;

import com.arc.zero.config.properties.file.FileProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 叶超
 * @since 2020/3/8 12:44
 */
@Component
public class FilePropertiesImpl implements FileProperties {

    /**
     * 临时目录，注意你电脑上是否有该目录
     */
    @Value("${web.upload.file.path:/data/upload}")
    private String uploadDir;

    @Override
    public String getFilePersistenceDirectory() {
        return uploadDir;
    }
}
