package com.arc.zero.mapper.excel;

import com.arc.zero.model.excel.ExcelEntity;
import com.arc.zero.model.excel.ExcelRequest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 微信扫码记录 mapper 接口
 *
 * @author yechao
 * @since 2018-07-26
 */
public interface ExcelMapper extends BaseMapper<ExcelEntity> {


    List<ExcelEntity> listPage(ExcelRequest request);
    List<ExcelEntity> list();

    Integer countByJoinId(@Param("joinId") Long joinId);
}
