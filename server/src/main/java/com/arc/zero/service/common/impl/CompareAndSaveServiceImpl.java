package com.arc.zero.service.common.impl;

import com.arc.core.model.domain.app.AppContact;
import com.arc.core.model.domain.system.SysResource;
import com.arc.zero.mapper.ddl.DDLMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author 叶超
 * @since 2020/2/29 11:26
 */
@Slf4j
@Service
@Deprecated
public class CompareAndSaveServiceImpl {

    @Resource
    private DDLMapper ddlMapper;

//    /**
//     * 比较两个集合
//     *
//     * @param newResources newResources
//     * @param dbResources  dbResources
//     * @return CompareAndSaveDto
//     */
//    private CompareAndSaveDto compareAndSave(Collection<AppContact> newResources, Collection<AppContact> dbResources) {

//        if (newResources == null || dbResources.size() == 0 || newResources == null || dbResources.size() == 0) {
//            return null;
//        }
//        Map map = getCompareResultMap(newResources, dbResources);
//        Object deleteValue = map.get("delete");
//        Object insertValue = map.get("insert");
//        Object updateValue = map.get("update");
//
//        int save = 0, delete = 0, update = 0;

//        if (insertValue != null) {
//            save = saveBatch((Set<SysResource>) insertValue);
//        }
//
//        if (deleteValue != null) {
//            delete = deleteBatch((Set<Long>) deleteValue);
//        }
//
//        if (updateValue != null) {
//            update = updateBatch((Set<SysResource>) updateValue);
//        }

//        log.debug("数据saveBatch={},delete={},update={}", save, delete, update);
//        return new CompareAndSaveDto(save, delete, update);
//    }

    /**
     * 返回值暂时用map简单做了封装，key设计待修正
     * map的key：insert/update/delete value对应三个list
     *
     * @param newRows newRows
     * @param dbRows dbRows
     * @return
     */
    //1、只新增 2、不动 3、insert update delete 三个都有可能 4、全删
    public static Map getCompareResultMap(Collection<AppContact> newRows, Collection<AppContact> dbRows) {


        Set<AppContact> leftSet = new HashSet<>();
        Set<AppContact> unionSet = new HashSet<>();
        Set<AppContact> rightSet = new HashSet<>();

        if (newRows == null || newRows.size() == 0) {

        }


        //结果缓存 最后返回
        Map<String, Object> map = new HashMap<>(12);

        //临时map缓存数据
        HashMap<String, SysResource> dbMap = new HashMap<>();
        Map<String, SysResource> receivedMap = new HashMap<>();


        Set<Long> deleteIdSet = new HashSet<>();
        Set<SysResource> updateList = new HashSet<>();
        Set<SysResource> insertList = new HashSet<>();

//        //不动数据库，源数据为空/null 且 新数据为空/null，，数据不合法（正常是不会走该分支） 返回空，
//        if ((dbList == null || dbList.size() == 0) && (bList == null || bList.size() == 0)) {
//            return map;
//        }
//        //没有新数据，删除数据库中的全部数据
//        if (bList == null || bList.size() == 0) {
//            for (SysResource sysResource : dbList) {
//                deleteIdSet.add(sysResource.getId());
//            }
//            map.put("delete", deleteIdSet);
//            map.put("insert", insertList);
//            map.put("update", updateList);
//            return map;
//        }
//
//        if (dbList == null || dbList.size() == 0) {
//            map.put("insert", bList);
//            return map;
//        }
//
//        //两个都非空
//        if (dbList != null && dbList.size() > 0 && bList != null && bList.size() > 0) {
//            for (SysResource resource : bList) {
//                receivedMap.put(resource.getPath(), resource);
//            }
//
//            //筛选出需要删除的 和 需要更新的
//            for (SysResource dbResource : dbList) {
//                dbMap.put(dbResource.getPath(), dbResource);
//
//                SysResource resourceInReceived = receivedMap.get(dbResource.getPath());
//                if (resourceInReceived != null) {
//                    //相同的更新
//                    resourceInReceived.setId(dbResource.getId());
//                    //updateMap.put(resourceInReceived.getPath(), resourceInReceived);
//                    updateList.add(resourceInReceived);
//                } else {
//                    //要删除的
//                    deleteIdSet.add(dbResource.getId());
//                }
//
//            }
//
//            //筛选出需要更新的
//            if (updateList != null) {
//                for (SysResource updateResource : updateList) {
//
//                    String key = updateResource.getPath();
//                    SysResource resourceInReceived = receivedMap.get(key);
//
//                    //能拿出来表示是需要更新的
//                    if (resourceInReceived != null) {
//                        receivedMap.remove(key);
//                    }
//                }
//            }
//            //找到需要插入的
//            insertList.addAll(receivedMap.values());
//            map.put("delete", deleteIdSet);
//            map.put("insert", insertList);
//            map.put("update", updateList);
//        }
        return map;
    }

    //交集
    public static void testRetainAll() {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("a");
        set1.add("b");
        set1.add("c");

        set2.add("c");
        set2.add("d");
        set2.add("e");
        //交集
        set1.retainAll(set2);
        System.out.println("交集是 " + set1);  //交集是 [c]
    }

    //并集
    public static void testUnionSet() {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("a");
        set1.add("b");
        set1.add("c");

        set2.add("b");
        set2.add("c");
        set2.add("d");

        set1.addAll(set2);
        System.out.println("并集是" + set1); //并集是[a, b, c, d]
    }

    //差集测试
    public static void testDifferenceSet() {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("a");
        set1.add("b");
        set1.add("c");
        set1.add("d");

        set2.add("c");
        set2.add("d");
        set2.add("e");
        set2.add("f");

        set1.removeAll(set2);
        System.out.println("差集是 " + set1); //差集是 [a, b]
    }
}
