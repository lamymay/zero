package com.arc.zero.test.tree.tree2;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author yechao
 * @since 2020/8/25 3:44 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeMenuNode implements Serializable {
    private String id;
    private String parentId;
    private String name;
    private Integer sort;
    private List<TreeMenuNode> children;
    private Boolean isAble;

    /**
     * 添加 树的额外属性(至少含有父节点ID:"parentId")
     **/
    private Map<String, Object> attributes;
}