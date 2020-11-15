package com.arc.zero.service.system.impl;

import com.arc.core.config.annotations.Note;
import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.model.domain.system.SysResource;
import com.arc.utils.Assert;
import com.arc.zero.mapper.system.SysResourceMapper;
import com.arc.zero.service.system.SysResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
@Service
//@SuppressWarnings("unchecked")
public class SysResourceServiceImpl implements SysResourceService {

    public static final String SLASH = "/";

    private final static String basicErrorController = "basicErrorController";

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private SysResourceMapper resourceMapper;

    @Override
    public Long save(SysResource resource) {
        return resourceMapper.save(resource) == 1 ? resource.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return resourceMapper.delete(id);
    }

    @Override
    public int update(SysResource resource) {
        return resourceMapper.update(resource);
    }

    @Override
    public SysResource get(Long id) {
        return null;
    }

    @Override
    public Page<SysResource> page() {
        PageImpl page = new PageImpl(list());
        log.debug("结果={}", page);
        return page;
    }

    @Override
    public List<SysResource> list() {
        return resourceMapper.list();
    }

    @Override
    public int deleteIdIn(Set<Long> ids) {
        return resourceMapper.deleteIdIn(ids);
    }

    /**
     * 扫描系统controller的url 路径构造成资源集合，然后保存到数据库
     *
     * @return
     */
    @Override
    public Integer scanController() {
        //1、获取系统中的全部Controller& RestController
        //2、分析出有哪些url，组装出相应的元数据后 update or insert dataList to db
        Set<SysResource> resources = scanSysResourceByControllerAnnotation();
        log.info(" 扫描系统controller 得到的资源有{}个，Set<SysResource> resources={}", resources.size(), resources);
        return compareAndSave(resources);
    }

    /**
     * 扫描系统controller的url 路径构造成资源集合
     *
     * @return
     */
    private Set<SysResource> scanSysResourceByControllerAnnotation() {
        Set<SysResource> resources = new HashSet<>();

        //获取全部controller，即：获取所有的 贴有注解 Controller & RestController  bean   注意：RestController 是 Controller 的子集
        Map<String, Object> beansWithControllerAnnotation = getAllControllerFromIoc();
        if (beansWithControllerAnnotation == null || beansWithControllerAnnotation.size() == 0) {
            log.info("贴有注解 Controller & RestController  bean 的数量={}", resources.size());
            return resources;
        }
        Collection<Object> beans = beansWithControllerAnnotation.values();

        //获取每一个controller
        for (Object bean : beans) {

            Method[] methods = bean.getClass().getDeclaredMethods();
            log.debug("Controller是bean{}中的methods[]={}", bean, Arrays.toString(methods));

            //如果存在则获取到 类上的 那一段url，否则类上的uri地址给默认值 {"/"}
            String[] rootClassRequestMappingUris = getRequestMappingOnClass(bean);
            log.info(" 类{}上的注解RequestMapping中配置的那一段uris={}", bean.getClass(), Arrays.toString(rootClassRequestMappingUris));

            for (Method method : methods) {
                //判断 bean中的方法是否是有效资源【是API接口的】1、一个bean 中的一个方法 默认情况下转换为一条数据记录  2、可能返回空 3、可能写了uri数组的就会返回多个
                Set<SysResource> newResources = builtSysResource(method, rootClassRequestMappingUris);
                if (newResources != null && newResources.size() != 0) {
                    printCollection(newResources);
                    resources.addAll(newResources);
                }

            }

        }
        return resources;
    }

    /**
     * 构造资源，合适资源【controller 、 restController 的方法】 即可转换为 一条数据，注意 本系统的requestMapping 是一个uri一条数据
     * GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE  ,CONNECT   全部的http标准定义了9种, Spring  定义了8种，少CONNECT，在Controller中支持 5种【 GET,  POST, PUT, PATCH, DELETE,】
     *
     * @param method
     * @param rootClassRequestMappingUris
     * @return
     */
    private Set<SysResource> builtSysResource(Method method, String[] rootClassRequestMappingUris) {
        Class<?> clz = method.getDeclaringClass();

        RequestMethod[] requestMethods = null;
        String[] paths = null;

        //非代理类的逻辑

        if (method.isAnnotationPresent(PostMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.POST};
            paths = method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.DELETE};
            paths = method.getAnnotation(DeleteMapping.class).value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.PUT};
            paths = method.getAnnotation(PutMapping.class).value();
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.PATCH};
            paths = method.getAnnotation(PatchMapping.class).value();
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.GET};
            paths = method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            paths = method.getAnnotation(RequestMapping.class).value();
            requestMethods = method.getAnnotation(RequestMapping.class).method();
            if (requestMethods == null || requestMethods.length == 0) {
                requestMethods = getSupportRequestMethods();
            }
        }

        String fullName = clz.getName();
        if (fullName.contains("EnhancerBySpringCGLIB") || fullName.contains("$$")) {
            fullName = fullName.substring(0, fullName.indexOf("$$"));
            log.debug("类{}，的被代理类是={}", fullName, fullName);
        }

        //-------------------------  判断是 代理类处理逻辑 START
        boolean notMatchedFlag = true;
        if (requestMethods == null || paths == null) {
            log.info("类{}是代理的类,方法{}不是API方法", clz, method.getName());
            //如果是被代理的类 那么需要找被代理类，通过反射获取被代理类的方法，而后获取注解
            log.debug("简称={},方法{}", method.getName(), method);
            if (notMatchedFlag) {
                PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
                if (postMapping != null) {
                    paths = postMapping.value();
                    requestMethods = new RequestMethod[]{RequestMethod.POST};
                    notMatchedFlag = false;
                }
            }
            if (notMatchedFlag) {
                DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
                if (deleteMapping != null) {
                    paths = deleteMapping.value();
                    requestMethods = new RequestMethod[]{RequestMethod.DELETE};
                    notMatchedFlag = false;
                }
            }
            if (notMatchedFlag) {
                DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
                if (deleteMapping != null) {
                    paths = deleteMapping.value();
                    requestMethods = new RequestMethod[]{RequestMethod.DELETE};
                    notMatchedFlag = false;
                }
            }
            if (notMatchedFlag) {
                PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
                if (putMapping != null) {
                    paths = putMapping.value();
                    requestMethods = new RequestMethod[]{RequestMethod.PUT};
                    notMatchedFlag = false;
                }
            }
            if (notMatchedFlag) {
                PatchMapping patchMapping = AnnotationUtils.findAnnotation(method, PatchMapping.class);
                if (patchMapping != null) {
                    paths = patchMapping.value();
                    requestMethods = new RequestMethod[]{RequestMethod.PATCH};
                    notMatchedFlag = false;
                }
            }
            if (notMatchedFlag) {
                GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
                if (getMapping != null) {
                    requestMethods = new RequestMethod[]{RequestMethod.GET};
                    paths = getMapping.path();
                    notMatchedFlag = false;
                }
            }
            if (notMatchedFlag) {
                RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                if (requestMapping != null) {
                    requestMethods = getSupportRequestMethods();
                    paths = requestMapping.path();
                    notMatchedFlag = false;
                }
            }
            Annotation[] annotations = AnnotationUtils.getAnnotations(method);
        }
        //-------------------------  判断是 代理类处理逻辑 END


        //再次校验 方法上的uri
        log.debug("method是={}", method);
        log.debug("requestMethods结果={}", Arrays.toString(requestMethods));
        log.debug("前半段uris,rootClassRequestMappingUris ={}", Arrays.toString(rootClassRequestMappingUris));
        log.debug("后半段uris,paths ={}", Arrays.toString(paths));
        if (requestMethods == null || paths == null) {
            log.info("类{}中方法{}不是API方法，原因：未获取到有效注解【POST,DELETE, PUT, PATCH, GET,   & RequestMapping】", clz, method.getName());
            //throw new RuntimeException(clz.getSimpleName() + "不是API方法");
            //凡是没有任何注解的则不需要处理，因为不是接口资源
            return null;
        }

        //资源路径   path
        //请求方法   method（GET/POST/PUT/DELETE...）
        //优先级       int priority
        //资源名       resourceName

        Set<SysResource> resources = new HashSet<>();
        //资源的完整路径 前半段
        Assert.notEmpty(rootClassRequestMappingUris, ProjectCodeEnum.ILLEGAL_PARAMETER.getMsg());
        Assert.notEmpty(paths, ProjectCodeEnum.ILLEGAL_PARAMETER.getMsg());


        // 通过类上的 uri 与方法上的uri取并集
        for (String rootPath : rootClassRequestMappingUris) {
            for (String methodPath : paths) {
                SysResource resource = new SysResource();

                //请求方法   method（GET/POST/PUT/DELETE...）
                if (requestMethods == null | requestMethods.length < 1) {
                    requestMethods = RequestMethod.values();
                }

                StringBuilder requestMethodStringBuilder = new StringBuilder();
                for (RequestMethod var : requestMethods) {
                    requestMethodStringBuilder.append(var).append(",");
                }
                requestMethodStringBuilder.delete(requestMethodStringBuilder.length() - 1, requestMethodStringBuilder.length());
                resource.setMethod(requestMethodStringBuilder.toString());
                String checkedPath = verifyPath(rootPath + methodPath);
                log.debug("checkedPath={}", checkedPath);
                resource.setPath(checkedPath);


                //Note  自定义注解
                log.debug("########## 测试 ########## 是否有Note ={}", method.isAnnotationPresent(Note.class));
                Note note = method.getAnnotation(Note.class);
                String noteValue = null;
                String noteName = null;
                int notePriority = 0;
                if (note != null) {
                    noteValue = note.value();
                    noteName = note.name();
                    notePriority = note.priority();
                }

                //优先级       int priority
                //备注           note
                //资源名       resourceName
                resource.setPriority(notePriority);
                resource.setNote(noteValue);
                resource.setResourceName(noteName);
                log.debug("结果={}", resource);
                resources.add(resource);
            }
        }
        return resources;
    }

    /**
     * 注意， requestMethods 如果在注解元属性中没有指定，则SpringMVC是支持全部的种类的5种
     *
     * @return
     */
    private RequestMethod[] getSupportRequestMethods() {
        return new RequestMethod[]{RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.GET};
    }


    /**
     * 获取类上的注解的值，类上有贴注解RequestMapping,目的获取贴在类上的一段地址
     * 注意
     * 1、controller 可能没RequestMapping 注解
     * 2、url可能不统一，有的写了一个斜线，有的有两个，有的没有斜线，即：“/uri”,“/uri/”,“uri”
     *
     * @param bean
     * @return
     */
    private String[] getRequestMappingOnClass(Object bean) {
        Class<?> clz = bean.getClass();
        String[] pathOnClass = null;
        RequestMapping classAnnotationRequestMapping = clz.getAnnotation(RequestMapping.class);
        if (classAnnotationRequestMapping != null) {
            pathOnClass = classAnnotationRequestMapping.value();//预期 类上的地址  数组的[0] 可能 为null！！}
        }
        //如果目标类是代理类那么势必注解信息也将丢失，
        if (pathOnClass == null) {
            classAnnotationRequestMapping = AnnotationUtils.findAnnotation(clz, RequestMapping.class);
            if (classAnnotationRequestMapping != null) {
                pathOnClass = classAnnotationRequestMapping.value();//预期 类上的地址  数组的[0] 可能 为null！！}
            }
        }

        return verifyPath(pathOnClass);
    }


    /**
     * 校验uri合法性
     * {null, "", "/", "user", "user/", "/user/"}; ==》[/, /, /, /user, /user, /user]
     *
     * @param pathOnClass
     * @return
     */
    private static String[] verifyPath(String[] pathOnClass) {
        if (pathOnClass == null || pathOnClass.length == 0) {
            return new String[]{SLASH};
        }

        for (int i = 0; i < pathOnClass.length; i++) {
            //空的
            String path = pathOnClass[i];
            //非法数据检查并给出默认值，是null 或者 “” 空串
            if (path == null) {
                path = SLASH;
                log.warn("【有异常数据】controller的url路径值为null={},将会被改写为/", path);
            }

            path = path.replace("//", "/");

            if (path == null || path.trim().length() == 0 || SLASH.equals(path)) {
                pathOnClass[i] = SLASH;
                continue;
            }

            //开头检查，是不是/打头的
            if (!path.startsWith(SLASH)) {
                path = SLASH + path;
            }
            //结尾检查，是不是/结尾的
            if (path.endsWith(SLASH)) {
                path = path.substring(0, path.length() - 1);
            }
            pathOnClass[i] = path;
        }
        return pathOnClass;

    }


    /**
     * 校验中的斜线
     * 保证斜线开头，非斜线结尾，例外空串返回空串，
     * 去掉连续两个正斜线中的一个
     *
     * @param path
     * @return
     */
    public static String verifyPath(String path) {
        String defaultPath = null;
        if (path == null) return defaultPath;
        if ("/".equals(path)) return path;

        path = path.replace("//", "/");
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }


    /**
     * 扫描资源比对后入库
     * 后端只做数据的录入，别的（层级维护）不做
     *
     * @param resources
     * @return
     */

    //入库的 删除的
    // 更新怎么做，需要考虑一下，用path来比对？相同的保留，不同的先清空后插入
    //以url 为标准，
    // 相同的要么更新要么不动，1、若扫描得到的新集合其注释名称&url=数据库中的，则保持数据库中的数据不动 ； 2、若两者仅仅url一样，则更新
    //不同的 1、数据库中有新的集合中没有，则删掉数据库中的； 2、若数据库没有新集合中有则插入新数据
    //一样的 url的 数据表示接口没有变，去比较注释变换了没有
    //saved 集合去掉相同的
    //一样的 url的 数据表示接口没有变，去比较注释变换了没有
    //新的插入
    //删除的
    //saved 集合去掉相同的
    //                            //若两者仅仅url一样，则更新，只做updateBatch
//                        //url 不一样，说明接口不一样了
    //log.debug("---------------------相同的资源  id={}，url={},note={}", dbResource.getId(), dbResource.getPath(), dbResource.getNote());
    //log.debug("---------------------相同的资源 url={},note={}", resource.getPath(), resource.getNote());//
    private int compareAndSave(Set<SysResource> resources) {
        //清空数据

        if (resources == null || resources.size() == 0) {
            resourceMapper.truncate("t_sys_resource");
            return 1;
        }

        int delete = 0, save = 0, update = 0;

        List<SysResource> dbResources = resourceMapper.list();

        Map map = getCompareResultMap(new HashSet<>(dbResources), resources);
        Set<Long> deleteIdSet = null;
        Set<SysResource> insertList = null;
        Set<SysResource> updateList = null;

        Object deleteValue = map.get("delete");
        Object insertValue = map.get("insert");
        Object updateValue = map.get("update");

        if (deleteValue != null) {
            deleteIdSet = (Set<Long>) deleteValue;
        }
        if (insertValue != null) {
            insertList = (Set<SysResource>) insertValue;
        }
        if (updateValue != null) {
            updateList = (Set<SysResource>) updateValue;
        }

        delete = deleteBatch(deleteIdSet);
        save = saveBatch(insertList);
        update = updateBatch(updateList);

        log.debug("数据saveBatch={},update={},delete={}", save, update, delete);
        return save;
    }

    private int deleteBatch(Set<Long> deleteIdSet) {
        int delete = 0;
        if (deleteIdSet != null && deleteIdSet.size() > 0) {
            delete = resourceMapper.deleteIdIn(deleteIdSet);
        }
        return delete;
    }

    private int updateBatch(Set<SysResource> resources) {
        int update = 0;
        if (resources != null && resources.size() >= 0) {
            for (SysResource resource : resources) {
                update += resourceMapper.update(resource);
            }

        }
        return update;
    }

    private int saveBatch(Set<SysResource> resources) {
        if (resources == null || resources.size() == 0) {
            return 0;
        }
        return resourceMapper.saveBatch(resources);
    }


    /**
     * 返回值暂时用map简单做了封装，key设计待修正
     * map的key：insert/update/delete value对应三个list
     *
     * @param dbList
     * @param bList
     * @return
     */
    //1、只新增 2、不动 3、insert update delete 三个都有可能 4、全删
    public static Map getCompareResultMap(Set<SysResource> dbList, Set<SysResource> bList) {

        //结果缓存 最后返回
        Map<String, Object> map = new HashMap<>(4);
        Set<Long> deleteIdSet = new HashSet<>();
        //临时map缓存数据
        HashMap<String, SysResource> dbMap = new HashMap<>();
        Map<String, SysResource> receivedMap = new HashMap<>();
        Set<SysResource> updateList = new HashSet<>();
        Set<SysResource> insertList = new HashSet<>();

        //不动数据库，源数据为空/null 且 新数据为空/null，，数据不合法（正常是不会走该分支） 返回空，
        if ((dbList == null || dbList.size() == 0) && (bList == null || bList.size() == 0)) {
            return map;
        }
        //没有新数据，删除数据库中的全部数据
        if (bList == null || bList.size() == 0) {
            for (SysResource sysResource : dbList) {
                deleteIdSet.add(sysResource.getId());
            }
            map.put("delete", deleteIdSet);
            map.put("insert", insertList);
            map.put("update", updateList);
            return map;
        }

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
            map.put("delete", deleteIdSet);
            map.put("insert", insertList);
            map.put("update", updateList);
        }
        return map;
    }


    /**
     * 获取全部的controller
     * 注意 RestController 是 Controller 的子集
     * SpringBoot 默认会有一个controller bean id 叫做 basicErrorController
     *
     * @return
     */
    private Map<String, Object> getAllControllerFromIoc() {
        Map<String, Object> controllerBeansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        log.debug("获取到的全部的controller的bean数量={}，注意SpringBoot 默认会有一个controller bean id 叫做 basicErrorController", controllerBeansWithAnnotation.size());
        if (controllerBeansWithAnnotation != null) {
            Object remove = controllerBeansWithAnnotation.remove(basicErrorController);
            log.debug("有效 controller的数量={},(已排除basicErrorController={})", controllerBeansWithAnnotation.size(), remove);
            printCollection(controllerBeansWithAnnotation.values());
        }
        return controllerBeansWithAnnotation;
    }


    /**
     * 打印测试
     *
     * @param collection
     */
    private static void printCollection(Collection collection) {
        if (!log.isDebugEnabled()) {
            return;
        }

        if (collection == null) {
            log.debug("集合是{}", collection);
            return;
        }

        Iterator it = collection.iterator();
        while (it.hasNext()) {
            log.debug("集合中的元素是{}", it.next());
        }
    }


    public static void main(String[] args) {
        String[] pathOnClass = {null, "", "/", "user", "user/", "/user/"};
        String[] verifyPath = verifyPath(pathOnClass);
        log.debug("######################### {}", Arrays.toString(verifyPath));
    }
}


//                String wholeUrl = path1[0].toString() + path2[0].toString();//预计是完整路径
//                if (method.isAnnotationPresent(WebSysResource.class)) {
//                    WebSysResource annotation = method.getAnnotation(WebSysResource.class);
//                    if (annotation != null) {
//                        String realmName = annotation.resourceName();
//                        String note = annotation.note();
//                        Integer priority = annotation.priority();
//                        SysResourceType type = annotation.type();
//                        ParentKey parentKey = annotation.parentKey();//指明父级是什么
//                        ParentKey key = annotation.key();
//                        RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();//被请求的方法是何种类型 请求方法的 数组 7种
//
//                        String packageName = method.getDeclaringClass().getPackage().getName();//预计是 packageName
//                        String[] path1 = method.getDeclaringClass().getAnnotation(RequestMapping.class).value();//预期 类上的地址  数组的[0] 可能 为null！！
//                        String[] path2 = method.getAnnotation(RequestMapping.class).value();//路径 但是怎么获取类上贴的那部分url呢？
//
//                        String wholeUrl = path1[0].toString() + path2[0].toString();//预计是完整路径
//                        if (!wholeUrl.startsWith("/")) {
//                            wholeUrl = "/" + wholeUrl;//补全url
//                        }
//
//                    }
//                }


//boolean getMappingAnnotation =method.isAnnotationPresent(GetMapping.class) ;
//                boolean postMappingAnnotation = method.isAnnotationPresent(PostMapping.class);
//                boolean putMappingAnnotation = method.isAnnotationPresent(PutMapping.class);
//                boolean deleteMappingAnnotation = method.isAnnotationPresent(DeleteMapping.class);
//                boolean patchMappingAnnotation = method.isAnnotationPresent(PatchMapping.class);
//
//                System.out.println("? GetMapping " + method.isAnnotationPresent(GetMapping.class));
//        System.out.println("? PostMapping " + method.isAnnotationPresent(PostMapping.class));
//        System.out.println("? DeleteMapping " + method.isAnnotationPresent(DeleteMapping.class));


//        Map<String, Object> restControllerBeansWithAnnotation = applicationContext.getBeansWithAnnotation(RestController.class);
//        Map<String, Object> beansWithAnnotation = new HashMap<>();
//        System.out.println("Controller" + controllerBeansWithAnnotation.size());
//        System.out.println("RestController " + restControllerBeansWithAnnotation.size());
//        log.debug("Controller ={},RestController ={}", controllerBeansWithAnnotation.size(), restControllerBeansWithAnnotation.size());
//        beansWithAnnotation.putAll(controllerBeansWithAnnotation);
//        beansWithAnnotation.putAll(restControllerBeansWithAnnotation);


//=================================================================

//        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
//
//
//        String name = requestMapping.name();
//        String[] value = requestMapping.value();
//        String[] path = requestMapping.path();
//
//        String[] params = requestMapping.params();
//        String[] headers = requestMapping.headers();
//
//        String[] consumes = requestMapping.consumes();
//        String[] produces = requestMapping.produces();
//        RequestMethod[] httpMethod = requestMapping.method();
//
//        log.debug("1结果name={}", name);
//        log.debug("2结果value={}", Arrays.toString(value));
//        log.debug("3结果path={}", Arrays.toString(path));
//        log.debug("4结果 params={}", Arrays.toString(params));
//        log.debug("5结果 headers={}", Arrays.toString(headers));
//        log.debug("6结果 consumes={}", Arrays.toString(consumes));
//        log.debug("7结果 produces={}", Arrays.toString(produces));
//        log.debug("8结果 httpMethod={}", Arrays.toString(httpMethod));

//        log.debug("------------------------------------- 查看 GetMapping 中的属性 -------------------------------------------");
//
//        GetMapping annotation = method.getAnnotation(GetMapping.class);
//        String  name = annotation.name();
//        String[]       value = annotation.value();
//        String[]  path = annotation.path();
//
//        String[]   consumes = annotation.consumes();
//        String[]    produces = annotation.produces();
//        String[]    headers = annotation.headers();
//        String[] params = annotation.params();
//
//        log.debug("1结果name={}", name);
//        log.debug("2结果value={}", Arrays.toString(value));
//        log.debug("3结果path={}", Arrays.toString(path));
//        log.debug("4结果 params={}", Arrays.toString(params));
//        log.debug("5结果 headers={}", Arrays.toString(headers));
//        log.debug("6结果 consumes={}", Arrays.toString(consumes));
//        log.debug("7结果 produces={}", Arrays.toString(produces));
//        log.debug("-------------------------------------------------------------------------------------");

//            for (Method method : methods) {
//
//                //获取父类的地址
//                String[] classPath = getClassPath(method);
//                String pathOnClass = "";
//                if (classPath != null) {
//                    pathOnClass = classPath[0];
//                }
//                System.out.println("类" + bean.getClass().getSimpleName() + "上标记的地址 " + pathOnClass);
//                String[] methodPaths = getMethodPath(method);
////                if (methodPaths != null && methodPaths.length > 0) {
////
////                    pathOnMethod = methodPaths[0];
////                }
//
//
//                //资源的完整路径 前半段
//                StringBuilder wholePath = new StringBuilder();
//                wholePath.append(checkPath(pathOnClass));
//
//                String pathOnMethod = "";
//                SysResource resource = null;
//
//                log.debug("是{}类的方法{}", method.getDeclaringClass().getSimpleName(), method.getName());
//                System.out.println("? PostMapping " + method.isAnnotationPresent(PostMapping.class));
//                System.out.println("? DeleteMapping " + method.isAnnotationPresent(DeleteMapping.class));
//                System.out.println("? PutMapping " + method.isAnnotationPresent(PutMapping.class));
//                System.out.println("? GetMapping " + method.isAnnotationPresent(GetMapping.class));
//                System.out.println("? ????????? " + method.isAnnotationPresent(Note.class));
//                System.out.println("? ????????? getDeclaredAnnotations" + Arrays.toString(method.getDeclaredAnnotations()));
//                System.out.println("? ????????? getAnnotations" + Arrays.toString(method.getAnnotations()));
//
//
//                //处理方法
//                if (method.isAnnotationPresent(RequestMapping.class)) {
//                    String[] value = method.getAnnotation(RequestMapping.class).value();
//                    System.out.println(Arrays.toString(value));
//                    if (value != null && value.length > 0) {
//                        pathOnMethod = value[0];
//                    }
//                    RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();
//
//
//                    System.out.println(Arrays.toString(requestMethods));
//                    System.out.println();
//                    StringBuilder requestMethodStringBuilder = new StringBuilder();
//                    //第一种可能 如果开发者只在方法上贴了RequestMapping 而不指定 method，则表示8中方法都可以访问的，第二种可能  method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST}
//                    if (requestMethods == null | requestMethods.length < 1) {
//                        requestMethods = RequestMethod.values();
//                    }
//                    for (RequestMethod var : requestMethods) {
//                        System.out.println(var);
//                        requestMethodStringBuilder.append(var).append(",");
//                    }
//                    resource = new SysResource();
//                    log.debug("------------------>类 {} 上标记的地址 {} 方法上标记的地址 {}\nHTTP请求方法为 {}  ", bean.getClass().getSimpleName(), pathOnClass, pathOnMethod, requestMethodStringBuilder.toString());
//
//                    resource.setMethod(requestMethodStringBuilder.toString());
//
//                } else if (method.isAnnotationPresent(GetMapping.class)) {
//                    String[] value = method.getAnnotation(GetMapping.class).value();
//                    if (value != null && value.length > 0) {
//                        pathOnMethod = value[0];
//                        resource = new SysResource();
//                        resource.setMethod(RequestMethod.GET.toString());
//                        System.out.println(method.getAnnotation(GetMapping.class).name());
//                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).value()));
//                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).params()));
//                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).path()));
//                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).produces()));
//                    }
//
//                } else if (method.isAnnotationPresent(PostMapping.class)) {
//                    String[] value = method.getAnnotation(PostMapping.class).value();
//                    if (value != null && value.length > 0) {
//                        pathOnMethod = value[0];
//                        resource = new SysResource();
//                        resource.setMethod(RequestMethod.POST.toString());
//                    }
//
//                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
//                    String[] value = method.getAnnotation(DeleteMapping.class).value();
//                    if (value != null && value.length > 0) {
//                        pathOnMethod = value[0];
//                        resource = new SysResource();
//                        resource.setMethod(RequestMethod.DELETE.toString());
//                    }
//
//                } else if (method.isAnnotationPresent(PutMapping.class)) {
//                    String[] value = method.getAnnotation(PutMapping.class).value();
//                    if (value != null && value.length > 0) {
//                        pathOnMethod = value[0];
//                        resource = new SysResource();
//
//                        resource.setMethod(RequestMethod.PUT.toString());
//                    }
//                } else {
//                    log.debug("------------------>其他方法类{} 上标记的地址 {} ", bean.getClass().getSimpleName(), pathOnClass);
//                }
//
//                //path1+path2
//                wholePath.append(checkPath(pathOnMethod));
//                if (resource != null) {
//                    //resourceName&note
//                    if (method.isAnnotationPresent(Note.class)) {
//                        log.debug("类 {} 上标记的地址 {} ", bean.getClass().getSimpleName(), pathOnClass);
//
//                        Note note = method.getAnnotation(Note.class);
//                        System.out.println(null == note);
//                        resource.setResourceName(method.getAnnotation(Note.class).value());
//                        resource.setPriority(note.priority());
//                    }
//
//                    if (wholePath.toString().trim().length() > 0) {
//                        resource.setPath(wholePath.toString());
//                        resources.add(resource);
//                    }
//                }
//
//            }
