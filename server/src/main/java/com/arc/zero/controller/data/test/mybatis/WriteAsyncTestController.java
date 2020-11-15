package com.arc.zero.controller.data.test.mybatis;

import com.arc.core.model.domain.system.KeyValue;
import com.arc.zero.service.system.KeyValueService;
import com.arc.zero.test.async.AsyncWriteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 异步写db的测试
 *
 * @author yechao
 * @since 2020/6/2 6:41 下午
 */
@Slf4j
@RestController
@RequestMapping("/async")
public class WriteAsyncTestController {

    @Resource
    private AsyncWriteService asyncWriteService;

    @Resource
    private KeyValueService keyValueService;

    @PostMapping("")
    public ResponseEntity save(@RequestBody KeyValue keyValue) {
        return ResponseEntity.ok(asyncWriteService.save(keyValue));
    }


    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id) {
        return ResponseEntity.ok(keyValueService.get(id));
    }

//    /**
//     * 通过code查询文件上传的记录
//     *
//     * @param code
//     * @return ResponseEntity
//     */
//    @GetMapping("/code/{code}")
//    public ResponseEntity code(@PathVariable String code) {
//        return ResponseEntity.ok(fileService.getByCode(code));
//    }
//
//    //todo 测试两种方法是否有效
//    @RequestMapping(value = "/delete/{code}", method = {RequestMethod.DELETE, RequestMethod.GET})
//    public ResponseEntity deleteByCode(@PathVariable String code) {
//        return ResponseEntity.ok(fileService.deleteByCode(code));
//    }
//
//    @GetMapping("/delete/id/{id}")
//    public ResponseEntity delete(@PathVariable Long id) {
//        return ResponseEntity.ok(fileService.delete(id));
//    }
//
//
//    @GetMapping("/url/{url}")
//    public ResponseEntity getSysFileByUrl(@PathVariable String url) {
//        SysFileRequest request = new SysFileRequest();
//        request.setUrl(url);
//        return ResponseEntity.ok(fileService.getByRequest(request));
//    }
//
//    @PostMapping("/list")
//    public ResponseEntity getByRequest(@RequestBody SysFileRequest request) {
//        return ResponseEntity.ok(ResponseEntity.ok(fileService.listByRequest(request)));
//
//    }
//
//    @PostMapping("/page")
//    public ResponseEntity page(@RequestBody SysFilePageRequest request) {
//        return ResponseEntity.ok(fileService.listPgae(request));
//    }
}
