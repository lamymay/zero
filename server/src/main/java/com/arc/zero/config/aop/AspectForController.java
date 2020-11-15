package com.arc.zero.config.aop;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.vo.ResponseVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author 叶超
 * @since 2019/4/10 17:50
 */
@Aspect
@Component
public class AspectForController {

    private final static Logger log = LoggerFactory.getLogger(AspectForController.class);

    /**
     * 切点
     */
    @Pointcut("execution(public * com.arc.zero.controller.*.*.*Controller.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long t1 = System.currentTimeMillis();
        log.info("START AT---->{}", t1);
//        ResponseEntity result = null;
        Object proceed = proceedingJoinPoint.proceed();
        log.info("业务耗时={}ms", (System.currentTimeMillis() - t1));
        return proceed;
    }


//    /**
//     * 环绕通知
//     * Around Advice 围绕连接点执行
//     * 我们使用@Around来捕获异常信息，并用之前定义好的Result进行异常的返回
//     *
//     * @param proceedingJoinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("pointcut()")
//    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
//        long t1 = System.currentTimeMillis();
//        log.debug("START AT======>{}", t1);
//        ResponseEntity result = null;
//        Object proceed = null;
//        try {
//            proceed = proceedingJoinPoint.proceed();
//            log.info("业务耗时={}ms", (System.currentTimeMillis() - t1));
//        } catch (Throwable e) {
//            log.debug("============================");
//            log.debug("getCause={}", e.getCause());
//            log.debug("getMessage={}", e.getMessage());
//            log.debug("getLocalizedMessage={}", e.getLocalizedMessage());
//            log.error("AOP捕获异常={}", e);
//            log.debug("============================");
//            e.printStackTrace();
//            proceed = handlerException(e);
//            log.info("系统出现异常，总耗时={}ms", (System.currentTimeMillis() - t1));
//        }
//
//
//        // 数据转换
//        log.debug("proceed instanceof ResponseEntity结果={}", (proceed instanceof ResponseEntity));
//        log.debug("proceed instanceof ResponseVo 结果={}", (proceed instanceof ResponseVo));
//
//        //是ResponseEntity类型的
//        if (proceed instanceof ResponseEntity) {
//            log.debug("proceed instanceof ResponseEntity结果={}", (proceed instanceof ResponseEntity));
//            log.debug("getClass={}", proceed.getClass());
//            log.debug("结果={}", proceed);
//            result = (ResponseEntity) proceed;
//        } else if (proceed instanceof ResponseVo) {
//            //是ResponseVo 类型的则转换一下
//            log.debug("返回值类型是 ResponseVo？={}", (proceed instanceof ResponseVo));
//            ResponseVo responseVo = (ResponseVo) proceed;
//            if (proceed != null && ProjectCodeEnum.SUCCESS.getCode() == responseVo.getCode()) {
//                result = ResponseEntity.status(HttpStatus.OK).body(responseVo);
//            } else {
//                //500
//                result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseVo);
//            }
//        } else {
//            //500
//            result = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(proceed);
//        }
//
//
//        //业务处理耗时
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("").append(System.currentTimeMillis() - t1).append("ms");
//        String msg = stringBuffer.toString();
////        result.setMsg(msg);
//        log.info("COST耗时= {}", msg);
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("timeConst", msg);//设置个性化的header
//        //headers.set("Content-Encoding", "gzip");
//        //headers.setContentDispositionFormData("attachment","XXX.xls");
//
//        return ResponseEntity
//                .ok()
////        .contentType(MediaType.APPLICATION_OCTET_STREAM)
////        .contentLength(resource.contentLength())
//                .header("cost", msg)
////        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"))
//                .body(result);
//
//
//    }
//

    /**
     * 处理异常的方法
     *
     * @param e 不同类型的异常处理
     * @return Vo
     */
    private ResponseEntity handlerException(Throwable e) {

        if (e instanceof BizException) {
            return ResponseEntity.ok(ResponseVo.failure(((BizException) e).getCode(), ((BizException) e).getMsg()));

        }
        return ResponseEntity.ok(ProjectCodeEnum.UNKNOWN);
    }


    /**
     * 处理异常的方法
     *
     * @param e 不同类型的异常处理
     * @return Vo
     */
    @Deprecated
    private ResponseVo handlerExceptionOld(Throwable e) {

        if (e instanceof BizException) {
            return ResponseVo.failure(((BizException) e).getCode(), ((BizException) e).getMsg());
        }
        return ResponseVo.failure(ProjectCodeEnum.UNKNOWN);
    }


}
