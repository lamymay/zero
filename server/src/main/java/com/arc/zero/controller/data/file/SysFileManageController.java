package com.arc.zero.controller.data.file;

import com.arc.core.model.request.system.file.SysFileRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 文件下载测试数据库记录的相关API
 * insert --路径中有斜线传递时候有问题 需要解决下
 *
 * @author lamymay
 * @date 2020/11/25
 */
@Slf4j
@RestController
@RequestMapping({"/v2/file"})
public class SysFileManageController {

    /**
     * 文件记录表相关操作的服务
     */
    @Autowired
    private SysFileService fileService;

    @RequestMapping(value = "/delete/{symbol}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity delete(@PathVariable String symbol) {
        HashMap<String, Object> map = new HashMap<>();
        if (symbol == null) {
            map.put("code", -1);
            map.put("message", "参数错误");
        }
        try {
            Long id = Long.valueOf(symbol);
            map.put("code", 1);
            map.put("data", id);
            map.put("describe", "参数是数字类型的id");
            boolean deleteAndCleanFile = fileService.deleteAndCleanFile(new SysFileRequest(id), true);
            map.put("data", deleteAndCleanFile);
        } catch (NumberFormatException exception) {
            /*
            注意 : 0X1234 会被判定为字符串
             */
            map.put("code", 1);
            map.put("describe", "参数是字符串类型的code");
            boolean deleteAndCleanFile = fileService.deleteAndCleanFile(new SysFileRequest(symbol), true);
            map.put("data", deleteAndCleanFile);
        } catch (Exception exception) {
            /*
            注意 : 0X1234 会被判定为字符串
             */
            map.put("code", -1);
            map.put("describe", exception.getCause() + exception.getMessage());
            map.put("data", exception);
        }

        return ResponseEntity.ok(map);
    }

    //        if (symbol instanceof Long) {
//            Long id = (Long) symbol;
//            map.put("code", 1);
//            map.put("data", id);
//            map.put("describe", "参数是数字类型的id");
//            boolean deleteAndCleanFile = fileService.deleteAndCleanFile(new SysFileRequest(id), true);
//            map.put("data", deleteAndCleanFile);
//        } else if (symbol instanceof String) {
//            /*
//            注意 : 0X1234 会被判定为字符串
//             */
//            map.put("code", 1);
//            String code = (String) symbol;
//            map.put("describe", "参数是字符串类型的code");
//            boolean deleteAndCleanFile = fileService.deleteAndCleanFile(new SysFileRequest(code), true);
//            map.put("data", deleteAndCleanFile);
//        }
//    @GetMapping("/delete/code/{code}")
//    public ResponseVo delete(@PathVariable String code) {
//        return ResponseVo.success(fileService.deleteByCode(code));
//    }

    @GetMapping("/get")
    public ResponseVo getSysFileByUrl(@RequestParam String url) {
        SysFileRequest request = new SysFileRequest();
        request.setUri(url);
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
