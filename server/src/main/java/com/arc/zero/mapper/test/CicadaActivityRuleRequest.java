package com.arc.zero.mapper.test;

import com.arc.core.model.request.PageQuery;
import com.arc.zero.mapper.TestBaseMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author yechao
 * @date 2020/11/12 5:06 下午
 */
@Data
@ToString(callSuper = true)
public class CicadaActivityRuleRequest extends PageQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 发码活动id
     */
    private Long activityId;


    /**
     * 发码活动名称
     */
    private String activityName;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 扩展字段
     */
    private String feature;


    /**
     * 激活码生效截止时间
     */
    private LocalDateTime gmtEffectEnd;

    /**
     * 激活码生效开始时间
     */
    private LocalDateTime gmtEffectStart;

    /**
     * 主键
     */
    private Long id;

    /**
     * 课程id
     */
    private Long lessonId;

    /**
     * 课程名称
     */
    private String lessonName;

    /**
     * 租户id
     */
    @NotNull(message = "租户ID必须提供")
    private Long orgId;

    /**
     * 租户名称
     */
    private String orgName;

    /**
     * 产品id
     */
    private Long productId;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 活动状态(失效状态不可再发放激活码)
     */
    private Integer state;

    /**
     * 优酷直充活动id
     */
    private Long ykChargeId;
}
