package com.arc.zero.mapper.test;


import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 华硕优酷知了星项目_活动规则模型
 *
 * @author yechao
 * @date 2020/11/11 6:14 下午
 */
@Data
@ToString(callSuper = true)
public class CicadaActivityRuleDO {


    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 更新时间
     */
    private Date gmtModified;

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
