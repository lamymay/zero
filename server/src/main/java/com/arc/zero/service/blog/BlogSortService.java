package com.arc.zero.service.blog;

import com.arc.core.model.domain.blog.BlogCategory;

import java.util.List;

/**
 * 博客分类相关服务
 *
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface BlogSortService {

    Long save(BlogCategory sort);

    int delete(Long id);

    int update(BlogCategory sort);

    BlogCategory get(Long id);

    List<BlogCategory> list();


}
