package com.arc.zero.controller.data.system;

import com.alibaba.fastjson.JSON;
import com.arc.core.model.domain.system.KeyValue;
import com.arc.core.model.request.system.file.KeyValueRequest;
import com.arc.zero.service.system.KeyValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 叶超
 * @since 2020/4/16 23:26
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/kv")
public class KeyValueController {

    /**
     * kv的service
     */
    @Autowired
    private KeyValueService keyValueService;

    /**
     * 保存
     *
     * @param keyValue kv实体
     * @return 返回数据的PK
     */
    @PostMapping(value = {"", "/save", "/insert"})
    public ResponseEntity save(@RequestBody KeyValue keyValue) {
        return ResponseEntity.ok(keyValueService.save(keyValue));
    }

    /**
     * 查询列表
     *
     * @param keyValue 查询参数
     * @return 返回符合条件的kv列表
     */
    @PostMapping(value = {"/list", "/query"})
    public ResponseEntity listByQuery(@RequestBody KeyValue keyValue) {
        return ResponseEntity.ok(keyValueService.list(keyValue));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Object request) {
        return ResponseEntity.ok(keyValueService.update(request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok(keyValueService.delete(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        System.out.println("##################KV日志###"+ JSON.toJSONString(log));
        System.out.println("#####################isTraceEnabled="+log.isTraceEnabled());
        System.out.println("##################### isDebugEnabled="+log.isDebugEnabled());
        System.out.println("#####################isErrorEnabled="+log.isErrorEnabled());
        System.out.println("#####################isInfoEnabled="+log.isInfoEnabled());


        try {
            System.out.println(1 / 0);
        } catch (Exception exception) {
            System.out.println("##################KV日志###"+ JSON.toJSONString(log));
            System.out.println("##################### 預期是debug"+log.isDebugEnabled());
            System.out.println("#####################isErrorEnabled"+log.isErrorEnabled());
            System.out.println("#####################isInfoEnabled"+log.isInfoEnabled());
            System.out.println("#####################isDebugEnabled"+log.isDebugEnabled());
            log.trace("trace exception={}",exception);
            log.debug("debug exception={}",exception);
            log.info("info exception={}",exception);
            log.warn("warn exception={}",exception);
            log.error("error exception={}",exception);
        }
        return ResponseEntity.ok(keyValueService.get(id));
    }

    @PostMapping(value = "/page")
    public ResponseEntity listPage(@RequestBody KeyValueRequest query) {
        return ResponseEntity.ok(keyValueService.listPage(query));
    }

}
