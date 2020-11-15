package com.arc.zero.service.common.impl;

import com.arc.core.model.BaseModel;
import lombok.*;

/**
 * @author 叶超
 * @since 2020/2/29 11:51
 */
@Getter
@Setter
@ToString
public class CompareAndSaveDto extends BaseModel {

    private static final long serialVersionUID = 1L;

    private Integer save=0;
    private Integer delete=0;
    private Integer update=0;

    public CompareAndSaveDto() {
    }

    public CompareAndSaveDto(Integer save, Integer delete, Integer update) {
        this.save = save;
        this.delete = delete;
        this.update = update;
    }
}
