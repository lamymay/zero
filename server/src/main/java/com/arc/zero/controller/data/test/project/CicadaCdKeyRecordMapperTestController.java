package com.arc.zero.controller.data.test.project;

import com.arc.core.model.vo.ResponseVo;
import com.arc.zero.mapper.test.CicadaCdKeyRecordDO;
import com.arc.zero.mapper.test.CicadaCdKeyRecordMapper;
import com.arc.zero.mapper.test.CicadaCdKeyRecordRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author may
 */
@Slf4j
@RestController
@RequestMapping("/v1/record")
public class CicadaCdKeyRecordMapperTestController {

    @Resource
    private CicadaCdKeyRecordMapper recordMapper;

    @PostMapping(value = "/save")
    public ResponseVo save(@RequestBody CicadaCdKeyRecordDO request) {
        Long row = recordMapper.insert(request);
        if (row != null && row == 1L) {
            return ResponseVo.success(request.getId());
        }
        return ResponseVo.failure();
    }

    @PostMapping(value = "/update")
    public ResponseVo update(@RequestBody CicadaCdKeyRecordDO request) {
        return ResponseVo.success(recordMapper.update(request));
    }

    @RequestMapping(value = "/delete/{id}")
    public ResponseVo delete(@PathVariable Long id) {
        return ResponseVo.success(recordMapper.deleteById(id));
    }

    @PostMapping(value = "/get")
    public ResponseVo get(@RequestBody CicadaCdKeyRecordRequest request) {
        return ResponseVo.success(recordMapper.selectOne(request.getId()));
    }

    @PostMapping(value = "/count")
    public ResponseVo count(@RequestBody CicadaCdKeyRecordRequest request) {
        return ResponseVo.success(recordMapper.selectPageCount(request));
    }

    @PostMapping(value = "/page")
    public ResponseVo page(@RequestBody CicadaCdKeyRecordRequest request) {
        log.debug("入参={}", request);
        Long total = recordMapper.selectPageCount(request);
        System.out.println("#### total=" + total);
        List<CicadaCdKeyRecordDO> records = recordMapper.selectPageList(request);

        Pageable pageable = PageRequest.of(request.getCurrentPage() - 1, request.getPageSize());
        Page page = new PageImpl<>(records, pageable, total==null?0:total);
        return ResponseVo.success(page);
    }

}
