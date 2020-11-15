package com.arc.zero.service.blog.impl;

import com.arc.core.model.domain.blog.BlogTag;
import com.arc.zero.mapper.blog.BlogTagMapper;
import com.arc.zero.service.blog.BlogTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
@Service
public class BlogTagServiceImpl implements BlogTagService {

    @Resource
    private BlogTagMapper tagMapper;

    @Override
    public Long save(BlogTag tag) {
        return tagMapper.save(tag) == 1 ? tag.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return tagMapper.delete(id);
    }

    @Override
    public int update(BlogTag tag) {
        return tagMapper.update(tag);
    }

    @Override
    public BlogTag get(Long id) {
        return tagMapper.get(id);
    }

    @Override
    public List<BlogTag> list() {
        return tagMapper.list();
    }
}
