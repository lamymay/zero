package com.arc.zero.service.system.impl;

import com.arc.core.model.domain.system.RoleResource;
import com.arc.core.model.domain.system.SysRole;
import com.arc.core.model.request.system.role.RoleResourceRequest;
import com.arc.core.model.request.system.role.SysRoleRequest;
import com.arc.utils.Assert;
import com.arc.utils.bean.BeanUtil;
import com.arc.zero.mapper.system.SysRoleMapper;
import com.arc.zero.service.system.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper roleMapper;

//    @Autowired    private SysResourceService resourceService;

    @Override
    public Long save(SysRole role) {
        return roleMapper.save(role) == 1 ? role.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return roleMapper.delete(id);
    }

    @Override
    public int update(SysRole role) {
        return roleMapper.update(role);
    }

    @Override
    public SysRole get(Long id) {
        return roleMapper.get(id);
    }

    @Override
    public List<SysRole> listBySysRoleRequest(SysRoleRequest roleRequest) {
        return roleMapper.list();
    }

    @Override
    public List<SysRole> listPage(SysRoleRequest roleRequest) {
        return list();
    }

    @Override
    public Integer saveBatch(List<SysRole> roles) {
        return roleMapper.batchSave(roles);
    }

    @Override
    public Integer updateBatch(List<SysRole> roles) {
        return null;
    }

    @Override
    public List<SysRole> list() {
        return roleMapper.list();
    }

    /**
     * 维护关系表role_resources
     *
     * @param id
     * @param resourceIds
     * @return
     */
    private int compareAndSaveRoleResource(Long id, Set<Long> resourceIds) {
        Assert.notNull(id);
        //清空数据
        roleMapper.deleteAllRoleResource(id);
        int updateRoleResource = 0;
        if (resourceIds != null && resourceIds.size() > 0) {
            Set<RoleResource> roleResources = new HashSet<>();
            for (Long resourceId : resourceIds) {
                roleResources.add((new RoleResource(id, resourceId)));
            }
            updateRoleResource = roleMapper.saveBatchRoleResource(roleResources);
        }
        return updateRoleResource;
    }

    //            int saveRelations=roleMapper.saveRoleResource(role.getId(), role.getResourceIds());


//    private int compareAndSave(Set<SysResource> resources) {
//
//        if (resources == null || resources.size() == 0) {
//            roleMapper.truncate("t_sys_resource");
//            return 1;
//        }
//
//        int delete = 0, save = 0, update = 0;
//
//        List<SysResource> dbResources = roleMapper.list();
//
//        Map map = getCompareResultMap(new HashSet<>(dbResources), resources);
//
//        System.out.println(map);
//        Set<Long> deleteIdSet = null;
//        Set<SysResource> insertList = null;
//        Set<SysResource> updateList = null;
//
//        Object deleteValue = map.get("delete");
//        Object insertValue = map.get("insert");
//        Object updateValue = map.get("update");
//
//        if (deleteValue != null) {
//            deleteIdSet = (Set<Long>) deleteValue;
//        }
//        if (insertValue != null) {
//            insertList = (Set<SysResource>) insertValue;
//        }
//        if (updateValue != null) {
//            updateList = (Set<SysResource>) updateValue;
//        }
//
//        delete = deleteBatch(deleteIdSet);
//        save = saveBatch(insertList);
//        update = updateBatch(updateList);
//
//        log.debug("数据saveBatch={},update={},delete={}", save, update, delete);
//        return save;
//    }
//
//    private int deleteBatch(Set<Long> deleteIdSet) {
//        int delete = 0;
//        if (deleteIdSet != null && deleteIdSet.size() > 0) {
//            delete = roleMapper.deleteIdIn(deleteIdSet);
//        }
//        return delete;
//    }
//
//    private int updateBatch(Set<SysResource> resources) {
//        int update = 0;
//        if (resources != null && resources.size() >= 0) {
//            for (SysResource resource : resources) {
//                update += roleMapper.update(resource);
//            }
//
//        }
//        return update;
//    }
//
//    private int saveBatch(Set<SysResource> resources) {
//        int saveBatch = 0;
//        if (resources != null && resources.size() > 0) {
//            saveBatch = roleMapper.saveBatch(resources);
//        }
//        return saveBatch;
//    }


    @Override
    public int updateRoleResource(RoleResourceRequest roleResourceRequest) {
        Assert.notNull(roleResourceRequest, "错误，向不存在的角色新增资源！");
        int update = update(BeanUtil.copyBean(roleResourceRequest, SysRole.class));
        if (update == 1) {
            //操作成功后更新关系表
            return compareAndSaveRoleResource(roleResourceRequest.getRoleId(), roleResourceRequest.getResourceIds());
        }
        return 0;
    }

    @Override
    public int saveRoleResource(RoleResourceRequest roleResourceRequest) {
        Assert.notNull(roleResourceRequest, "错误，向不存在的角色新增资源！");
        Assert.notNull(roleResourceRequest.getRoleId(), "错误，向不存在的角色新增资源！");
        Assert.notNull(roleResourceRequest.getResourceIds(), "错误，向角色配置空的资源！");


        Long roleId = roleResourceRequest.getRoleId();

        Set<RoleResource> roleResourceSet = new HashSet<>(16);
        for (Long resourceId : roleResourceRequest.getResourceIds()) {
            roleResourceSet.add(new RoleResource(roleId, resourceId));
        }

        return roleMapper.saveBatchRoleResource(roleResourceSet);
    }
}


