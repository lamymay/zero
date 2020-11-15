package com.arc.zero.mapper.system;

import com.arc.core.model.domain.system.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 表数据库控制层接口
 *
 * @author X
 */
public interface MenuMapper extends BaseMapper<SysMenu> {

    Long save(SysMenu menu);

    int delete(Long menu);

    int update(SysMenu menu);

    /**
     * ·
     * 主键查询
     *
     * @param id
     * @return
     */
    SysMenu get(Long id);

    /**
     * 逻辑删除，即：更新
     *
     * @param id
     * @return
     */
    int deleteLogical(Long id);

    /**
     * 批量保存，待测试
     *
     * @param menus
     * @return
     */
    int saveBatch(@Param("menus") List<SysMenu> menus);

    /**
     * select * from menu where  systemId=AAA AND level=1 AND  state!=0
     * level为null 查询全部的
     * systemId 不为null
     *
     * @param systemId 系统id
     * @param level    level can be null
     * @return
     */
    List<SysMenu> listMenu(@Param("systemId") Integer systemId, @Param("level") Integer level);

    List<SysMenu> list();

    List<SysMenu> listMenuBySystemIdAndParentId(@Param("systemId") Integer systemId, @Param("parentId") Long parentId);

    //    List<SysMenu> list();

}
