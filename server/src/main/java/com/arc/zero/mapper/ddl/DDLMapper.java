package com.arc.zero.mapper.ddl;

import org.apache.ibatis.annotations.Param;

/**
 * 执行DDL的DAO
 *
 * @author 叶超
 * @since 2020/2/29 11:28
 */
public interface DDLMapper {

    /**
     * 删减表，id将会从0开始递增
     *
     * @param tableName 表名称
     */
    void truncate(@Param("tableName") String tableName);

}
