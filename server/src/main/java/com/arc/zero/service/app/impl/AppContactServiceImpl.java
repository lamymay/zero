package com.arc.zero.service.app.impl;

import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.app.AppContact;
import com.arc.core.model.domain.app.AppContactDetail;
import com.arc.core.model.domain.system.SysUserAuth;
import com.arc.utils.Assert;
import com.arc.zero.mapper.app.AppContactDetailMapper;
import com.arc.zero.mapper.app.AppContactMapper;
import com.arc.zero.service.app.AppContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * APP联系人表服务
 *
 * @author lamy
 * @since
 */
@Slf4j
@Service
public class AppContactServiceImpl implements AppContactService {

    @Resource
    private AppContactMapper appContactMapper;

    @Resource
    private AppContactDetailMapper appContactDetailMapper;

    @Override
    public Long save(AppContact contact) {
        return appContactMapper.save(contact) == 1 ? contact.getId() : null;
    }

    @Override
    public int delete(Long id) {
        return appContactMapper.delete(id);
    }


    @Override
    public int update(AppContact tAppContact) {
        return appContactMapper.update(tAppContact);
    }

    @Override
    public AppContact get(Long id) {
        return appContactMapper.get(id);
    }

    @Override
    public AppContact get(AppContact contact) {
        return appContactMapper.getByQuery(contact);
    }

    @Override
    public List<AppContact> list(AppContact contact) {
        return appContactMapper.list(contact);
    }

    @Override
    public Set<AppContact> listByUserId(Long userId) {
        // todo 连表查询
        return appContactMapper.listByUserId(userId);
    }

    /**
     * remove all records  by user
     * 1、select user_id from user_auth where user=XX AND identifier_type=cellphone
     * 2、delete from contact where user_id=XX
     *
     * @param userId
     * @return
     */
    private boolean deleteAllContactByUserId(Long userId) {
        return deleteByUserId(userId) != 0;
    }

    @Override
    public int deleteByUserId(Long userId) {
        Assert.notNull(userId);
        int delete = appContactMapper.deleteByUserId(userId);
        if (delete != 0) {
            log.warn("通过系统用户id:{},删除用户联系人数据数据条数:{}", userId, delete);
        }
        return delete;
    }


    @Override
    public int deleteBatch(Set<Long> deleteIdSet) {
        if (deleteIdSet == null || deleteIdSet.size() > 0) {
            return 0;
        }
        return appContactMapper.deleteBatch(deleteIdSet);
    }

    @Override
    public int updateBatch(Set<AppContact> contacts) {
        int update = 0;
        if (contacts != null && contacts.size() >= 0) {
            //todo  批量更新
            for (AppContact contact : contacts) {
                update += appContactMapper.update(contact);
            }
        }
        return update;
    }


    // todo 改进 比对逻辑
    @Override
    @Deprecated
    public int saveBatch(Set<AppContact> contactSet) {
        if (contactSet == null || contactSet.size() == 0) {
            return 0;
        }
        return appContactMapper.saveBatch(contactSet);
    }


    // 一个联系人下关联多个电话或者Email， 注意 联系人表是自关联的， 有的主人不是系统用户所以有可能没有userId

    /**
     * 数据类似
     * [ {contact1-带有userId，通讯录的主人},{contact2},{contact3},{contact4}]
     *
     * @param userAuth
     * @param contactSet
     * @return
     */
    // 本地替换后数据，先删除旧的数据然后插入新的数据        log.debug("APP联系人同步，策略:仅保留client端的,丢弃server端的 ,清除数据库中旧数据条数={}，插入新数据条数={}", delFlag, saveBatch);
    @Override
    public Set<AppContact> saveBatchChildren(SysUserAuth userAuth, Set<AppContact> contactSet) {

        if (contactSet == null || contactSet.size() == 0) {
            return Collections.emptySet();
        }

        //删除既有数据 ab 两部分
        Long userId = userAuth.getUserId();
        Set<Long> appContactIds = appContactMapper.listIdsByUserId(userId);
        if (appContactIds != null && appContactIds.size() > 0) {
            int deleteContactDetailAffectRows = appContactDetailMapper.deleteBatchByContactIds(appContactIds);
            log.debug("子表删除数量={}", deleteContactDetailAffectRows);
        }

        int deleteContactAffectRows = appContactMapper.deleteByUserId(userId);
        log.debug("联系人表删除数量={}， ", deleteContactAffectRows);

        //2 保存通讯录的下属

        //构建用户联系人数据集合 contactSet，获取user对应的用户id
        Set<AppContactDetail> appContactDetailList = new HashSet<>();

        for (AppContact rowContact : contactSet) {
            rowContact.setUserId(userId);
            Long result = appContactMapper.save(rowContact);
            if (result != 1) {
                throw new BizException(ProjectCodeEnum.INSERT_EXCEPTION);
            }
            Long incContactId = rowContact.getId();
            appContactDetailList.clear();
            //save 子属性
            List<String> phoneList = rowContact.getPhones();
            List<String> emailList = rowContact.getEmails();


            if (phoneList != null && phoneList.size() > 0) {
                for (String phone : phoneList) {
                    appContactDetailList.add(new AppContactDetail(incContactId, "phone", phone, ""));
                }
            }

            if (emailList != null && emailList.size() > 0) {
                for (String email : emailList) {
                    appContactDetailList.add(new AppContactDetail(incContactId, "email", email, ""));
                }
            }
            if (appContactDetailList.size() > 0) {
                int saveBatch = appContactDetailMapper.saveBatch(appContactDetailList);
                log.debug("子表数据={}", saveBatch);
            }
        }
        return null;
    }

//    /**
//     * 相同数据用update， 不同数据采用不同方式维护 减缓id增大的
//     *
//     * @param userId
//     * @param receivedContactSet
//     * @param dbContactSet
//     * @return
//     */
//    private CompareAndSaveDto compareAndSave(Long userId, Set<AppContact> receivedContactSet, Set<AppContact> dbContactSet) {
//        int delete = appContactService.deleteByUserId(userId);
//
//        dbContactSet.addAll(receivedContactSet);
//
//        int saveBatch = appContactService.saveBatchWithUserId(userId, dbContactSet);
//
//        CompareAndSaveDto compareAndSaveDto = new CompareAndSaveDto(saveBatch, delete, 0);
//        return compareAndSaveDto;
//        // 1 received is empty or null   & db is null
//
//        // 2 received is empty or null   & db  has data
//
//        // 3 received  has data & db  has data
//
//    }
}
