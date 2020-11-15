package com.arc.zero.service.mall.impl;

import com.arc.core.model.domain.mall.MallQuestion;
import com.arc.zero.mapper.mall.MallQuestionMapper;
import com.arc.zero.service.mall.MallQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: yechao
 * @date: 2019/1/17 22:54
 */
@Service
@Slf4j
public class MallQuestionServiceImpl implements MallQuestionService {

    @Autowired
    private MallQuestionMapper questionMapper;

    @Override
    public Long save(MallQuestion question) {
        try {
            if (question.getAnswer() != null && question.getAnswer().trim().length() > 0) {
                int save = questionMapper.save(question);
                return save == 0 ? null : question.getId();
            } else {
                return null;
            }

        } catch (Exception e) {
            log.error("{}", e);
            e.printStackTrace();
            System.out.println("area 错误" + e.getCause());
            log.error("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!questionMapper.save出错={}",e);

            return null;
        }
    }

    @Override
    public Integer delete(Long id) {
        return questionMapper.delete(id);
    }

    @Override
    public Integer update(MallQuestion question) {
        return questionMapper.update(question);
    }

    @Override
    public MallQuestion get(Long id) {
        return questionMapper.get(id);
    }
}
