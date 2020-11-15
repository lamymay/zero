package com.arc.zero.mapper.mall;

import com.arc.core.model.domain.mall.MallQuestion;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 */
public interface MallQuestionMapper {

    int save(MallQuestion mallQuestion);

    int delete(Long id);

    int update(MallQuestion mallQuestion);

    MallQuestion get(Long id);

}
