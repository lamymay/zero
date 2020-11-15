package com.arc.zero.test.tree.tree2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yechao
 * @since 2020/8/25 3:45 下午
 */
//通过java8 stream转换
//实体类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZhField {
    private String id;

    /**
     * 上级领域id
     */
    private String parentId;

    /**
     * 领域名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

}