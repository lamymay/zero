package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.SysUserAuth;
import org.apache.ibatis.annotations.Param;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 */
public interface SysUserAuthMapper {

    int save(SysUserAuth user);

    int delete(Long id);

    int update(SysUserAuth user);

    SysUserAuth get(Long id);


    /**
     * 通过 登录类型 & 标识 获取用户登录信息
     *
     * @param identifier   标识
     * @param credential   密码凭证
     * @param identityType 登录类型
     * @return
     */
    SysUserAuth getByIdentifierWithCredentialAndIdentityType(@Param("identifier") String identifier, @Param("credential") String credential, @Param("identityType") Integer identityType);

    SysUserAuth getByIdentifierAndIdentifierType(@Param("identifier") String identifier,@Param("identityType") Integer identityType);
}


//    <!--    连表查询   t_sys_user_auth left join  t_sys_user -->
//    <select id="getUserByIdentityTypeAndIdentifier" resultMap="user">
//        SELECT
//        user_.id AS user_id,
//        user_.`nickname` AS user_nickname,
//        user_.`avatar` AS user_avatar,
//        user_.`state` AS user_state,
//        user_.create_date AS user_create_date,
//        user_.update_date AS user_update_date
//        FROM
//        t_sys_user AS user_
//        LEFT JOIN t_sys_user_auth AS user_auth_ ON user_.id = user_auth_.user_id
//        WHERE
//        user_auth_.identity_type = #{identityType}
//        AND 	user_auth_.identifier =  #{identifier}
//    </select>
