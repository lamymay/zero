package com.arc.zero.test.list;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;

/**
 * @author may
 * @since 2019/7/23 12:01
 */
public class ListUtil {


    public static void main(String[] args) {
        ArrayList<SysResource> aList = new ArrayList<>();
        ArrayList<SysResource> bList = new ArrayList<>();

        aList.add(new SysResource(1L, "主页", "/index"));
        aList.add(new SysResource(2L, "setting", "/setting"));
        aList.add(new SysResource(3L, "user", "/user"));
        aList.add(new SysResource(4L, "test", "/test"));


        bList.add(new SysResource("user_v2", "/user"));
        bList.add(new SysResource("test_v2", "/test"));
        bList.add(new SysResource("file", "/file"));
        bList.add(new SysResource("img", "/img"));
        Map map = ListUtil.testList(aList, bList);
        System.out.println(map);
//        Set<Long> removeIds = (Set<Long>) map.get("delete");
//        printList(removeIds);
    }


    /**
     * 返回值暂时用map简单做了封装，key设计待修正
     * map的key：insert/update/delete value对应三个list
     *
     * @param dbList 从db中获取的list
     * @param bList  目标list
     * @return Map
     */
    public static Map testList(List<SysResource> dbList, List<SysResource> bList) {

        //1、只新增 2、不动 3、insert update delete 三个都有可能 4、全删

        //结果缓存 最后返回
        Map<String, Object> map = new HashMap<>(4);

        //不动数据库，源数据为空/null 且 新数据为空/null，，数据不合法（正常是不会走该分支） 返回空，
        if ((dbList == null || dbList.size() == 0) && (bList == null || bList.size() == 0)) {
            return map;
        }

        Set<Long> deleteIdSet = new HashSet<>();
        //没有新数据，删除数据库中的全部数据
        if (bList == null || bList.size() == 0) {
            for (SysResource sysResource : dbList) {
                deleteIdSet.add(sysResource.getId());
            }
            map.put("delete", deleteIdSet);
            return map;
        }

        //临时map缓存数据
        HashMap<String, SysResource> dbMap = new HashMap<>();
        Map<String, SysResource> receivedMap = new HashMap<>();

        Set<SysResource> updateList = new HashSet<>();
        Set<SysResource> insertList = new HashSet<>();


        if (dbList == null || dbList.size() == 0) {
            map.put("insert", bList);
            return map;
        }


        //两个都非空
        if (dbList != null && dbList.size() > 0 && bList != null && bList.size() > 0) {
            for (SysResource resource : bList) {
                receivedMap.put(resource.getPath(), resource);
            }

            //筛选出需要删除的 和 需要更新的
            for (SysResource dbResource : dbList) {
                dbMap.put(dbResource.getPath(), dbResource);

                SysResource resourceInReceived = receivedMap.get(dbResource.getPath());
                if (resourceInReceived != null) {
                    //相同的更新
                    resourceInReceived.setId(dbResource.getId());
                    //updateMap.put(resourceInReceived.getPath(), resourceInReceived);
                    updateList.add(resourceInReceived);
                } else {
                    //要删除的
                    deleteIdSet.add(dbResource.getId());
                }

            }

            //筛选出需要更新的
            if (updateList != null) {
                for (SysResource updateResource : updateList) {

                    String key = updateResource.getPath();
                    SysResource resourceInReceived = receivedMap.get(key);

                    //能拿出来表示是需要更新的
                    if (resourceInReceived != null) {
                        receivedMap.remove(key);
                    }
                }
            }
            //找到需要插入的
            insertList.addAll(receivedMap.values());
        }

        map.put("delete", deleteIdSet);
        map.put("insert", insertList);
        map.put("update", updateList);

        printList(deleteIdSet);
        return map;
    }


    private static void printList(Collection list) {
        if (list != null) {
            System.out.println("方式一");
            for (Object item : list) {
                SysResource resource = (SysResource) item;
                System.out.println(resource.getId() + " " + resource.getResourceName() + " " + resource.getPath());
            }
            System.out.println("------------------------");
            System.out.println("方式二");
            Iterator it = list.iterator();
            while (it.hasNext()) {
                SysResource resource = (SysResource) it.next();

                System.out.println(resource.getId() + " " + resource.getResourceName() + " " + resource.getPath());

            }

        } else {
            System.out.println("null");
        }
    }


    /**
     * 比较一个list集合里是否有重复的值(如果有删除重复值)
     */

    //最优法
    public static List<Object> removeRepeat(List<Object> list) {
        Set<Object> set = new HashSet<Object>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
        return list;
    }

    //一般法
    public static List<Object> removeRepeat2(List<Object> list) {
        List<Object> noRepeatList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            boolean isok = true;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).equals(list.get(j))) {
                    isok = false;
                }
            }
            if (isok) {
                noRepeatList.add(list.get(i));
            }
        }
        return noRepeatList;
    }


}


@Data
class SysResource {

    private static final long serialVersionUID = 1L;

    private Long id;// 主键

    private String parentId;// 上级节点ID

    private String resourceName;// 资源名

    private String path;// 资源路径

    private String method;// 请求方法（GET，POST...）

    private int state;//状态

    private Integer priority;// 优先级

    private String note;// 备注

    private String type;// 资源类型

    private Date createDate;// 创建时间

    private Date updateDate;// 修改时间

    public SysResource() {
    }

    public SysResource(long id, String name, String path) {
        this.id = id;
        this.resourceName = name;
        this.path = path;
    }

    public SysResource(String name, String path) {
        this.resourceName = name;
        this.path = path;
    }


    public void testRequestMethod() {
        RequestMethod[] values = RequestMethod.values();
        System.out.println(Arrays.toString(values));
        System.out.println("GET,HEAD,POST,PUT,PATCH,DELETE,OPTIONS,TRACE".length());

    }
}
