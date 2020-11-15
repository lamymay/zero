package com.arc.zero.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * 初始化操作
 *
 * @author yechao
 * @date 2019/1/2 23:24
 */
@Slf4j
@Component
public class SystemInitializationStartup implements ApplicationListener<ContextRefreshedEvent> {


    /**
     * web.system.initial:是否在系统启动的时候初始化一些操作
     * 注意：1、建议配置在配置文件中 2、缺省为true
     */
    @Value("${web.system.initial:true}")
    boolean initial;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //Spring容器加载完毕之后执行: 以下方法
        log.debug("是否在系统启动的时候初始化一些操作 {}", initial);
        if (initial) {
            log.debug("debug结果={}", System.getenv("OS"));
            log.info("info结果={}", System.getenv("OS"));
            log.warn("warn结果={}", System.getenv("OS"));
            log.trace("trace结果={}", System.getenv("OS"));
            log.error("error结果={}", System.getenv("OS"));

            log.info("System.getenv() 方法是获取指定的环境变量的值={}", System.getenv());
            log.info("System.getenv() 方法是获取指定的环境变量的值={}", System.getenv().toString());

            log.debug("++++++++++++++++++++++++++++++++++++++");
            log.info("Path ={}", System.getenv("Path"));
            log.info("用户名USERNAME ={}", System.getenv("USERNAME"));
            log.info("计算机名COMPUTERNAME ={}", System.getenv("COMPUTERNAME"));
            log.info("用户目录USERPROFILE ={}", System.getenv("USERPROFILE"));
            log.info("用户域USERDNSDOMAIN ={}", System.getenv("USERDNSDOMAIN"));
            log.info("帐户的域的名称USERDOMAIN ={}", System.getenv("USERDOMAIN"));
            log.info("Session名称 ={}", System.getenv("SESSIONNAME"));
            log.info("用户公共目录ALLUSERSPROFILE ={}", System.getenv("ALLUSERSPROFILE"));
            log.info("可执行后缀 ={}", System.getenv("PATHEXT"));
            log.info("classpath环境变量 ={}", System.getenv("classpath"));
            log.info("JAVA_HOME ={}", System.getenv("JAVA_HOME"));
            log.info("用户临时文件目录TEMP ={}", System.getenv("TEMP"));
            log.info("系统盘符 ={}", System.getenv("SystemDrive"));
            log.info("默认程序目录 ={}", System.getenv("ProgramFiles"));
            log.info("处理器体系结构 ={}", System.getenv("PROCESSOR_ARCHITECTURE"));
            log.info("处理级别 ={}", System.getenv("PROCESSOR_LEVEL"));
            log.info("系统启动目录 ={}", System.getenv("SystemRoot"));
            log.info("命令行解释器可执行程序的准确路径 ={}", System.getenv("ComSpec"));
            log.info("应用程序数据目录 ={}", System.getenv("APPDATA"));
            log.info("java.version ={}", System.getenv("java.version"));
            log.info("java.vendor ={}", System.getenv("java.vendor"));

            log.debug("++++++++++++++++++++++++++++++++++++++");
            String[] activeProfiles = applicationContext.getEnvironment().getActiveProfiles();
            log.info("activeProfiles.length ={}", activeProfiles.length);
            String[] activeProfiles1 = env.getActiveProfiles();
            String[] defaultProfiles = env.getDefaultProfiles();
            log.info("activeProfiles1={}", activeProfiles1);
            log.info("defaultProfiles={}", defaultProfiles);

            if (activeProfiles != null && activeProfiles.length != 0) {
                for (String profile : activeProfiles) {
                    log.info("profile ={}", profile);
                }
            }
            log.info("profile ={}", System.getProperty("spring.profiles.active"));



            System.out.println("##################################################");
            System.out.println(" Spring容器加载完毕之后执行的方法可以做一些扩展  ");
            System.out.println("##################################################");
        }
    }


}

//System.getProperty()
//
//java.version Java ：运行时环境版本
//java.vendor Java ：运行时环境供应商
//java.vendor.url ：Java供应商的 URL
//java.home &nbsp;&nbsp;：Java安装目录
//java.vm.specification.version： Java虚拟机规范版本
//java.vm.specification.vendor ：Java虚拟机规范供应商
//java.vm.specification.name &nbsp; ：Java虚拟机规范名称
//java.vm.version ：Java虚拟机实现版本
//java.vm.vendor ：Java虚拟机实现供应商
//java.vm.name&nbsp; ：Java虚拟机实现名称
//java.specification.version：Java运行时环境规范版本
//java.specification.vendor：Java运行时环境规范供应商
//java.specification.name ：Java运行时环境规范名称
//java.class.version ：Java类格式版本号
//java.class.path ：Java类路径
//java.library.path  ：加载库时搜索的路径列表
//java.io.tmpdir  ：默认的临时文件路径
//java.compiler  ：要使用的 JIT编译器的名称
//java.ext.dirs ：一个或多个扩展目录的路径
//os.name ：操作系统的名称
//os.arch  ：操作系统的架构
//os.version  ：操作系统的版本
//file.separator ：文件分隔符
//path.separator ：路径分隔符
//line.separator ：行分隔符
//user.name ：用户的账户名称
//user.home ：用户的主目录
//user.dir：用户的当前工作目录
