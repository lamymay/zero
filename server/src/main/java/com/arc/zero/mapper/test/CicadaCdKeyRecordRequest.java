package com.arc.zero.mapper.test;

import java.io.Serializable;
import java.util.Date;

import com.arc.core.model.request.TestPageQuery;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 激活码记录表
 *
 * @author lamy
 * @since 
 */
@Getter
@Setter
@ToString
public class CicadaCdKeyRecordRequest extends TestPageQuery {

	private static final long serialVersionUID = 1L;

	private Long id;// 主键
	private String activatePhone;// 激活手机号
	private Long activateUtid;// 激活优酷id
	private String activeKey;// 激活码
	private Long activityId;// 发码活动id
	private String activityName;// 发码活动名称
	private String feature;// 扩展字段
	private Date gmtActivate;// 激活时间
	private Date gmtCreate;// 创建时间
	private Date gmtEffectEnd;// 激活码生效截止时间
	private Date gmtEffectStart;// 激活码生效开始时间
	private Date gmtModified;// 修改时间
	private Date gmtSend;// 发送激活短信时间
	private Integer keyState;// 激活码状态：未使用、已使用
	private Long lessonId;// 课程id
	private String lessonName;// 课程名称
	private String operationName;// 发码操作人姓名
	private Long orgId;// 租户id
	private String orgName;// 租户名称
	private Long productId;// 产品id
	private String productName;// 产品名称
	private String userName;// 发码活动名称
	private String userPhone;// 发码活动名称
	private Long ykChargeId;// 优酷直充活动id
}
