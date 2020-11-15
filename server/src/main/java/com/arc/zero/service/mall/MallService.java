package com.arc.zero.service.mall;

import com.arc.core.model.domain.mall.Mall;

import java.util.List;

/**
 * JAVA项目是分层来写的，
 * 这是服务层，目的是处理业务，
 *
 * @author yechao
 * @date 2018/12/21
 */
public interface MallService {

    Long save(Mall mall);

    int delete(Long id);

    int update(Mall mall);

    Mall get(Long id);

    List<Mall> list();

    Boolean importExcel(Long fileId);


}
