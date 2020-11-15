package com.arc.zero.service.blog;

import com.arc.core.model.domain.blog.BlogArticle;
import com.arc.core.model.request.blog.BlogArticleRequest;

import java.util.List;
import java.util.Map;

/**
 * 博客信息相关服务
 *
 * @author 叶超
 * @since 2019/1/30 17:33
 */
public interface BlogArticleService {

    Long save(BlogArticle blogArticle);

    Integer delete(Long id);

    Integer update(BlogArticle blogArticle);

    BlogArticle get(Long id);

    List<BlogArticle> list();

    List<BlogArticle> listPage(BlogArticleRequest query);

    List<BlogArticle> listBlogByUserId(Long authorId);

    /**
     * 时间线 查询接口--测试哦
     *
     * @param authorId
     * @return
     */
    List<Map<String, String>> listTimeLine(Long authorId);
}
