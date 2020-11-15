package com.arc.zero.service.system.impl;

import com.alibaba.fastjson.JSON;
import com.arc.core.model.domain.system.SysMenu;
import com.arc.core.model.request.system.menu.SysMenuRequest;
import com.arc.zero.mapper.system.MenuMapper;
import com.arc.zero.service.system.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author 叶超
 * @since 2019/7/8 16:44
 */
@Slf4j
//说明， 这里持久层使用Mybatis Plus  初次使用，本service是使用mp方式去做
@Service("menuServiceImpl")
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Long save(SysMenu menu) {
//        return menuMapper.insert(menu) == 1 ? menu.getId() : null;
        return menuMapper.save(menu);
    }

    @Override
    public boolean delete(Long id) {
        return menuMapper.deleteById(id) != 0;
    }

    @Override
    public int update(SysMenu menu) {
        return menuMapper.update(menu);
    }

    @Override
    public SysMenu get(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public boolean deleteLogical(Long id) {
        return false;
    }


    @Override
    public int saveBatch(List<SysMenu> menus) {
        return 0;
    }

    @Override
    public List<SysMenu> listMenus(Integer systemId, Integer level) {
        if (systemId == null) {
            return Collections.emptyList();
        }
        //level == null ,全部层级的菜单
        return menuMapper.listMenu(systemId, level);
    }

    @Override
    public List<SysMenu> listAllMenus() {
        return menuMapper.list();
    }

    /**
     * 分页查询
     *
     * @param request SysMenuRequest
     * @return
     */
    @Override
    public Page<SysMenu> listPage(SysMenuRequest request) {
        long total = 1000;
        return new PageImpl<SysMenu>(null, request, total);
    }

    /**
     * 查询组装树状数据结构
     *
     * @param systemId 系统id
     * @return 有层级结构的数据
     */
    @Override
    public List<SysMenu> listTreeMenuWithSystemIdAndLevel(Integer systemId) {
        long t1 = System.currentTimeMillis();
        //List<SysMenu> sysMenus = treeSysMenu1(systemId, 0L);
        List<SysMenu> sysMenus = treeSysMenu2(systemId);
        long t2 = System.currentTimeMillis();
        System.out.println("耗时=" + (t2 - t1));
        return sysMenus;
    }


    /**
     * 建树测试1
     * 思路:多次查询db组装children
     *
     * @param systemId 系统id
     * @param parentId 父id
     * @return 树形数据
     */
    private List<SysMenu> treeSysMenu1(Integer systemId, Long parentId) {
        List<SysMenu> menuLevel1 = menuMapper.listMenuBySystemIdAndParentId(systemId, parentId);
        if (menuLevel1 == null) {
            return null;
        }
        for (SysMenu sysMenu1 : menuLevel1) {
            sysMenu1.setChildren(treeSysMenu1(systemId, sysMenu1.getId()));
        }
        return menuLevel1;
    }

    /**
     * 建树测试2
     * 思路:一次查询全部待组装数据,后内存中做组装
     *
     * @param systemId 系统id
     * @return 树形数据
     */
    public static List<SysMenu> treeSysMenu2(Integer systemId) {
        //List<SysMenu> allMenus = listMenus(systemId, null);
        List<SysMenu> allMenus = getMenuTestData();
        log.debug("建树测试2,一次查询全部待组装数据,size={},menus={}", (allMenus == null ? 0 : allMenus.size()), JSON.toJSONString(allMenus));
        if (allMenus == null || allMenus.size() == 0) {
            return null;
        }
        List<SysMenu> rootMenus = new ArrayList<>(16);
        Map<Long, SysMenu> menuMap = new HashMap<>();

        List<SysMenu> menus2 = new ArrayList<>(16);
        int level = 0;
        for (SysMenu node1 : allMenus) {
            menuMap.put(node1.getId(), node1);

            //level1 的
            if (node1.getLevel() == 1) {
                for (SysMenu node2 : allMenus) {
                    if (node1.getId().equals(node2.getParentId())) {
                        if (node1.getChildren() == null) {
                            ArrayList<SysMenu> children = new ArrayList<>();
                            children.add(node2);
                            node1.setChildren(children);
                        } else {
                            node1.getChildren().add(node2);
                        }
                    }
                }
                rootMenus.add(node1);
            }
        }
        return rootMenus;
    }

    private static List<SysMenu> getMenuTestData() {
        String jsonStrong = "[{\"code\":\"0\",\"createDate\":1574652093000,\"id\":6,\"level\":1,\"name\":\"一级菜单A\",\"nameEnglish\":\"A\",\"parentId\":0,\"sort\":1,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":7,\"level\":1,\"name\":\"一级菜单B\",\"nameEnglish\":\"B\",\"parentId\":0,\"sort\":2,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":8,\"level\":1,\"name\":\"一级菜单C\",\"nameEnglish\":\"C\",\"parentId\":0,\"sort\":3,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":9,\"level\":1,\"name\":\"一级菜单D\",\"nameEnglish\":\"D\",\"parentId\":0,\"sort\":4,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":10,\"level\":1,\"name\":\"一级菜单E\",\"nameEnglish\":\"E\",\"parentId\":0,\"sort\":5,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":11,\"level\":1,\"name\":\"一级菜单F\",\"nameEnglish\":\"F\",\"parentId\":0,\"sort\":6,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":1,\"level\":1,\"name\":\"系统\",\"nameEnglish\":\"system\",\"parentId\":0,\"sort\":41,\"state\":1,\"systemId\":4,\"updateDate\":1562663899000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":2,\"level\":1,\"name\":\"文件\",\"nameEnglish\":\"file\",\"parentId\":0,\"sort\":42,\"state\":1,\"systemId\":4,\"updateDate\":1562663899000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":3,\"level\":1,\"name\":\"测试\",\"nameEnglish\":\"test\",\"parentId\":0,\"sort\":43,\"state\":1,\"systemId\":4,\"updateDate\":1562663899000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":4,\"level\":1,\"name\":\"app\",\"nameEnglish\":\"application\",\"parentId\":0,\"sort\":44,\"state\":1,\"systemId\":4,\"updateDate\":1562663899000},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":5,\"level\":1,\"name\":\"高级\",\"nameEnglish\":\"special\",\"parentId\":0,\"sort\":45,\"state\":1,\"systemId\":4,\"updateDate\":1562663899000},{\"code\":\"0\",\"id\":30,\"level\":2,\"name\":\"QR\",\"note\":\"二维码的\",\"parentId\":3,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/qr-test\"},{\"code\":\"0\",\"id\":28,\"level\":2,\"name\":\"Resource\",\"note\":\"ResourceResourceResource\",\"parentId\":2,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/resource\"},{\"code\":\"0\",\"id\":27,\"level\":2,\"name\":\"账单管理\",\"note\":\"ShoppingReceipt\",\"parentId\":2,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/sr\"},{\"code\":\"0\",\"id\":26,\"level\":2,\"name\":\"File文件\",\"parentId\":2,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/file\"},{\"code\":\"0\",\"id\":25,\"level\":2,\"name\":\"测试ModalDemo\",\"note\":\"测试\",\"parentId\":4,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/ModalDemo\"},{\"code\":\"0\",\"id\":24,\"level\":2,\"name\":\"测试博客详情\",\"note\":\"测试\",\"parentId\":4,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/blog-detail\"},{\"code\":\"0\",\"id\":23,\"level\":2,\"name\":\"写博客\",\"note\":\"blog-MD\",\"parentId\":4,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/bmd\"},{\"code\":\"0\",\"id\":22,\"level\":2,\"name\":\"博客管理\",\"note\":\"manage-blog.....\",\"parentId\":4,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/manage-blog\"},{\"code\":\"0\",\"id\":21,\"level\":2,\"name\":\"菜单\",\"note\":\"menu\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/menu\"},{\"code\":\"0\",\"id\":20,\"level\":2,\"name\":\"md\",\"note\":\"点点滴滴都是\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/md\"},{\"code\":\"0\",\"id\":19,\"level\":2,\"name\":\"博客\",\"note\":\"\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"url\":\"/blog\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":18,\"level\":2,\"name\":\"图片预览\",\"note\":\"4\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/file-search\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":17,\"level\":2,\"name\":\"样式测试\",\"note\":\"4\",\"parentId\":3,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/css-test\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":16,\"level\":2,\"name\":\"登录\",\"note\":\"4\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/login\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":15,\"level\":2,\"name\":\"主页\",\"note\":\"4\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/index\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":14,\"level\":2,\"name\":\"文件\",\"note\":\"4\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/file\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":13,\"level\":2,\"name\":\"role\",\"note\":\"4\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/role\"},{\"code\":\"0\",\"createDate\":1574652093000,\"id\":12,\"level\":2,\"name\":\"用户列表\",\"note\":\"4\",\"parentId\":1,\"sort\":100,\"state\":1,\"systemId\":4,\"updateDate\":1574652093000,\"url\":\"/user\"}]";
        return JSON.parseArray(jsonStrong, SysMenu.class);
    }

    public static void main(String[] args) {

        System.out.println("46143E3AB80F4282".length());
        List<SysMenu> treeSysMenus = treeSysMenu2(4);
        System.out.println(JSON.toJSONString(treeSysMenus));

        Date date1 = new Date();
        LocalDateTime date2 = LocalDateTime.now();
        LocalDateTime date3 = LocalDateTime.now().plusDays(1);

        System.out.println(date1);
        System.out.println(date2);
        System.out.println(date3);
    }


    // 可以加入权限控制 不同权限的人菜单元素不一样 处理allMenus即可
//
//        ArrayList<SysMenu> level1 = new ArrayList<>();
//        ArrayList<SysMenu> level2 = new ArrayList<>();
//        ArrayList<SysMenu> level3 = new ArrayList<>();
//        ArrayList<SysMenu> level4 = new ArrayList<>();
//
//
//        for (SysMenu everyone : allMenus) {
//            int level = everyone.getLevel();
//            if (1 == level) {
//                level1.add(everyone);
//            } else if (2 == level) {
//                level2.add(everyone);
//            } else if (3 == level) {
//                level3.add(everyone);
//            } else if (4 == level) {
//                level4.add(everyone);
//            }
//        }
//
//        //按照level层级来组织数据
//        for (SysMenu rootNode : level1) {
//            //准备集合装children
//            List<SysMenu> children = rootNode.getChildren();
//            if (children == null) {
//                children = new ArrayList<>(16);
//            }
//
//            //child
//            for (SysMenu node2 : level2) {
//                if (node2.getParentId() != null && node2.getParentId().equals(rootNode.getId())) {
//                    children.add(node2);
//                }
//            }
//            rootNode.setChildren(children);
//        }
//
//        //2 级
//        for (SysMenu node2 : level2) {
//            //准备集合装children
//            List<SysMenu> children = node2.getChildren();
//            if (children == null) {
//                children = new ArrayList<>(16);
//            }
//
//            //child
//            for (SysMenu node3 : level3) {
//                if (node3.getParentId() != null && node3.getParentId().equals(node2.getId())) {
//                    children.add(node2);
//                }
//            }
//            node2.setChildren(children);
//        }
//
//
//        //3 级
//        for (SysMenu node3 : level3) {
//            //准备集合装children
//            List<SysMenu> children = node3.getChildren();
//            if (children == null) {
//                children = new ArrayList<>(16);
//            }
//
//            //child
//            for (SysMenu node4 : level4) {
//                if (node4.getParentId() != null && node4.getParentId().equals(node3.getId())) {
//                    children.add(node4);
//                }
//            }
//            node3.setChildren(children);
//        }
//        return level1;

    //菜单a
    //|----a1
    //|----a2
    //|----a3
    //     |----a31
    //     |----a32
    //     |----a33
    //菜单b
    //|----b1
    //|----b2
    //|----b3


}
