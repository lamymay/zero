package com.arc.zero.model.excel;

import lombok.Data;
import lombok.ToString;

/**
 * @author 叶超
 * @since 2019/11/1 16:13
 */
@Data
@ToString
public class ExcelRequest {

    private Integer currentPage = 1;

    private Integer pageSize = 10;

    private Integer offSet;

    public Integer getOffSet() {
        return (currentPage - 1) * pageSize;
    }

    private Long id;
    private Long joinId;

    private String sheet;
    private String name;
    private String title;

    private String fileName;


    private Long userId; // 用户编号

    private String key; // key


}
