package com.arc.zero.mapper.blog;

import com.arc.core.model.domain.blog.BlogArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/1/30 15:05
 */
    public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    int save(BlogArticle blogArticle);

    int delete(Long id);

    int update(BlogArticle blogArticle);

    BlogArticle get(Long id);

    List<BlogArticle> list();

    List<BlogArticle> listBlogByUserId(Long authorId);

    List<BlogArticle> listTimeLine(Long authorId);
}
