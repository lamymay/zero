package com.arc.zero.controller.data.app;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.app.AppContact;
import com.arc.core.model.request.app.AppContactRequest;
import com.arc.zero.service.app.AppContactService;
import com.arc.zero.service.app.SyncAppContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 处理联系人同步相关的接口
 *
 * @author 叶超
 * @since 2020/2/27 10:44
 */
@Validated
@Slf4j
@RestController
@RequestMapping("/contact")
public class ContactController {

    /**
     * 通讯录相关服务
     */
    @Autowired
    private AppContactService appContactService;

    @Autowired
    private SyncAppContactService syncAppContactService;

    /**
     * 同步联系人
     *
     * @param request
     * @return
     */
    @PostMapping("/sync")
    public ResponseEntity sync(@RequestBody AppContactRequest request) {
        log.debug("########################################");
        log.debug("APP同步获取接口参数={}", request);
        long t1 = System.currentTimeMillis();
        //参数校验
        if (request == null || request.getContacts() == null || request.getContacts().size() == 0) {
            throw new BizException(ProjectCodeEnum.ILLEGAL_PARAMETER);
        }
        Set<AppContact> contacts = syncAppContactService.sync(request);
        log.debug("###################" + (System.currentTimeMillis() - t1) + "ms#####################");
        return ResponseEntity.ok(contacts);
    }


    //*************************** 后台管理使用的接口或者测试用 ************************************
//
//    @PostMapping("/sync/1")
//    public ResponseEntity sync1(String contacts) {
//        log.debug("########################################");
//        log.debug("同步获取接口参数={}", JSON.toJSONString(contacts));
//        return ResponseEntity.ok(true);
//    }

    /**
     * 保存一条数据并返回数据的主键
     *
     * @param contact 实体
     * @return 主键 PK
     */
    @PostMapping("")
    public ResponseEntity<Long> save(@NotNull @RequestBody AppContact contact) {
        return ResponseEntity.ok(appContactService.save(contact));
    }

    /**
     * 通过主键删除一条数据
     *
     * @param id 主键
     * @return 影响数据条数
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(appContactService.delete(id));
    }

    /**
     * 更新一条数据
     *
     * @param id
     * @param contact
     * @return 影响数据条数
     */
    @PutMapping("/{id}")
    public ResponseEntity<Integer> update(@PathVariable(required = true) Long id, @NotNull @RequestBody AppContact contact) {
        contact.setId(id);
        return ResponseEntity.ok(appContactService.update(contact));
    }

    /**
     * 通过主键查询一条数据
     *
     * @param id 主键
     * @return 返回一条数据
     */
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Long id) {
        return ResponseEntity.ok(appContactService.get(id));
    }

    /**
     * 条件查询数据列表
     *
     * @return 数据集合
     */
    @PostMapping("/list/query")
    public ResponseEntity<List<AppContact>> list(AppContact request) {
        return ResponseEntity.ok(appContactService.list(request));
    }

    /**
     * 批量删除
     *
     * @param ids 主键集合
     * @return 影响条数
     */
    @DeleteMapping("/delete/batch")
    public ResponseEntity<Integer> deleteIdIn(@NotNull @RequestBody Set<Long> ids) {
        log.debug("批量删除 parameter ids={}", ids.size());
        return ResponseEntity.ok(appContactService.deleteBatch(ids));
    }

//    /**
//     * 批量更新
//     *
//     * @param contacts 数据集合
//     * @return 影响条数
//     */
//    ResponseVo<Integer> updateBatch(List<AppContact> contacts) {
//        return ResponseVo.success(appContactService.updateBatch(contacts));
//    }

}
