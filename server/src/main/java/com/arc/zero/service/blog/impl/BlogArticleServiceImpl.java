package com.arc.zero.service.blog.impl;

import com.arc.core.model.domain.blog.BlogArticle;
import com.arc.core.model.request.blog.BlogArticleRequest;
import com.arc.zero.mapper.blog.BlogArticleMapper;
import com.arc.zero.service.blog.BlogArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
@Service
@Transactional
public class BlogArticleServiceImpl implements BlogArticleService {

    @Resource
    private BlogArticleMapper blogArticleMapper;

    @Override
    public Long save(BlogArticle blogArticle) {
        return blogArticleMapper.save(blogArticle) == 1 ? blogArticle.getId() : null;
    }

    @Override
    public Integer delete(Long id) {
        return blogArticleMapper.delete(id);
    }

    @Override
    public Integer update(BlogArticle blogArticle) {
        return blogArticleMapper.update(blogArticle);
    }

    @Override
    public BlogArticle get(Long id) {
        return blogArticleMapper.get(id);
    }

    @Override
    public List<BlogArticle> list() {
        return blogArticleMapper.list();
    }

    //todo 分页
    @Override
    public List<BlogArticle> listPage(BlogArticleRequest query) {
        return blogArticleMapper.list();
    }

    @Override
    public List<BlogArticle> listBlogByUserId(Long authorId) {
        return blogArticleMapper.listBlogByUserId(authorId);
    }

    @Override
    public List<Map<String, String>> listTimeLine(Long authorId) {
        List<BlogArticle> articles = blogArticleMapper.listTimeLine(authorId);
        List<Map<String, String>> results = new LinkedList<>();

        for (BlogArticle article : articles) {
            StringBuffer value = new StringBuffer();

//            String content = "";
//            try {
//                content = article.getContent().substring(0, 7);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                log.error("结果={}",ex);
//            }
            value.append(article.getId()).append(" ").append(article.getTitle()).append(" ");//.append(content);
            HashMap<String, String> map = new HashMap<>();
            map.put("timeLine", value.toString());
            results.add(map);
        }

        return results;
    }
}
