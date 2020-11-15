package com.arc.zero.model.excel;

import lombok.Data;
import lombok.ToString;

/**
 * 扫码回执信息 传输对象--实体
 *
 * @author: yechao
 * @date: 2018/5/5
 */
@Data
@ToString
public class ExcelEntity {

    private Long id;
    private Long joinId;
    private Long userId;
    private String fileName;
    private String sheet;
    private String title;
    private String remark;

}
