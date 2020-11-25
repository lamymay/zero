package com.arc.zero.controller.data.system;

import com.arc.core.model.request.system.file.SysFilePageRequest;
import com.arc.core.model.request.system.file.SysFileRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文件下载测试数据库记录的相关API
 *
 * @author lamymay
 */
@Slf4j
@RestController
@RequestMapping({"/sys/file"})
public class SysFileController {
    @Resource
    private SysFileService fileService;


    @GetMapping("/{id}")
    public ResponseVo getSysFileByCode(@PathVariable Long id) {
        return ResponseVo.success(fileService.getById(id));
    }

    /**
     * 通过code查询文件上传的记录
     *
     * @param code
     * @return ResponseVo
     */
    @GetMapping("/code/{code}")
    public ResponseVo code(@PathVariable String code) {
        return ResponseVo.success(fileService.getByCode(code));
    }

    //todo 测试两种方法是否有效
    @RequestMapping(value = "/delete/{code}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseVo deleteByCode(@PathVariable String code) {
        return ResponseVo.success(fileService.deleteByCode(code));
    }

    @GetMapping("/delete/id/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        return ResponseVo.success(fileService.delete(id));
    }


    @GetMapping("/query")
    public ResponseVo getSysFileByUrl(@RequestParam String url) {
        SysFileRequest request = new SysFileRequest();
        request.setUrl(url);
        return ResponseVo.success(fileService.getByRequest(request));
    }


    @PostMapping("/page")
    public ResponseVo page(@RequestBody SysFilePageRequest request) {
        return ResponseVo.success(fileService.listPgae(request));
    }


    @PostMapping("/list")
    public ResponseEntity getByRequest(@RequestBody SysFileRequest request) {
        return ResponseEntity.ok(ResponseVo.success(fileService.list(request)));
    }

//    /**
//     * listAll
//     *
//     * @return
//     */
//    @GetMapping("/list2")
//    public ResponseEntity list2() {
//        List<SysFile> list = fileService.list();
//        return ResponseEntity.ok(list);
//    }
//
//    /**
//     * listByRequest
//     *
//     * @param request
//     * @return
//     */
//    @PostMapping("/list3")
//    public ResponseEntity list3(@RequestBody SysFileRequest request) {
//        List<SysFile> list = fileService.list(request);
//        return ResponseEntity.ok(list);
//    }


}
