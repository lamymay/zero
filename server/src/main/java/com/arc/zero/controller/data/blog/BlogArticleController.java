package com.arc.zero.controller.data.blog;

import com.arc.core.config.annotations.Note;
import com.arc.core.model.domain.blog.BlogArticle;
import com.arc.core.model.request.blog.BlogArticleRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.blog.BlogArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 博客信息主出口
 * 文章相关的业务场景：
 * 1单条数据相关-
 * 1.1、查看一篇文章/修改时候的回显
 * 1.2、修改一篇、删除
 * 2批量相关
 * 2.1、批量审核文章--update/delete
 * 2.2、批量查看？
 *
 * @author 叶超
 * @since 2019/1/30 15:17
 */
@Slf4j
@RestController
@Note("文章相关接口")
@RequestMapping("/blog")
public class BlogArticleController {

    @Resource
    private BlogArticleService blogArticleService;


    /**
     * 保存，新建文章
     *
     * @param blog 文章
     * @return 有效信息为数据的id
     */
    @PostMapping("/save")
    @Note("保存文章")
    public ResponseVo create(@RequestBody BlogArticle blog) {
        log.debug("结果={}", blog);
        return ResponseVo.success(blogArticleService.save(blog));
    }

    /**
     * 获取指定id的文章
     */
    //www.jianshu.com/writer#/notebooks/26120803/notes/51460318 a08703180b1c
    @Note("获取文章")
    //【谁的】/【什么笔记本的】/【什么文章】
//    @GetMapping("/user/{authorId}/notebooks/{notebookId}/notes/{articleId}")
    @GetMapping("/{articleId}")
    public ResponseVo getBlogArticleByUserId(
//            @PathVariable("authorId") Long authorId,
//            @PathVariable("notebookId") Long notebookId,
            @PathVariable("articleId") Long articleId) {
        log.debug("参数 articleId={}", articleId);
        return ResponseVo.success(blogArticleService.get(articleId));
    }

//    /**
//     * 获取文章
//     *
//     * @param id
//     * @return
//     */
//    @Note("获取文章")
//    @GetMapping("/{id}")
//    public ResponseVo get(@PathVariable("id") Long id) {
//        log.debug("参数id={}", id);
//        return ResponseVo.success(blogArticleService.get(id));
//    }

    /**
     * 更新，编辑，发布
     *
     * @param blog
     * @return
     */
    @PostMapping("/update")
    @Note("保更新文章")
    public ResponseVo modify(@RequestBody BlogArticle blog) {
        return ResponseVo.success(blogArticleService.update(blog));
    }

    @PostMapping("/page")
    @Note("分页获取文章")
    public ResponseVo page() {
        BlogArticleRequest query = new BlogArticleRequest();
        return ResponseVo.success(blogArticleService.listPage(query));
    }

    @GetMapping("/timeline/{authorId}")
    @Note("时间线")
    public ResponseVo timeline(@PathVariable("authorId") Long authorId) {
        return ResponseVo.success(blogArticleService.listTimeLine(authorId));
    }

}
