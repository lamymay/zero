package com.arc.zero.service.excel;

import com.arc.zero.model.excel.ExcelEntity;
import com.arc.zero.model.excel.ExcelRequest;

import java.util.List;

/**
 * @author 叶超
 * @since 2019/11/1 12:48
 */
public interface ExcelService {

    List<ExcelEntity> listPage(ExcelRequest excelEntity);

    Integer countByJoinId(Long joinId);

}
