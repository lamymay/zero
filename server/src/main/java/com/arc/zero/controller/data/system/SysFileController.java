package com.arc.zero.controller.data.system;

import com.arc.core.model.domain.system.SysFile;
import com.arc.core.model.request.system.file.SysFileRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件下载测试数据库记录的相关API
 * insert --路径中有斜线传递时候有问题 需要解决下
 * delete --ok
 * update --ok
 * select one  --ok
 * select page --todo 使用 spring data 就好做了,这里先使用原生的 mybatis plus
 * upload      --基本解决
 * download    --基本解决技术瓶颈,但是下载文件乱码,前端还有技术问题
 *
 * @author lamymay
 * @date 2020/11/25
 */
@Slf4j
@RestController
@RequestMapping({"/sys/file"})
public class SysFileController {

    /**
     * 文件记录表相关操作的服务
     */
    @Autowired
    private SysFileService fileService;


    @PostMapping("save")
    public ResponseEntity save(@RequestBody SysFile sysFile) {
        return ResponseEntity.ok(fileService.save(sysFile));
    }

    @GetMapping("/id/{id}")
    public ResponseVo getById(@PathVariable Long id) {
        return ResponseVo.success(fileService.getById(id));
    }

    /**
     * 通过code查询文件上传的记录
     *
     * @param code
     * @return ResponseVo
     */
    @GetMapping("/code/{code}")
    public ResponseVo getByCode(@PathVariable String code) {
        return ResponseVo.success(fileService.getByCode(code));
    }


    /**
     * 测试两种方法是否有效
     *
     * @param id   id,可选
     * @param code code,可选
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity deleteByCode(@RequestParam(required = false) Long id, @RequestParam(required = false) String code) {
        log.info("通过id={}/code={}删除文件", id, code);
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("code", code);
        ResponseEntity<Integer> responseEntity = ResponseEntity.ok(fileService.deleteByRequest(map));
        log.info("删除文件返回结果={}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/delete/id/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        return ResponseVo.success(fileService.deleteById(id));
    }

    @GetMapping("/delete/code/{code}")
    public ResponseVo delete(@PathVariable String code) {
        return ResponseVo.success(fileService.deleteByCode(code));
    }

    @GetMapping("/get")
    public ResponseVo getSysFileByUrl(@RequestParam String url) {
        SysFileRequest request = new SysFileRequest();
        request.setUrl(url);
        return ResponseVo.success(fileService.getByRequest(request));
    }

    @PostMapping("/list")
    public ResponseEntity getByRequest(@RequestBody SysFileRequest request) {
        return ResponseEntity.ok(ResponseVo.success(fileService.list(request)));
    }

    //todo 分页出参有问题
    //考虑ng的模型还是新建模型
    @PostMapping("/page")
    public ResponseVo page(@RequestBody SysFileRequest request) {
        return ResponseVo.success(fileService.listPage(request));
    }
}
