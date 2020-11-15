package com.arc.zero.mapper.test;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * 激活码记录表
 *
 * @author lamy
 * @since 
 */
@Getter
@Setter
public class CicadaCdKeyRecordDO {

	private static final long serialVersionUID = 1L;


     /**
    *  激活手机号
    */
    private String activatePhone;

     /**
    *  激活优酷id
    */
    private Long activateUtid;

     /**
    *  激活码
    */
    private String activeKey;

     /**
    *  发码活动id
    */
    private Long activityId;

     /**
    *  发码活动名称
    */
    private String activityName;

     /**
    *  扩展字段
    */
    private String feature;

     /**
    *  激活时间
    */
    private Date gmtActivate;

     /**
    *  创建时间
    */
    private Date gmtCreate;

     /**
    *  激活码生效截止时间
    */
    private Date gmtEffectEnd;

     /**
    *  激活码生效开始时间
    */
    private Date gmtEffectStart;

     /**
    *  修改时间
    */
    private Date gmtModified;

     /**
    *  发送激活短信时间
    */
    private Date gmtSend;

     /**
    *  主键
    */
    private Long id;

     /**
    *  激活码状态：未使用、已使用
    */
    private Integer keyState;

     /**
    *  课程id
    */
    private Long lessonId;

     /**
    *  课程名称
    */
    private String lessonName;

     /**
    *  发码操作人姓名
    */
    private String operationName;

     /**
    *  租户id
    */
    private Long orgId;

     /**
    *  租户名称
    */
    private String orgName;

     /**
    *  产品id
    */
    private Long productId;

     /**
    *  产品名称
    */
    private String productName;

     /**
    *  发码活动名称
    */
    private String userName;

     /**
    *  发码活动名称
    */
    private String userPhone;

     /**
    *  优酷直充活动id
    */
    private Long ykChargeId;
}
