package com.arc.zero.service.mall;


import com.arc.core.model.domain.mall.MallQuestion;

/**
 * @author yechao
 * @date 2019/1/17 22:54
 */
public interface MallQuestionService {

    Long save(MallQuestion question);

    Integer delete(Long id);

    Integer update(MallQuestion question);

    MallQuestion get(Long id);
}
