package com.arc.zero.mapper.mall;

import com.arc.core.model.domain.mall.MallArea;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 */
public interface MallAreaMapper {

    int save(MallArea mallArea);

    int delete(Long id);

    int update(MallArea mallArea);

    MallArea get(Long id);

    List<MallArea> list();

}
