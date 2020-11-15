package com.arc.zero.service.excel.impl;

import com.arc.zero.mapper.excel.ExcelMapper;
import com.arc.zero.service.excel.ExcelService;
import com.arc.zero.model.excel.ExcelEntity;
import com.arc.zero.model.excel.ExcelRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 叶超
 * @since 2019/11/1 12:48
 */
@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private ExcelMapper excelMapper;

    @Override
    public List<ExcelEntity> listPage(ExcelRequest excelEntity) {
        return excelMapper.listPage(excelEntity);
    }

    @Override
    public Integer countByJoinId(Long joinId) {
        return excelMapper.countByJoinId(joinId);

    }

}
