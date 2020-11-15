package com.arc.zero.test.file;

import com.arc.core.config.annotations.Note;
import com.arc.core.model.domain.system.SysResource;
import com.arc.zero.mapper.system.SysResourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 叶超
 * @since 2019/1/30 17:33
 */
@Slf4j
//@SuppressWarnings("unchecked")
public class Temp {


    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private SysResourceMapper resourceMapper;


    private Set<SysResource> sacnController() {

        //        List<SysResource> resourceList = new ArrayList<>();

        //获取所有的 贴有注解 Controller & RestController  bean   注意：RestController 是 Controller 的子集
        Map<String, Object> beansWithControllerAnnotation = getAllControllerFromIoc();
        Collection<Object> beans = beansWithControllerAnnotation.values();


        Set<SysResource> resources = new HashSet<>();
        for (Object bean : beans) {

            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {

                //获取父类的地址
                String[] classPath = getClassPath(method);
                String pathOnClass = "";
                if (classPath != null) {
                    pathOnClass = classPath[0];
                }
                System.out.println("类" + bean.getClass().getSimpleName() + "上标记的地址 " + pathOnClass);
                String[] methodPaths = getMethodPath(method);
//                if (methodPaths != null && methodPaths.length > 0) {
//
//                    pathOnMethod = methodPaths[0];
//                }


                //资源的完整路径 前半段
                StringBuilder wholePath = new StringBuilder();
                wholePath.append(checkPath(pathOnClass));

                String pathOnMethod = "";
                SysResource resource = null;

                log.debug("是{}类的方法{}", method.getDeclaringClass().getSimpleName(), method.getName());
                System.out.println("? PostMapping " + method.isAnnotationPresent(PostMapping.class));
                System.out.println("? DeleteMapping " + method.isAnnotationPresent(DeleteMapping.class));
                System.out.println("? PutMapping " + method.isAnnotationPresent(PutMapping.class));
                System.out.println("? GetMapping " + method.isAnnotationPresent(GetMapping.class));
                System.out.println("? ????????? " + method.isAnnotationPresent(Note.class));
                System.out.println("? ????????? getDeclaredAnnotations" + Arrays.toString(method.getDeclaredAnnotations()));
                System.out.println("? ????????? getAnnotations" + Arrays.toString(method.getAnnotations()));


                //处理方法
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    String[] value = method.getAnnotation(RequestMapping.class).value();
                    System.out.println(Arrays.toString(value));
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                    }
                    RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();


                    System.out.println(Arrays.toString(requestMethods));
                    System.out.println();
                    StringBuilder requestMethodStringBuilder = new StringBuilder();
                    //第一种可能 如果开发者只在方法上贴了RequestMapping 而不指定 method，则表示8中方法都可以访问的，第二种可能  method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST}
                    if (requestMethods == null | requestMethods.length < 1) {
                        requestMethods = RequestMethod.values();
                    }
                    for (RequestMethod var : requestMethods) {
                        System.out.println(var);
                        requestMethodStringBuilder.append(var).append(",");
                    }
                    resource.setMethod(requestMethodStringBuilder.toString());

                    resource = new SysResource();
                    log.debug("------------------>类 {} 上标记的地址 {} 方法上标记的地址 {}\nHTTP请求方法为 {}  ", bean.getClass().getSimpleName(), pathOnClass, pathOnMethod, requestMethodStringBuilder.toString());

                    resource.setMethod(requestMethodStringBuilder.toString());

                } else if (method.isAnnotationPresent(GetMapping.class)) {
                    String[] value = method.getAnnotation(GetMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.GET.toString());
                        System.out.println(method.getAnnotation(GetMapping.class).name());
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).value()));
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).params()));
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).path()));
                        System.out.println(Arrays.toString(method.getAnnotation(GetMapping.class).produces()));
                    }

                } else if (method.isAnnotationPresent(PostMapping.class)) {
                    String[] value = method.getAnnotation(PostMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.POST.toString());
                    }

                } else if (method.isAnnotationPresent(DeleteMapping.class)) {
                    String[] value = method.getAnnotation(DeleteMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.DELETE.toString());
                    }

                } else if (method.isAnnotationPresent(PutMapping.class)) {
                    String[] value = method.getAnnotation(PutMapping.class).value();
                    if (value != null && value.length > 0) {
                        pathOnMethod = value[0];
                        resource = new SysResource();
                        resource.setMethod(RequestMethod.PUT.toString());
                    }
                } else {
                    log.debug("------------------>其他方法类{} 上标记的地址 {} ", bean.getClass().getSimpleName(), pathOnClass);
                }

                //path1+path2
                wholePath.append(checkPath(pathOnMethod));
                if (resource != null) {
                    //resourceName&note
                    if (method.isAnnotationPresent(Note.class)) {
                        log.debug("类 {} 上标记的地址 {} ", bean.getClass().getSimpleName(), pathOnClass);

                        Note note = method.getAnnotation(Note.class);
                        System.out.println(null == note);
                        resource.setResourceName(method.getAnnotation(Note.class).value());
                        resource.setPriority(note.priority());
                    }

                    if (wholePath.toString().trim().length() > 0) {
                        resource.setPath(wholePath.toString());
                        resources.add(resource);
                    }
                }


            }
        }
        System.out.println(resources.size());
        System.out.println(resources.size());
        System.out.println(resources.size());
        return resources;
    }


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

    /**
     * 扫描资源比对后入库
     * 后端只做数据的录入，别的（层级维护）不做
     *
     * @param resources resources
     * @return 成功数量
     */
    private int compareAndSave(Set<SysResource> resources) {
        //清空数据

        if (resources == null || resources.size() == 0) {
            resourceMapper.truncate("t_sys_resource");
            return 1;
        }

        int delete = 0, save = 0, update = 0;

        List<SysResource> dbResources = resourceMapper.list();

        Map map = getCompareResultMap(new HashSet<>(dbResources), resources);

        System.out.println(map);
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
        int saveBatch = 0;
        if (resources != null && resources.size() > 0) {
            saveBatch = resourceMapper.saveBatch(resources);
        }
        return saveBatch;
    }


    //========================  for log ========================

    /**
     * 处理controller方法的类型
     *
     * @param method Method
     * @return HttpMethod[]
     */
    private RequestMethod[] getHttpMethods(Method method) {
        //返回值:一个表示底层成员声明类的对象


        // 测试 method 方法的方法
        Annotation[] annotations = method.getAnnotations();
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();

        Class<?> className = method.getDeclaringClass();
        String methodName = method.getName();
        int parameterCount = method.getParameterCount();

        AnnotatedType annotatedReturnType = method.getAnnotatedReturnType();
        Class<?>[] exceptionTypes = method.getExceptionTypes();
        String toString = method.toString();

        log.debug("返回一个表示底层成员声明类的对象={}", className);

        log.debug("以字符串形式返回此方法的名称={}", methodName);
        log.debug("返回描述此方法的字符串={}", toString);
        log.debug("annotatedReturnType={}", annotatedReturnType);
        log.debug("parameterCount={}", parameterCount);
        log.debug("annotations={}", Arrays.toString(annotations));
        log.debug("exceptionTypes={}", Arrays.toString(exceptionTypes));
        log.debug("元素上直接存在的所有注释={}", Arrays.toString(declaredAnnotations));
        log.debug("返回一个数组数组，它们以声明顺序表示由此Method对象表示的方法的形式参数的注释={}", Arrays.toString(parameterAnnotations));


        log.debug("------------------------------------------- 判断Controller接口支持何种Http Method ------------------------------------------");

        log.debug("?是否有PostMapping={}", method.isAnnotationPresent(PostMapping.class));
        log.debug("?是否有DeleteMapping={}", method.isAnnotationPresent(DeleteMapping.class));
        log.debug("?是否有PutMapping={}", method.isAnnotationPresent(PutMapping.class));

        log.debug("?是否有GetMapping={}", method.isAnnotationPresent(GetMapping.class));
        log.debug("?是否有PatchMapping={}", method.isAnnotationPresent(PatchMapping.class));
        log.debug("?是否有 RequestMapping={},", method.isAnnotationPresent(RequestMapping.class));


        //	GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE  ,CONNECT   全部的http标准定义了9种, Spring  定义了8种，少CONNECT，在Controller中支持 5种【 GET,  POST, PUT, PATCH, DELETE,】

        RequestMethod[] requestMethods = null;

        if (method.isAnnotationPresent(PostMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.POST};
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.DELETE};
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.PUT};
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.GET};
        } else if (method.isAnnotationPresent(PatchMapping.class)) {
            requestMethods = new RequestMethod[]{RequestMethod.PATCH};
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            log.debug("------------------------------------- 查看 RequestMapping中的属性 -------------------------------------------");
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            String name = requestMapping.name();
            String[] value = requestMapping.value();
            String[] path = requestMapping.path();

            String[] params = requestMapping.params();
            String[] headers = requestMapping.headers();

            String[] consumes = requestMapping.consumes();
            String[] produces = requestMapping.produces();
            requestMethods = requestMapping.method();
            if (requestMethods == null || requestMethods.length == 0) {
                //注意， requestMethods 如果在注解元属性中没有指定，则SpringMVC是支持全部的种类的5种
                requestMethods = new RequestMethod[]{RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.GET};

            }
            log.debug("1结果name={}", name);
            log.debug("2结果value={}", Arrays.toString(value));
            log.debug("3结果path={}", Arrays.toString(path));
            log.debug("4结果 params={}", Arrays.toString(params));
            log.debug("5结果 headers={}", Arrays.toString(headers));
            log.debug("6结果 consumes={}", Arrays.toString(consumes));
            log.debug("7结果 produces={}", Arrays.toString(produces));
            log.debug("8结果 requestMethods={}", Arrays.toString(requestMethods));
        } else {
            log.info("类{}中方法{}不是API方法，原因：未获取到有效注解【POST,DELETE, PUT, PATCH, GET,   & RequestMapping】", className, methodName);
        }


        log.debug("########## 测试 ########## 是否有Note ={}", method.isAnnotationPresent(Note.class));
        log.debug("-------------------------------------------------------------------------------------");
        log.debug("处理后得到类{},方法{}的RequestMethod={}", className, methodName, Arrays.toString(requestMethods));
        log.debug("-------------------------------------------------------------------------------------");
        return requestMethods;
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
     * 类上上有贴注解【RequestMapping】url
     *
     * @param method
     * @return
     */
    private String[] getClassPath(Method method) {
        Class<?> clz = method.getDeclaringClass();
        RequestMapping classAnnotationRequestMapping = clz.getAnnotation(RequestMapping.class);
        //类上有贴注解RequestMapping,目的获取贴在类上的一段地址
        if (classAnnotationRequestMapping != null) {
            String[] pathOnClass = classAnnotationRequestMapping.value();//预期 类上的地址  数组的[0] 可能 为null！！}
            if (pathOnClass != null) {
//                log.debug("类上有该{}注解RequestMapping的方法是 ={}，路径是{}", clz.getName(), method.getName(), Arrays.toString(pathOnClass));
                return pathOnClass;
            }
        } else {
//            log.debug("类{}上没有地址该方法是 ={}", clz.getName(), method.getName());
        }
        return null;
    }


    /**
     * 获取全部的controller
     *
     * @return
     */
    private Map<String, Object> getAllControllerFromIoc() {
        Map<String, Object> controllerBeansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        return controllerBeansWithAnnotation;
    }


    public static void main(String[] args) {
        //两段url 做判断
        System.out.println(checkPath(null));
        System.out.println(checkPath(""));
        System.out.println(checkPath("   "));
        System.out.println("----------------------------------");
        System.out.println(checkPath("/"));
        System.out.println(checkPath("//"));
        System.out.println(checkPath("///"));
        System.out.println("----------------------------------");
        System.out.println(checkPath("//user"));
        System.out.println(checkPath("user//"));

        System.out.println(checkPath("user"));
        System.out.println(checkPath("/user"));
        System.out.println(checkPath("user/"));
        System.out.println(checkPath("/user/"));
        System.out.println("----------------------------------");
        System.out.println(checkPath("user//get/"));
        System.out.println(checkPath("user//save"));

    }

    /**
     * 校验中的斜线
     * 保证斜线开头，非斜线结尾，例外空串返回空串，
     * 去掉连续两个正斜线中的一个
     *
     * @param path
     * @return
     */
    public static String checkPath(String path) {
        if (path == null) return null;
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


    public static Map getRequestMethodAndPathByMethod1(Class<?> clz) {
        if (clz == null) return null;
        log.debug("*******************************************************");
        Map<String, Object> map = new LinkedHashMap<>();
        RequestMethod[] requestMethods = null;
        String[] paths = null;
        map.put("paths", paths);
        map.put("requestMethods", requestMethods);
//        // by  基类 来获取注解数据
//        Class<?> superclass = clz.getSuperclass();
//        Method[] declaredMethods = superclass.getDeclaredMethods();
//
//        for (Method declaredMethod : declaredMethods) {
//            GetMapping declaredMethodAnnotation = declaredMethod.getAnnotation(GetMapping.class);
//            String[] path = declaredMethodAnnotation.value();
//            System.out.println(Arrays.toString(path));
//        }
//
//
//        Annotation[] annotations = AnnotationUtils.getAnnotations(method);
//        log.debug("结果value={}", Arrays.toString(annotations));
//        log.debug("结果 被代理类 Class={},方法 methodName={}", superclass, method.getName());

        log.debug("*******************************************************");
        return map;
    }


    public static String printMethod(Method method) {
        if (method == null) {
            return null;
        }
        log.debug("*******************************************************");
        GetMapping getMapping = null;
        Class clz = null;
        String fullName = clz.getName();

        log.debug("代理类的 fullName={}", fullName);//com.arc.zero.controller.data.system.ArcSysUserController$$EnhancerBySpringCGLIB$$afa0fdfb
        if (fullName.contains("EnhancerBySpringCGLIB") || fullName.contains("$$")) {
            fullName = fullName.substring(0, fullName.indexOf("$$"));
            log.debug("被代理类的 fullName={}", fullName);

            try {
                clz = Class.forName(fullName);
                log.debug("被代理类的Class={}", clz.getName());

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        Method[] pMethods = clz.getMethods();
        for (Method pMethod : pMethods) {
            String name1 = pMethod.getName();
            log.debug("结果={}", name1);
            log.debug("结果={}", name1);
            log.debug("结果={}", name1);
            if (pMethod.isAnnotationPresent(GetMapping.class)) {
                getMapping = pMethod.getAnnotation(GetMapping.class);
                log.debug("结果getMapping={}", getMapping);

                if (getMapping == null) {
                    log.debug("######################### getMapping");
                    log.debug("######################### getMapping");
                    log.debug("######################### getMapping");
                    log.debug("######################### getMapping");
                }
                String[] value = getMapping.value();
                String[] path = getMapping.path();
                String[] params = getMapping.params();
                if (value != null) {
                    log.debug("结果value={}", Arrays.toString(value));
                }
                if (path != null) {
                    log.debug("结果 path{}", Arrays.toString(path));
                }
                if (path != null) {
                    log.debug("结果params={}", Arrays.toString(params));
                }
            }
        }


        log.debug("*******************************************************");
        return null;
    }

    public void printFindAnnotation(Method method) {
        GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
        if (getMapping != null) {
            log.debug("结果value={}", Arrays.toString(getMapping.value()));
            log.debug("结果path={}", Arrays.toString(getMapping.path()));
            log.debug("结果params={}", Arrays.toString(getMapping.params()));
        }

    }


    /**
     * url+Method +className+methodName +其他
     *
     * @param method
     * @return
     */
    private String[] getMethodPath(Method method) {
        String classSimpleName = method.getDeclaringClass().getSimpleName();
//        log.debug("是{}类的方法{}", classSimpleName, method.getName());
        if (method.isAnnotationPresent(GetMapping.class)) {
            return method.getAnnotation(GetMapping.class).value();
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            return method.getAnnotation(PostMapping.class).value();
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            return method.getAnnotation(DeleteMapping.class).value();
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            return method.getAnnotation(PutMapping.class).value();
        } else {
            return null;
        }
    }


    private void printRequestMapping(Method method) {
        log.debug("#######  查看 RequestMapping中的属性 #######");
        //debug 测试
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String name = requestMapping.name();
        String[] value = requestMapping.value();
        String[] path = requestMapping.path();

        String[] params = requestMapping.params();
        String[] headers = requestMapping.headers();

        String[] consumes = requestMapping.consumes();
        String[] produces = requestMapping.produces();
        RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();


        log.debug("1结果name={}", name);
        log.debug("2结果value={}", Arrays.toString(value));
        log.debug("3结果path={}", Arrays.toString(path));
        log.debug("4结果 params={}", Arrays.toString(params));
        log.debug("5结果 headers={}", Arrays.toString(headers));
        log.debug("6结果 consumes={}", Arrays.toString(consumes));
        log.debug("7结果 produces={}", Arrays.toString(produces));
        log.debug("8结果 requestMethods={}", Arrays.toString(requestMethods));
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
