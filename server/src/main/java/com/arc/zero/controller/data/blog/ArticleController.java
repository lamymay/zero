package com.arc.zero.controller.data.blog;

import com.arc.core.config.annotations.Note;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.blog.BlogArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@Note("文章测试相关接口")
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private BlogArticleService blogArticleService;

    //获取谁的--什么笔记本--什么文章
    //www.jianshu.com/writer#/notebooks/26120803/notes/51460318 a08703180b1c

    //
    @Note("测试使用文章编码获取一篇文章")
    @GetMapping("/a/{code}")
    public ResponseVo getBlogArticleByCode(@PathVariable("code") Long code) {
        log.debug("参数 articleId={}", code);
        return ResponseVo.success(blogArticleService.get(code));
    }

}
