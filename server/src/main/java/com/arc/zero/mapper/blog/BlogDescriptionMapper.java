package com.arc.zero.mapper.blog;

import com.arc.core.model.domain.blog.BlogDescription;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/2/1 16:33
 */
public interface BlogDescriptionMapper {

    int save(BlogDescription description);


    int delete(Long id);

    int update(BlogDescription description);

    BlogDescription get(Long id);

    List<BlogDescription> list();
}
