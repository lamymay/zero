package com.arc.zero.service.app.impl;

import com.arc.core.enums.system.IdentifierTypeEnum;
import com.arc.core.enums.system.ProjectCodeEnum;
import com.arc.core.exception.BizException;
import com.arc.core.model.domain.app.AppContact;
import com.arc.core.model.domain.system.SysUserAuth;
import com.arc.core.model.request.app.AppContactRequest;
import com.arc.zero.config.properties.app.ArcLogSwitch;
import com.arc.zero.service.app.AppContactService;
import com.arc.zero.service.app.SyncAppContactService;
import com.arc.zero.service.system.SysUserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 叶超
 * @since 2020/3/14 13:33
 */
@Slf4j
@Service
public class SyncAppContactServiceImpl implements SyncAppContactService {


    @Autowired
    private SysUserAuthService userAuthService;

    @Autowired
    private AppContactService appContactService;

    /**
     * sync
     * 0、提示是同步方案
     * 1、远程覆盖本地
     * 2、远程被本地覆盖
     * 3、合并后处理      【暂时使用该方案，且有server完成】
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Set<AppContact> sync(AppContactRequest request) {
        //参数校验
        if (request == null) {
            throw new BizException(ProjectCodeEnum.ILLEGAL_PARAMETER);
        } else if (request.getContacts() == null || request.getContacts().size() == 0) {
            throw new BizException(ProjectCodeEnum.APP_SYNC_CONTACT_NO_ONE);
        }

        final Integer type = request.getType();
        log.debug("type 结果={}", type);
        if (type == null) {
            throw new BizException(ProjectCodeEnum.APP_SYNC_CONTACT_UNKNOWN_TYPE);
        }

        //用户id
        SysUserAuth userAuth = userAuthService.getByIdentifierAndIdentifierType(request.getUserFlag(), IdentifierTypeEnum.CELLPHONE.getNumber());
        if (userAuth == null) {
            throw new BizException(ProjectCodeEnum.APP_SYNC_CONTACT_USER_NOT_EXIST);
        }

//        AppContact dbAppContact = appContactService.get(new AppContact(userAuth.getUserId()));


//        Long contactId = dbAppContact.getId();
        List<AppContact> contacts = request.getContacts();
        Set<AppContact> contactSet = new HashSet<>(contacts);
        if (contacts.size() != contactSet.size()) {
            log.debug("联系人同步中，数据中有重复数据,contacts.size={},contactSet.size={}", contacts.size(), contactSet.size());
        }

        Set<AppContact> backResult = null;
        switch (type) {
            case 1:
                //todo 1=并集 insert data by received data and analysis the common
                //backResult = unionSet(contactSet, userId);

                log.debug("todo 1=并集 insert data by received data and analysis the common");
                break;

            case 2:
                //todo 2=仅保留server端的 list all data from db and discard received data
                //backResult = onlyAcceptServer(userId);
                log.debug("222222222222222222 todo 2=仅保留server端的 ");
                break;

            case 3:
                //3=仅保留client端的， 保留client端的 delete  all data from db and insert data by received data
                backResult = onlyAcceptClient(userAuth, contactSet);
                break;
            default:
                backResult = new HashSet<>();
        }

        backResult = appContactService.listByUserId(userAuth.getUserId());
        if (ArcLogSwitch.appContactSyncLog) {
            log.debug("APP联系人同步结果日志记录={}", backResult);
        }
        return backResult;
    }

    /**
     * 保留client端的 delete  all data from db and insert data by received data
     *
     * @param userAuth
     * @param contactSet
     * @return
     */
    private Set<AppContact> onlyAcceptClient(SysUserAuth userAuth, Set<AppContact> contactSet) {
        //远程覆盖本地
        //1、remove all records  by user
        //2、insert all user's data
        //3、response user's data

        //1、参数校验
        if (contactSet == null || contactSet.size() == 0) {
            return Collections.emptySet();
        }
        //1 保存通讯录主人 saveBatchChildren
//        contactId.getId()
//        for (AppContact contact : contactSet) {
//            List<String> phoneList = contact.getPhones();
//            if (phoneList != null & phoneList.size() > 0) {
//
//            }
//        }


        //2 保存通讯录的下属

        return appContactService.saveBatchChildren(userAuth, contactSet);
    }

    /**
     * 仅保留server端的 list all data from db and discard received data
     *
     * @param userId
     * @return
     */
    private Set<AppContact> onlyAcceptServer(Long userId) {
        AppContact contact = new AppContact();
        contact.setUserId(userId);
        List<AppContact> contactList = appContactService.list(contact);
        LinkedHashSet<AppContact> contactSet = new LinkedHashSet<>(contactList);
        log.debug("APP联系人同步，策略:仅保留server端的 ,数据库中的数据条数:{},转换为set后的数据条数:{}", contactList.size(), contactSet.size());
        return contactSet;
    }

    /**
     * 1=并集， insert data by received data and analysis the common
     * 假设 leftSet =dbSet        rightSet= receiveSet
     *
     * @param receivedContactSet
     * @param userId
     * @return
     */
    private Set<AppContact> unionSet(Set<AppContact> receivedContactSet, Long userId) {

        //        Set<AppContact> receivedSetNew = receivedContactSet;
//        receivedSetNew.removeAll(dbContactSet);//        if (!b) {            log.error("失败的操作 receivedContactSet.removeAll(dbContactSet);");       }
//        Set<AppContact> targetSet = dbContactSet;
//        targetSet.addAll(receivedSetNew);

//        CompareAndSaveDto compareAndSaveDto = compareAndSave(userId,receivedContactSet, dbContactSet);
//        int delete = appContactMapper.deleteByUserId(userId);
//        dbContactSet.addAll(receivedContactSet);
//        int saveBatch = appContactMapper.saveBatchWithUserId(userId,dbContactSet);

//        log.debug("APP联系人同步，策略:client端的与server端的合并去重取并集 ,比较后处理的结果={}", compareAndSaveDto);
//        Set<AppContact> dbContactSet2 = ;
        //

        // [a,b,c,d]  [c,d,e] ==> [a,b,c,d,e]  ==> update cd  & insert e
        Set<AppContact> dbContactSet = appContactService.listByUserId(userId);
        return dbContactSet;
    }


}
