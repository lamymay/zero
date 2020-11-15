package com.arc.zero.controller.data.system;

import com.arc.core.model.domain.system.SysDataDictionary;
import com.arc.core.model.request.system.dictionary.SysDataDictionaryRequest;
import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.service.system.SysDataDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统数据字典相关接口
 * 接口提供的功能：
 * 单表：查询、新增、批量新增、删除、批量删除、更新、批量更新、分页查询
 * *
 *
 * @author 叶超
 * @since 2019/4/27
 */
@Slf4j
@RestController
@RequestMapping("/sys/dictionaries")
public class DataDictionaryController {

    @Resource
    private SysDataDictionaryService dictionaryService;

    /**
     * 新建数据字典
     *
     * @param dictionary SysDataDictionary
     * @return ResponseVo
     */
    @PostMapping("")
    public ResponseVo save(@RequestBody SysDataDictionary dictionary,
                           @RequestBody(required = false) List<SysDataDictionary> dictionaries,
                           @RequestParam(required = false) Boolean batch) {
        if (batch != null && batch) {
            log.debug("batchSave 数据字典,参数 dictionarys={}, ", dictionaries.size());
            return ResponseVo.success(dictionaryService.batchSave(dictionaries));
        }
        log.debug("新建数据字典，参数 sysLog={}, ", dictionary.toString());
        return ResponseVo.success(dictionaryService.save(dictionary));
    }

    /**
     * 批量操作--save
     *
     * @param dictionaries
     * @return
     */
    @PostMapping("/batch/save")
    public ResponseVo batchSave(@RequestBody List<SysDataDictionary> dictionaries) {
        log.debug("batchSave 数据字典,参数 dictionarys={}, ", dictionaries.size());
        return ResponseVo.success(dictionaryService.batchSave(dictionaries));
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        log.debug("参数删除数据字典，参数id={}", id);
        return ResponseVo.success(dictionaryService.delete(id));
    }

    /**
     * 更新数据字典
     * 注意 1请求类型为Content-Type:application/json
     * 方法： PUT
     * 对于必要参数没有传则判断了一下会返回错误代码
     * http://ip:port/url
     *
     * @param dictionary
     * @return ResponseVo
     */
    @PutMapping("")
    public ResponseVo update(@RequestBody SysDataDictionary dictionary) {
        return ResponseVo.success(dictionaryService.update(dictionary));
    }

    /**
     * 获取单个数据字典
     * ApiImplicitParam这个注解不只是注解，还会影响运行期的程序，例子如下：
     *
     * @param id 主键
     * @return ResponseVo
     */
    @GetMapping("/{id}")
    public ResponseVo get(@PathVariable("id") Long id) {
        log.debug("获取单个数据字典,参数 id={}", id);
        return ResponseVo.success(dictionaryService.get(id));
    }

    /**
     * 获取数据字典列表
     *
     * @return ResponseVo
     */
    @PostMapping("/page")
    public ResponseVo page(@RequestBody SysDataDictionaryRequest dictionaryRequest) {
        log.debug("获取数据字典列表，参数={}", dictionaryRequest);
        return ResponseVo.success(dictionaryService.page());
    }

    @PostMapping("/type")
    public ResponseVo listBytType(String type) {
        return ResponseVo.success(dictionaryService.listByType(type));
    }

}

