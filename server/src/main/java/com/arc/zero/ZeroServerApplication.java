package com.arc.zero;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类，项目将打包为jar，运行于内置tomcat
 *
 * @author X
 */
//@RestController
//EnableCaching  开启缓存
@EnableAsync
@EnableCaching
@EnableTransactionManagement
@MapperScan({"com.arc.zero.mapper"})
@SpringBootApplication
        (exclude = {
                org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
                org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class

        })
public class ZeroServerApplication {
    private static final Logger log = LoggerFactory.getLogger(ZeroServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ZeroServerApplication.class, args);
    }

//
//    @RequestMapping("/info")
//    @ResponseBody
//    public Object info(HttpServletRequest request) {
//        log.info("################################");
//        log.info("时间={},请求方法为 info ={},", LocalDateTime.now(), request);
//        log.info("################################");
//        return request.getContextPath() + "  " + request.getRequestURI() + "  " + request.getRemoteHost() + "  ";
//    }

}
