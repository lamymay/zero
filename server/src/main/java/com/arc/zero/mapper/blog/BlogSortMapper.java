package com.arc.zero.mapper.blog;

import com.arc.core.model.domain.blog.BlogCategory;
import com.arc.zero.mapper.TestBaseMapper;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/1/30 15:05
 */
public interface BlogSortMapper extends TestBaseMapper<BlogCategory> {

    int save(BlogCategory sort);

    int delete(Long id);

    int update(BlogCategory sort);

    BlogCategory get(Long id);

    List<BlogCategory> list();

}
