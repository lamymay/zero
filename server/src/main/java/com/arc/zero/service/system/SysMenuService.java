package com.arc.zero.service.system;

import com.arc.core.model.domain.system.SysMenu;
import com.arc.core.model.request.system.menu.SysMenuRequest;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 菜单相关服务层，目的是处理业务，
 *
 * @author 叶超
 * @since 2019/7/8 16:41
 */

public interface SysMenuService {

    /**
     * 保存一条数据
     *
     * @param menu
     * @return 主键
     */
    Long save(SysMenu menu);

    /**
     * 批量保存
     *
     * @param menus 数据集合
     * @return 操作成功的数量
     */
    int saveBatch(List<SysMenu> menus);


    /**
     * 单条数据查询
     *
     * @param id 主键id
     * @return 单条数据
     */
    SysMenu get(Long id);

    /**
     * 物理删除 deletePhysically 【默认】
     *
     * @param id 主键
     * @return 删除是否成功
     */
    boolean delete(Long id);

    /**
     * 逻辑删除【实际上是更新】
     *
     * @param id
     * @return
     */
    boolean deleteLogical(Long id);

    /**
     * 更新
     *
     * @param request
     * @return 操作成功数量
     */
    int update(SysMenu request);

    /**
     * 返回带层级结构的已经启用的菜单数据
     *
     * @param systemId
     * @param level
     * @return
     */
    List<SysMenu> listMenus(Integer systemId, Integer level);


    List<SysMenu> listAllMenus();


    /**
     * 分页查询
     *
     * @param request SysMenuRequest
     * @return
     */
    Page<SysMenu> listPage(SysMenuRequest request);

    /**
     * 查询组装树状数据结构
     *
     * @param systemId 系统id
     * @return 有层级结构的数据
     */
    List<SysMenu> listTreeMenuWithSystemIdAndLevel(Integer systemId);
}
