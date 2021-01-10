package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * JAVA项目是分层来写的，
 * 这是持久层，目的是与数据库交互，
 *
 * @author X
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    int save(SysUser user);

    int delete(Long id);

    int update(SysUser user);

    SysUser get(Long id);

    /**
     * 根据用户id获取用户的
     *
     * @param id
     * @return
     */
    SysUser getUserWithAuth(Long id);

    List<SysUser> list();

    /*
    在使用foreach的时候最关键的也是最容易出错的就是collection属性，该属性是必须指定的，但是在不同情况 下，该属性的值是不一样的，主要有一下3种情况：

    1. 参数列表仅一个参数[单参数]
    1.1 单参数且参数类型是一个array数组的时候，collection的属性值为array
    1.2 List<基本数据类型>   collection属性值为list
    1.3 List<自定义的Object>   collection属性值为list

    2. 参数列表多个参数,建议封装成一个Map了
       封装成map，
       实际上如果你在传入参数的时候，在breast里面也是会把它封装成一个Map的，map的key就是参数名，所以这个时候collection属性值就是传入的List或array对象在自己封装的map里面的key 下面分别来看看上述三种情况的示例代码：

   */

    /**
     * 测试1
     * 不指定别名
     *
     * @return 用户列表
     */
    List<SysUser> listTest1(List<Long> ids);

    List<SysUser> listTest2(@Param("ids") List<Long> ids);

    /**
     *  指定别名& 多参数1
     *
     * @param ids ids
     * @param state state
     * @return
     */
    List<SysUser> listTest3(@Param("ids") List<Long> ids,@Param("state") Integer state);

    /**
     * 指定别名& 多参数2
     * @param ids
     * @param user
     * @return
     */
    List<SysUser> listTest4(@Param("ids") List<Long> ids,@Param("user") SysUser user);


    // 数组传参数
    List<SysUser> listTest5(Long[] ids);

    // 单个参数利用map传参数
    List<SysUser> listTest6(Map<String, Object> map);


    //多个参数利用map传参数
    List<SysUser> listTest7(Map<String ,Object> map);


}
